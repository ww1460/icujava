package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.pig4cloud.pigx.ccxxicu.api.entity.bed.BedRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "床位使用时间记录查询条件")
public class BedRecordBo extends BedRecord {

	/**
	 * 查询的开始时间
	 */

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="查询的开始时间")
	private LocalDateTime firstTime;
	/**
	 * 查询的结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value="查询的结束时间")
	private LocalDateTime lastTime;

	/**
	 * 床位名
	 */
	@ApiModelProperty(value="床位名")
	private String bedName;
	/**
	 * 床位编号
	 */
	@ApiModelProperty(value="床位编号")
	private String bedCode;




}
