package com.pig4cloud.pigx.ccxxicu.api.vo.nursing;

import com.pig4cloud.pigx.ccxxicu.api.entity.nursing.NursingRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(value = "护理记录修改展示")
public class NursingRecordShow {


	/**
	 * 护理记录
	 */
	@ApiModelProperty(value = "护理记录")
	private NursingRecord nursingRecord;
	/**
	 * 护理项目
	 */
	private List<NursingProjectVo> nursingProject;





}
