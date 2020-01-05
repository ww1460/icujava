package com.pig4cloud.pigx.ccxxicu.api.vo.illnessNursing;

import com.pig4cloud.pigx.ccxxicu.api.entity.illnessNursing.IllnessNursingState;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 病情护理项目状态
 * @author: yyj
 * @create: 2019-10-17 11:25
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "病情护理项目状态")
public class IllnessProjectStateVo extends IllnessNursingState {


	private String projectName;

	private String projectCode;

	private String projectId;


}
