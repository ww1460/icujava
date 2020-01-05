package com.pig4cloud.pigx.ccxxicu.api.Bo.hisdata;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.DispensingDrug;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: pigx
 * @description: 医嘱和MRFID关联
 * @author: yyj
 * @create: 2019-09-13 11:20
 **/
@Data
@ApiModel(value = "医嘱和MRFID关联")
@EqualsAndHashCode(callSuper = true)
public class DispensingDrugBo extends DispensingDrug {


	/**
	 * 医嘱批号
	 */
	private String batchNumber;


}
