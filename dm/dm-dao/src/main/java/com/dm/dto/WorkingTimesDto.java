package com.dm.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class WorkingTimesDto extends BaseDto{

	 /*
     * 课题编号
     */
	private String projectNo;
	/*
	 * FY年度
	 */
	private String fyYear;
	/*
	 * 人员id
	 */
	private String userId;
	/*
	 * 日期
	 */
	@JSONField (format="yyyy/MM/dd")
	private Date dateTm;
	/*
	 * 工时
	 */
	private BigDecimal workingTimes;
    /*
     * 内容
     */
	private String content;

	/*
	 * 主题
	 */
	private String projectName;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDateTm() {
		return dateTm;
	}

	public void setDateTm(Date dateTm) {
		this.dateTm = dateTm;
	}

	public BigDecimal getWorkingTimes() {
		return workingTimes;
	}

	public void setWorkingTimes(BigDecimal workingTimes) {
		this.workingTimes = workingTimes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


}
