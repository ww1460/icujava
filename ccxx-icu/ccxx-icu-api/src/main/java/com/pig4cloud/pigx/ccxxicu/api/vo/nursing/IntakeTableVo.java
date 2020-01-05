package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @program: pigx
 * @description: 出入量表格展示
 * @author: yyj
 * @create: 2019-09-03 18:38
 **/
@Data
@ApiModel(value = "出入量记录分页查询条件")
public class IntakeTableVo {

	private HashMap<String, List<List<IntakeOutputShow>>> intakeShows;
	private HashMap<String, List<List<IntakeOutputShow>>> outputShows;
	private List<Integer> countNum;
	private IntakeCountVo intakeCountVo;
	private HashMap<String, String> projectName;
	private Integer startTimeHour;


}
