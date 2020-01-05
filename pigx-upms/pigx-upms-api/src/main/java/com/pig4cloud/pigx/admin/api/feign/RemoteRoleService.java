package com.pig4cloud.pigx.admin.api.feign;


import com.pig4cloud.pigx.admin.api.dto.UserInfo;
import com.pig4cloud.pigx.admin.api.entity.SysRole;
import com.pig4cloud.pigx.common.core.constant.SecurityConstants;
import com.pig4cloud.pigx.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author bolei
 * @Date 2019/9/26 14:02
 * @Version 1.0
 * @Des 描述
 */

@FeignClient(contextId = "remoteRoleService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteRoleService {

	/**
	 * 通过角色列表获取角色的最大数据权限的角色
	 *
	 * @param roleIds
	 * @return
	 */
	@GetMapping("/role/maxDstypeRole")
	R<SysRole> getMaxDstypeRole(@RequestParam(value = "roleIds") String roleIds);

}
