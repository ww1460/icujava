package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessProjectRecord;
import com.pig4cloud.pigx.ccxxicu.api.entity.assess.AssessRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "评估记录")
public class AssessRecordBo {

	/**
	 * 评估结果
	 */
	@ApiModelProperty(value="评估结果")
	private AssessRecord assessRecord;
	/**
	 * 评估项目记录
	 */
	@ApiModelProperty(value="评估项目记录")
	private List<AssessProjectRecord> assessProjectRecords;


}
