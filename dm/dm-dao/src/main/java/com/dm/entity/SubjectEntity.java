package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 课题M Entity
 * @author 张丽
 *
 */
public class SubjectEntity  extends BaseDto{
    /*
     * 课题编号
     */
	private String projectNo;
	/*
	 * FY年度
	 */
	private String fyYear;
	/*
	 * 主题
	 */
	private String projectName;
	/*
	 * 目标
	 */
	private String projectGoal;
	/*
	 * 课题分类
	 */
	private int projectType;
	/*
	 * 计划人工
	 */
	private BigDecimal planTimes;
	/*
	 * 内容
	 */
	private String contents;
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectGoal() {
		return projectGoal;
	}
	public void setProjectGoal(String projectGoal) {
		this.projectGoal = projectGoal;
	}
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	public BigDecimal getPlanTimes() {
		return planTimes;
	}
	public void setPlanTimes(BigDecimal planTimes) {
		this.planTimes = planTimes;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}




}
