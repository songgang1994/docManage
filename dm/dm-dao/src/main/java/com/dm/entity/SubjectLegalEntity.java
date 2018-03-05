package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 课题法人Entity
 * @author  张丽
 *
 */

public class SubjectLegalEntity extends BaseDto {
    /*
     * 课题编号
     */
	private String projectNo;
	/*
	 * FY年度
	 */
	private String fyYear;
	/*
	 * 法人id
	 */
	private int legalId;
	/*
	 * 比例
	 */
	private BigDecimal percentage;
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
	public int getLegalId() {
		return legalId;
	}
	public void setLegalId(int legalId) {
		this.legalId = legalId;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}



}
