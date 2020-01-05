package com.pig4cloud.pigx.ccxxicu.api.vo.project;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import com.pig4cloud.pigx.ccxxicu.api.vo.piping.PipUseTime;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @program: pigx
 * @description: 报表和固定值
 * @author: yyj
 * @create: 2019-09-13 14:20
 **/
@Data
@ApiModel(value = "报表和固定值")
public class NursingVo {


	private List<ProjectValueVo> projectValueVos;


	private List<NursingReportVo> nursingReportVos;


	private List<Nurse> nurseName;

	private List<PipUseTime> pipUseTimes;

	private Integer hour;


}
