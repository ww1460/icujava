package com.pig4cloud.pigx.ccxxicu.openapi;

import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisPatient;
import com.pig4cloud.pigx.ccxxicu.common.exception.ApiException;
import com.pig4cloud.pigx.ccxxicu.common.msg.ResponseCode;
import com.pig4cloud.pigx.ccxxicu.common.utils.IpUtil;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisPatientService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author bolei
 * @Date 2019/8/23 14:02
 * @Version 1.0
 * @Des 描述
 */


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/his" )
@Api(value = "his", tags = "his对接接口")
public class HisController {

private final HisPatientService hisPatientService;


	/**
	 * 演示
	 * @return
	 */
	@ApiOperation(value = "demo", notes = "演示")
	@GetMapping("/demo" )
	public R get(HttpServletRequest request) {

		//127
		String userIP = IpUtil.getUserIP(request);
		System.out.println(userIP);
		//内网ip
		String ipAddress = IpUtil.getIpAddr(request);
		System.out.println(ipAddress);
		String remoteIp = IpUtil.getRemoteIp(request);
		System.out.println(remoteIp);
		String userIP1 = IpUtil.getUserIP(request);
		System.out.println(userIP1);
		String serverIP = IpUtil.getServerIP();
		System.out.println(serverIP);
		//throw new BusinessException(ResponseCode.OK.getCode(),"ddd");
		 throw new ApiException(ResponseCode.OK.getCode(),ResponseCode.OK.getMessage());
	}



	/**
	 * 入科患者接口
	 * @param hisPatient
	 * @return
	 */
	@ApiOperation(value = "admissionPatient",notes = "入科患者接口")
	@Inner(value = false)
	@PostMapping("/admissionPatient")
	public R admissionPatient(@RequestBody HisPatient hisPatient){
		System.out.println(""+hisPatient);

		Boolean aBoolean = hisPatientService.hisPatientAdd(hisPatient);
		if (aBoolean){
			return R.ok(0,"患者入科成功");
		}else{
			return R.failed(1,"患者入科失败");
		}
	}

	/**
	 * 利用his中的患者id修改患者出科信息
	 * @return
	 */
	@ApiOperation(value = "利用his中的患者id修改患者出科信息",notes = "利用his中的患者id修改患者出科信息")
	@Inner(value = false)
	@PostMapping("/hisPatientId")
	public R hisPatientId(@RequestBody HisPatient hisPatient){
		System.out.println(""+hisPatient);

		Boolean aBoolean = hisPatientService.hisPatientId(hisPatient);
		if (aBoolean){
			return R.ok(200,"成功！！！");
		}else{
			return R.failed(500,"接收患者数据失败！！！！");
		}
	}





}
