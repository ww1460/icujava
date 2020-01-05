package com.pig4cloud.pigx.ccxxicu.controller.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdviceExt;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDeptVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisDoctorsAdviceExtVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisNurseVo;
import com.pig4cloud.pigx.ccxxicu.api.vo.His.HisPatientVo;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDataService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/hisData" )
@Api(value = "hisData", tags = "我方去请求his接口中的数据")
public class HisDataController {




	private final HisDataService hisDataService;

	/**
	 * his科室查询
	 * @return
	 */
	@ApiOperation(value = "his科室查询",notes = "his科室查询")
	@SysLog("his科室查询")
	@GetMapping("/hisDept")
	public R hisDept(HisDeptVo hisDeptVo){

		Boolean aBoolean = hisDataService.hisDept(hisDeptVo);
		if (aBoolean){
			return R.ok(200,"成功");
		}else{
			return R.failed(500,"失败！");
		}

	}



	/**
	 * his护士查询
	 * @return
	 */
	@ApiOperation(value = "his护士查询",notes = "his护士查询")
	@SysLog("his护士查询")
	@GetMapping("/hisNurse")
	public R hisNurse(HisNurseVo hisNurseVo){
		Boolean aBoolean = hisDataService.hisNurse(hisNurseVo);
		if (aBoolean){
			return R.ok(200,"成功");
		}else{
			return R.failed(500,"失败！");
		}

	}


	/**
	 * his患者查询
	 * @return
	 */
	@ApiOperation(value = "his患者查询",notes = "his患者查询")
	@SysLog("his患者查询")
	@GetMapping("/hisPatient")
	public R hisPatient(HisPatientVo hisPatientVo) {
		Boolean aBoolean = hisDataService.hisPatient(hisPatientVo);
		if (aBoolean){
			return R.ok(200,"成功");
		}else{
			return R.failed(500,"失败！");
		}
	}




	/**
	 * hi医嘱查询
	 * @return
	 */
	@ApiOperation(value = "his医嘱查询",notes = "his医嘱查询")
	@SysLog("his医嘱查询")
	@GetMapping("/hisDoctorsAdvice")
	public R hisDoctorsAdvice(String patientId){

		Boolean aBoolean = hisDataService.hisDoctorsAdvice(patientId);
		if (aBoolean){
			return R.ok(200,"成功");
		}else{
			return R.failed(500,"失败！");
		}
	}

	/**
	 * 医嘱执行情况查询
	 */
	@ApiOperation(value = "医嘱执行情况查询",notes = "医嘱执行情况查询")
	@SysLog("医嘱执行情况查询")
	@GetMapping("/hisDoctorsAdviceExt")
	public R hisDoctorsAdviceExt(HisDoctorsAdviceExtVo hisDoctorsAdviceExtVo){

		Boolean aBoolean = hisDataService.hisDoctorsAdviceExt(hisDoctorsAdviceExtVo);
		if (aBoolean){
			return R.ok(200,"成功");
		}else{
			return R.failed(500,"失败！");
		}
	}

	/**
	 * 医嘱执行情况修改
	 */
	@ApiOperation(value = "医嘱执行情况修改",notes = "医嘱执行情况修改")
	@SysLog("医嘱执行情况修改")
	@GetMapping("/hisDoctorsAdviceExtUpdate")
	public R hisDoctorsAdviceExtUpdate(HisDoctorsAdviceExt hisDoctorsAdviceExt){

		Boolean aBoolean = hisDataService.updateDoctorsAdviceExt(hisDoctorsAdviceExt);
		if (aBoolean){
			return R.ok(200,"成功");
		}else{
			return R.failed(500,"失败！");
		}
	}







}
