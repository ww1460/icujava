package com.pig4cloud.pigx.ccxxicu.api.vo.nurseVo;

import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士个人信息 NurseInfo")
public class NurseInfo extends Nurse {

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 随机盐
	 */
	@ApiModelProperty(value = "随机盐")
	private String salt;

	/**
	 * 微信openid
	 */
	@ApiModelProperty(value = "微信openid")
	private String wxOpenid;

	/**
	 * QQ openid
	 */
	@ApiModelProperty(value = "QQ openid")
	private String qqOpenid;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;


	/**
	 * 锁定标记
	 */
	@ApiModelProperty(value = "锁定标记")
	private String lockFlag;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phone;
	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String avatar;

	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "部门ID")
	private String deptId;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private Integer tenantId;

	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "部门名称")
	private String deptName;

}
