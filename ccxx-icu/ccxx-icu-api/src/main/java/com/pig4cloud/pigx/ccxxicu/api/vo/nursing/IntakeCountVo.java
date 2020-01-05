package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @program: pigx
 * @description: 计算出入量数据
 * @author: yyj
 * @create: 2019-10-21 10:16
 **/
@Data
@ApiModel(value = "出入量记录分页查询条件")
public class IntakeCountVo {


	private HashMap<String, List<Integer>> intake;//入量

	private HashMap<String, List<Integer>> outPut;//出量

	private List<Integer> equilibriumAmount;//平衡量


}
