/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.ccxxicu.api.entity.newlogin;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pigx.common.core.constant.enums.RfidTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * mac地址与护士RFID的关系
 *
 * @author 崔洪振
 * @date 2019-09-06 15:03:28
 */
@Data
@TableName("mac_rfid_user_relation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "mac地址与护士RFID的关系")
public class MacRfidUserRelation extends Model<MacRfidUserRelation> {
private static final long serialVersionUID = 1L;

    /**
     * mac自增编号
     */
    @TableId
    @ApiModelProperty(value="mac自增编号")
    private Integer id;
    /**
     * mac地址信息
     */
    @ApiModelProperty(value="mac地址信息")
    private String macAddress;
    /**
     * 护士的RFID值
     */
    @ApiModelProperty(value="护士的RFID值")
    private String rfidId;
    /**
     * 护士的名字
     */
    @ApiModelProperty(value="护士的名字")
    private String nurseName;
    /**
     * 护士编号
     */
    @ApiModelProperty(value="护士编号")
    private String nurseId;
    /**
     * 护士RFID上传时间
     */
    @ApiModelProperty(value="护士RFID上传时间")
    private LocalDateTime rfidTime;

	/**
	 * 终端内网ip地址
	 */
	@ApiModelProperty(value="终端内网ip地址")
	private String terminalIntranetIp;

	/**
	 * Rfid的类型
	 */
	@ApiModelProperty(value="Rfid的类型")
	private String rfidType;

	/**
	 * 护士、病人、药、等的状态，可用来做监控
	 */
	@ApiModelProperty(value="状态  0：在；1：离开")
	private Integer status;

	private RfidTypeEnum rfidTypeEnum;
}
