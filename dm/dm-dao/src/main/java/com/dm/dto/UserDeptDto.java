package com.dm.dto;

import com.dm.entity.UserDeptEntity;

public class UserDeptDto extends UserDeptEntity{
	/*
	 * 用户名
	 */
	private String userName;
	/*
	 * 上级部门
	 */
	private String parentDeptId;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getParentDeptId() {
		return parentDeptId;
	}

	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}

}
