package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: pigx
 * @description: 护士
 * @author: yyj
 * @create: 2019-10-28 17:45
 **/
@Data
public class NurseWatch {

	/**
	 * 护士表id
	 */
	@ApiModelProperty(value="护士表id")
	private String nurseId;

	/**
	 * 护士的RFID
	 */
	@ApiModelProperty(value="护士的RFID")
	private String nurseRfid;

	/**
	 * 护士真实姓名
	 */
	@ApiModelProperty(value="护士真实姓名")
	private String realName;

	/**
	 * 护士真实姓名
	 */
	@ApiModelProperty(value="护士真实姓名")
	private String userName;


}
