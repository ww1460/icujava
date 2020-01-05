package com.pig4cloud.pigx.ccxxicu.api.vo.drug;

import com.pig4cloud.pigx.ccxxicu.api.entity.drug.EasilyConfusedCatalog;
import com.pig4cloud.pigx.ccxxicu.api.entity.drug.EasilyConfusedParticular;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: pigx
 * @description: 易混淆药品展示
 * @author: yyj
 * @create: 2019-09-19 14:25
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士个人信息 NurseInfo")
public class EasilyConfusedVo extends EasilyConfusedCatalog {

	/**
	 * 易混淆药品详情
	 */
	@ApiModelProperty(value = "易混淆药品详情")
	List<EasilyConfusedParticular> easilyConfusedParticulars;



}
