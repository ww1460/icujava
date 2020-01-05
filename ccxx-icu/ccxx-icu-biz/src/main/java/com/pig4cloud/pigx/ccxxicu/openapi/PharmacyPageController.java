package com.pig4cloud.pigx.ccxxicu.openapi;


import cn.hutool.core.bean.BeanUtil;
import com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata.DispensingDrugBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.hisdata.HisDoctorsAdvice;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import com.pig4cloud.pigx.ccxxicu.api.entity.patient.Patient;
import com.pig4cloud.pigx.ccxxicu.common.utils.IpUtil;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.hisdata.HisDoctorsAdviceService;
import com.pig4cloud.pigx.ccxxicu.service.nurse.DispensingDrugService;
import com.pig4cloud.pigx.ccxxicu.service.patient.PatientService;
import com.pig4cloud.pigx.ccxxicu.service.pharmacy.PharmacyService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/pharmacy" )
@Api(value = "pharmacy", tags = "配药页面的信息获取")
public class PharmacyPageController {

	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private DispensingDrugService dispensingDrugService;

	@Autowired
	private HisDoctorsAdviceService hisDoctorsAdviceService;

	/**
	 * 通用接口：根据IP地址获取各类的RFID
	 * @param request request
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/ip")
	@Inner(value = false)
	public R getRfidByIpAddress(HttpServletRequest request) {
		String rfidType = request.getParameter("type");
		String userIP1 = IpUtil.getUserIP(request);
		log.info("终端ip地址:"+userIP1);
		return R.ok(pharmacyService.getRfidByipAddress(userIP1, rfidType));
	}

	/**
	 * 两个动作：
	 * 1）根据IP地址获取护士的N-RFID
	 * 2）根据N-RFID获取护士的ID
	 * @param request request
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/ipaddress")
	@Inner(value = false)
	public R getById(HttpServletRequest request) {
		return R.ok(pharmacyService.getNurseIdByRfidByIp(IpUtil.getIpAddr(request)));
	}

	/**
	 * 根据护士ID获取患者列表
	 * @param nurseid nurseid
	 * @return R
	 */
	@ApiOperation(value = "通过护士id查询患者列表", notes = "通过护士id查询患者列表")
	@GetMapping("/patients/{nurseid}")
	@Inner(value = false)
	public R getPatientsByNurseId(@PathVariable("nurseid") String nurseid) {
		return R.ok(pharmacyService.getPatientListByNurseId(nurseid));
	}

	/**
	 * 根据患者ID获取医嘱ID列表
	 * @param patientid patientid
	 * @return R
	 */
	@ApiOperation(value = "根据患者ID获取医嘱ID列表", notes = "根据患者ID获取医嘱ID列表")
	@GetMapping("/advicelist/{patientid}")
	@Inner(value = false)
	public R getDoctorAdviceList(@PathVariable("patientid") String patientid) {
		return R.ok(pharmacyService.getDoctorAdviceListByPatientId(patientid));
	}

	/**
	 * 全查医嘱数据
	 * @return
	 */
	@ApiOperation(value = "全查医嘱数据",notes = "全查医嘱数据")
	@SysLog("全查医嘱数据")
	@GetMapping("/selectAll")
	public R selectAll(HisDoctorsAdvice hisDoctorsAdvice){
		return R.ok(hisDoctorsAdviceService.selectAll(hisDoctorsAdvice));
	}

	/**
	 * 根据批次号（医嘱ID）获取医嘱详情
	 * @param batchnumber batchnumber
	 * @return R
	 */
	@ApiOperation(value = "根据批次号（医嘱ID）获取医嘱详情", notes = "根据批次号（医嘱ID）获取医嘱详情")
	@GetMapping("/advicedetail/{batchnumber}")
	@Inner(value = false)
	public R getAdvicedetailByBatchNumber(@PathVariable("batchnumber" ) String batchnumber) {
		return R.ok(pharmacyService.getAdviceDetailByBatchNumber(batchnumber));
	}


	/**
	 * 配药页面将医嘱ID和MRFID绑定，写入数据库中
	 * @param dispensingDrugBo 配药
	 * @return R
	 */
	@ApiOperation(value = "配药页面将医嘱ID和MRFID绑定", notes = "配药页面将医嘱ID和MRFID绑定")
	@SysLog("配药页面将医嘱ID和MRFID绑定" )
	@PostMapping("/save")
	@Inner(value = false)
	public R saveMrfidAndDoctorAdvice(@RequestBody DispensingDrugBo dispensingDrugBo) {
		DispensingDrug dispensingDrug = new DispensingDrug();
		BeanUtil.copyProperties(dispensingDrugBo,dispensingDrug);
		dispensingDrug.setDispensingDrugId(SnowFlake.getId()+""); //雪花
		HisDoctorsAdvice batchNumber = hisDoctorsAdviceService.getBatchNumber(dispensingDrugBo.getBatchNumber());
		dispensingDrug.setDoctorsAdviceId(batchNumber.getDoctorsAdviceId());
		return R.ok(dispensingDrugService.save(dispensingDrug));
	}

	/**
	 * 第2.1步：根据患者ID获取任务列表，表：tak_task
	 * @param patientId patientId
	 * @return R
	 */
	@ApiOperation(value = "根据患者ID获取任务列表", notes = "根据患者ID获取任务列表")
	@GetMapping("/taskdisp/{patientId}")
	@Inner(value = false)
	public R getTaskListsByPatientId(@PathVariable("patientId") String patientId) {
		return R.ok(pharmacyService.getTaskListByPatientId(patientId));
	}

	/**
	 * 第2.2步：根据护理任务获取医嘱ID，表：nur_dispensing_drug
	 * @param taskId taskId
	 * @return R
	 */
	@ApiOperation(value = "根据护理任务获取医嘱ID", notes = "根据护理任务获取医嘱ID")
	@GetMapping("/advices/{taskId}")
	@Inner(value = false)
	public R getDoctorAdviceListsByTaskId(@PathVariable("taskId") String taskId) {
		return R.ok(pharmacyService.getDoctorAdviceListByTaskId(taskId));
	}

	/**
	 * 第2.3步：根据医嘱ID获取医嘱批次号，表：his_doctors_advice
	 * @param adviceId adviceId
	 * @return R
	 */
	@ApiOperation(value = "根据医嘱ID获取医嘱批次号", notes = "根据医嘱ID获取医嘱批次号")
	@GetMapping("/batchnumber/{adviceId}")
	@Inner(value = false)
	public R getBatchNumbersByDoctorAdviceId(@PathVariable("adviceId") String adviceId) {
		return R.ok(pharmacyService.getBatchNumberByDoctorAdviceId(adviceId));
	}

	/**
	 * 配药室配药页面保存功能
	 * @param dispensingDrug 配药
	 * @return R
	 */
	@ApiOperation(value = "新增配药", notes = "新增配药")
	@SysLog("新增配药" )
	@PostMapping("/add")
	public R save(@RequestBody DispensingDrug dispensingDrug) {
		return R.ok(pharmacyService.saveDispensingDrugInfo(dispensingDrug));
	}

	/**
	 * 给药途径的枚举值，表：his_doctors_advice_dictionaries
	 * @return R
	 */
	@ApiOperation(value = "根据医嘱ID获取医嘱批次号", notes = "根据医嘱ID获取医嘱批次号")
	@GetMapping("/labelenum")
	@Inner(value = false)
	public R getLabelNameValueEnum() {
		return R.ok(pharmacyService.getLabelNameValueEnums());
	}

}
