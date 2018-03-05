package com.dm.entity;

import java.math.BigInteger;

import com.dm.dto.BaseDto;

/**
 * USER_ROLE_RELATION Entity
 * @author 张丽
 *
 */
public class UserRoleRelationEntity extends BaseDto{

	/*
	 * userId
	 */
	private String userId;
	/*
	 * roleId
	 */
	private int roleId;
	/*
	 * version
	 */
	private BigInteger version;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public BigInteger getVersion() {
		return version;
	}
	public void setVersion(BigInteger version) {
		this.version = version;
	}



}
