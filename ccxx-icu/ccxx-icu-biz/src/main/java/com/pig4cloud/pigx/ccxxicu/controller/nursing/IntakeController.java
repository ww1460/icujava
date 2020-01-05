package com.pig4cloud.pigx.ccxxicu.controller.nursing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.IntakeOutPutBo;
import com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo.NursingBo;
import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.IntakeOutputRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.project.ProjectRecord;
import com.pig4cloud.pigx.ccxxicu.common.emums.DataSourceEnum;
import com.pig4cloud.pigx.ccxxicu.common.utils.SnowFlake;
import com.pig4cloud.pigx.ccxxicu.service.nursing.IntakeService;
import com.pig4cloud.pigx.ccxxicu.service.project.ProjectRecordService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: pigx
 * @description: 出入量数据
 * @author: yyj
 * @create: 2019-10-24 15:38
 **/
@RestController
@AllArgsConstructor
@RequestMapping("/intakeRecord")
@Api(value = "intakeRecord", tags = "出入量记录")
public class IntakeController {

	private final IntakeService intakeOutputRecordService;

	@Autowired
	private ProjectRecordService projectRecordService;
	/**
	 * 分页查询
	 *
	 * @param page           分页对象
	 * @param intakeOutPutBo 出入量记录
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getIntakeOutputRecordPage(Page page, IntakeOutPutBo intakeOutPutBo) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (roleCodes.get(0).equals("ROLE_ADMIN")) {
			//管理员
			return R.ok(intakeOutputRecordService.selectByPage(page, intakeOutPutBo));

		}
		intakeOutPutBo.setDeptId(user.getDeptId() + "");
		intakeOutPutBo.setNurseId(user.getId() + "");

		return R.ok(intakeOutputRecordService.selectByPage(page, intakeOutPutBo));
	}


	/**
	 * 通过id查询出入量记录
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(intakeOutputRecordService.getById(id));
	}

	/**
	 * 新增出入量记录
	 *
	 * @param intakeOutputRecord 出入量记录
	 * @return R
	 */
	@ApiOperation(value = "新增出入量记录", notes = "新增出入量记录")
	@SysLog("新增出入量记录")
	@PostMapping("/add")
	public R save(@RequestBody IntakeOutputRecord intakeOutputRecord) {

		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		if (intakeOutputRecord.getPatientId()==null) {
			return R.failed(1, "患者不可为空！");
		}

		long id = SnowFlake.getId();
		intakeOutputRecord.setCreateUserId(user.getId() + "");
		intakeOutputRecord.setDelFlag(0);
		intakeOutputRecord.setIntakeOutputId(id + "");
		intakeOutputRecord.setDeptId(user.getDeptId()+"");
		intakeOutputRecord.setSource(DataSourceEnum.INTAKE_OUTPUT.getCode());

		ProjectRecord projectRecord = new ProjectRecord();

		projectRecord.setPatientId(intakeOutputRecord.getPatientId());
		projectRecord.setProjectId(intakeOutputRecord.getProjectId());
		projectRecord.setProjectValue(intakeOutputRecord.getIntakeOutputValue()+"");
		projectRecord.setProjectSpecificRecord(intakeOutputRecord.getValueDescription());
		projectRecord.setCreateUserId(user.getId() + "");
		projectRecord.setSourceId(id+"");
		projectRecord.setSource(DataSourceEnum.INTAKE_OUTPUT.getCode());
		projectRecord.setDeptId(user.getDeptId() + "");
		boolean add = projectRecordService.add(projectRecord);
		if (!add) {
			return R.failed();
		}
		return R.ok(intakeOutputRecordService.add(intakeOutputRecord));
	}

	/**
	 * 查询某患者的出入量数据
	 * 时间  患者
	 * @param nursingBo 患者
	 * @return R
	 */
	@ApiOperation(value = "查询某患者的出入量数据", notes = "查询某患者的出入量数据")
	@SysLog("查询某患者的出入量数据")
	@PostMapping("/getMapData")
	public R getMapData(@RequestBody NursingBo nursingBo) {


		return R.ok(intakeOutputRecordService.getDateShow(nursingBo));
	}


	/**
	 * 查询某患者的出入量数据
	 * 护理记录单
	 * @param nursingBo 患者
	 * @return R
	 */
	@ApiOperation(value = "查询某患者的出入量数据", notes = "查询某患者的出入量数据")
	@SysLog("查询某患者的出入量数据")
	@PostMapping("/getReport")
	public R getReport(@RequestBody NursingBo nursingBo) {


		PigxUser user = SecurityUtils.getUser();

		List<String> roleCodes = SecurityUtils.getRoleCodes();

		if (user == null || roleCodes.isEmpty()) {

			R.failed(1, "操作有误！");

		}

		nursingBo.setDeptId(user.getDeptId()+"");


		return R.ok(intakeOutputRecordService.getReport(nursingBo));
	}


}
