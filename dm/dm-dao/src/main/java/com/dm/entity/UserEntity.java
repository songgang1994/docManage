package com.dm.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.dm.dto.BaseDto;

/**
 * 用户信息 Entity
 * @author  张丽
 *
 */

public class UserEntity extends BaseDto   {
	/*
	 * 用户id
	 */
	private String userId;
	/*
	 * 用户类型
	 */
	private String userType;
	/*
	 * 域账号
	 */
	private String userCode;
	/*
	 * 用户名
	 */
	private String userName;
	/*
	 * 密码
	 */
	private String pwd;
	/*
	 * 邮箱
	 */
	private String email;
	/*
	 * version
	 */
	private BigInteger version;
	/*
	 * 删除flg
	 */
	private String deleteFlg;

	public String getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigInteger getVersion() {
		return version;
	}
	public void setVersion(BigInteger version) {
		this.version = version;
	}



}

