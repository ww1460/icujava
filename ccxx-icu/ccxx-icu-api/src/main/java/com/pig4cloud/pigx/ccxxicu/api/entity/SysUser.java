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

package com.pig4cloud.pigx.ccxxicu.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author 博羸
 * @date 2019-07-20 15:04:54
 */
@Data
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户表")
public class SysUser extends Model<SysUser> {
private static final long serialVersionUID = 1L;

    /**
   * 主键ID
   */
    @TableId
    private Integer userId;
    /**
   * 用户名
   */
    private String username;
    /**
   * 
   */
    private String password;
    /**
   * 随机盐
   */
    private String salt;
    /**
   * 简介
   */
    private String phone;
    /**
   * 头像
   */
    private String avatar;
    /**
   * 部门ID
   */
    private Integer deptId;
    /**
   * 创建时间
   */
    private LocalDateTime createTime;
    /**
   * 修改时间
   */
    private LocalDateTime updateTime;
    /**
   * 0-正常，9-锁定
   */
    private String lockFlag;
    /**
   * 0-正常，1-删除09090
   */
    private String delFlag;
    /**
   * 微信openid
   */
    private String wxOpenid;
    /**
   * QQ openid
   */
    private String qqOpenid;
    /**
   * 
   */
    private String giteeLogin;
    /**
   * 
   */
    private String oscId;
    /**
   * 所属租户
   */
    private Integer tenantId;
  
}
