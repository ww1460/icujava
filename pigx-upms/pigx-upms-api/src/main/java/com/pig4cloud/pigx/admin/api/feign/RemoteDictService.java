package com.pig4cloud.pigx.admin.api.feign;

import com.pig4cloud.pigx.admin.api.entity.SysDictItem;
import com.pig4cloud.pigx.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pigx.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(contextId = "remoteDictService",value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDictService {

	/**
	 * 通过字典名称
	 * @param type
	 * @return
	 */
	@PostMapping("/dict/type/{type}")
	R<List<SysDictItem>> dictItemList(@PathVariable String type);


}
