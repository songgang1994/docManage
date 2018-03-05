package com.dm.dto;

import java.util.List;

import com.dm.entity.DepartmentInfoEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public class StaffInfoDto extends BaseDto{

	private String userId;
	private String userName;
	private List<DepartmentInfoEntity> deptlist;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<DepartmentInfoEntity> getDeptlist() {
		return deptlist;
	}
	public void setDeptlist(List<DepartmentInfoEntity> deptlist) {
		this.deptlist = deptlist;
	}




}
