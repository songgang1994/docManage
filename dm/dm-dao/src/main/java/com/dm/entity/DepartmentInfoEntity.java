package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * 部门 Entity
 * @author 张丽
 *
 */

public class DepartmentInfoEntity extends BaseDto{
	/*
	 * 部门id
	 */
	private String deptId;
	/*
	 * 部门名
	 */
	private String deptName;
	/*
	 * 上级部门id
	 */
	private String parentDeptId;

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getParentDeptId() {
		return parentDeptId;
	}
	public void setParentDeptId(String parentDeptId) {
		this.parentDeptId = parentDeptId;
	}



}
