package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * 课题部署Entity
 * @author  张丽
 *
 */

public class SubjectDepEntity  extends BaseDto {
    /*
     * 课题编号
     */
	private String projectNo;
	/*
	 * FY年度
	 */
	private String fyYear;
	/*
	 * 部门id
	 */
	private String deptId;

	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getFyYear() {
		return fyYear;
	}
	public void setFyYear(String fyYear) {
		this.fyYear = fyYear;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}




}
