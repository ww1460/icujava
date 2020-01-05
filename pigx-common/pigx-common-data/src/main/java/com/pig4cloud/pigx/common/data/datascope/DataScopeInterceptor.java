/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.common.data.datascope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.pig4cloud.pigx.admin.api.entity.SysRole;
import com.pig4cloud.pigx.admin.api.feign.RemoteDeptService;
import com.pig4cloud.pigx.admin.api.feign.RemoteRoleService;
import com.pig4cloud.pigx.admin.api.feign.RemoteUserService;
import com.pig4cloud.pigx.admin.api.vo.UserVO;
import com.pig4cloud.pigx.common.core.constant.SecurityConstants;
import com.pig4cloud.pigx.common.core.exception.CheckedException;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.enums.DataScopeTypeEnum;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author lengleng
 * @date 2018/12/26
 * <p>
 * mybatis 数据权限拦截器
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {

	private final RemoteDeptService remoteDeptService;

	private final RemoteRoleService remoteRoleService;


	@Override
	@SneakyThrows
	public Object intercept(Invocation invocation) {
		StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		this.sqlParser(metaObject);
		// 先判断是不是SELECT操作
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
			return invocation.proceed();
		}

		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
		String originalSql = boundSql.getSql();
		Object parameterObject = boundSql.getParameterObject();

		//查找参数中包含DataScope类型的参数
		DataScope dataScope = findDataScopeObject(parameterObject);
		if (dataScope == null) {
			return invocation.proceed();
		}

		String scopeName = dataScope.getScopeName();
		List<Integer> deptIds = dataScope.getDeptIds();
		// 优先获取赋值数据
		if (CollUtil.isEmpty(deptIds)) {
			PigxUser user = SecurityUtils.getUser();
			if (user == null) {
				throw new CheckedException("auto datascope, set up security details true");
			}

			List<String> roleIdList = user.getAuthorities()
					.stream().map(GrantedAuthority::getAuthority)
					.filter(authority -> authority.startsWith(SecurityConstants.ROLE))
					.map(authority -> authority.split("_")[1])
					.collect(Collectors.toList());

			//通过角色roleIdList查询用户具有的所有角色的权限最大值
			/*Entity query = Db.use(dataSource)
					.query("SELECT * FROM sys_role where role_id IN (" + CollUtil.join(roleIdList, ",") + ")")
					.stream().min(Comparator.comparingInt(o -> o.getInt("ds_type"))).get();

			Integer dsType = query.getInt("ds_type");*/

			//fix 修改为远程调用
			R<SysRole> sysRoleR = remoteRoleService.getMaxDstypeRole(CollUtil.join(roleIdList, ","));
			SysRole sysRole = sysRoleR.getData();
			Integer dsType = sysRole.getDsType();

			// 查询全部
			if (DataScopeTypeEnum.ALL.getType() == dsType) {
				return invocation.proceed();
			}
			// 自定义
			if (DataScopeTypeEnum.CUSTOM.getType() == dsType) {
				String dsScope = sysRole.getDsScope();
				deptIds.addAll(Arrays.stream(dsScope.split(","))
						.map(Integer::parseInt).collect(Collectors.toList()));
			}
			// 查询本级及其下级
			if (DataScopeTypeEnum.OWN_CHILD_LEVEL.getType() == dsType) {
				//查询本级及下级的部门ID
				/*List<Integer> deptIdList = Db.use(dataSource)
						.findBy("sys_dept_relation", "ancestor", user.getDeptId())
						.stream().map(entity -> entity.getInt("descendant"))
						.collect(Collectors.toList());*/
				R<List<Integer>> deptIdListR =remoteDeptService.getSubordinate(user.getDeptId());
				List<Integer> deptIdList = deptIdListR.getData();
				deptIds.addAll(deptIdList);
			}
			// 只查询本级
			if (DataScopeTypeEnum.OWN_LEVEL.getType() == dsType) {
				deptIds.add(user.getDeptId());
			}
		}
		String join = CollectionUtil.join(deptIds, ",");
		originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
		metaObject.setValue("delegate.boundSql.sql", originalSql);
		return invocation.proceed();
	}

	/**
	 * 生成拦截对象的代理
	 *
	 * @param target 目标对象
	 * @return 代理对象
	 */
	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	/**
	 * mybatis配置的属性
	 *
	 * @param properties mybatis配置的属性
	 */
	@Override
	public void setProperties(Properties properties) {

	}

	/**
	 * 查找参数是否包括DataScope对象
	 *
	 * @param parameterObj 参数列表
	 * @return DataScope
	 */
	private DataScope findDataScopeObject(Object parameterObj) {
		if (parameterObj instanceof DataScope) {
			return (DataScope) parameterObj;
		} else if (parameterObj instanceof Map) {
			for (Object val : ((Map<?, ?>) parameterObj).values()) {
				if (val instanceof DataScope) {
					return (DataScope) val;
				}
			}
		}
		return null;
	}

}
