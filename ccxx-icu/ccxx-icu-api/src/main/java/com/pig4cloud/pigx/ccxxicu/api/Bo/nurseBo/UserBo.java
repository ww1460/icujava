package com.pig4cloud.pigx.ccxxicu.api.Bo.nurseBo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pig4cloud.pigx.ccxxicu.api.entity.nurse.Nurse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "护士新增信息 UserBo")
public class UserBo extends Nurse {

	/**
	 * 科室名
	 */
	@ApiModelProperty(value = "科室名")
	private String deptName;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 随机盐
	 */
	@JsonIgnore
	@ApiModelProperty(value = "随机盐")
	private String salt;

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
	 * 角色ID
	 */
	@ApiModelProperty(value = "角色id集合")
	private List<Integer> role;

	/**
	 * 新密码
	 */
	@ApiModelProperty(value = "新密码")
	private String newpassword1;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像地址")
	private String avatar;

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
	 * 码云唯一标识
	 */
	@ApiModelProperty(value = "码云唯一标识")
	private String giteeLogin;
	/**
	 * 开源中国唯一标识
	 */
	@ApiModelProperty(value = "开源中国唯一标识")
	private String oscId;


}
