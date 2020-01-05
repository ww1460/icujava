package com.pig4cloud.pigx.ccxxicu.webservice;

import java.io.Serializable;

/**
 * @Author bolei
 * @Date 2019/10/30 15:22
 * @Version 1.0
 * @Des 描述
 */


public class UserDemo implements Serializable {

	private static final long serialVersionUID = -3628469724795296287L;
	private  int id;
	private String userName;
	private String passWord;
	private String userSex;
	private String nickName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
