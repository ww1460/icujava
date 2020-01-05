package com.pig4cloud.pigx.admin.api.feign;


import com.pig4cloud.pigx.admin.api.dto.DeptTree;
import com.pig4cloud.pigx.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @Author bolei
 * @Date 2019/9/26 14:02
 * @Version 1.0
 * @Des 描述
 */

@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDeptService {



	/**
	 * 查询本级及其下级部门
	 */
	@GetMapping("/dept/subordinate")
	public R getSubordinate (@RequestParam(value = "deptId") Integer deptId);


	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/dept/tree")
	public R<List<DeptTree>> getTree();



}
