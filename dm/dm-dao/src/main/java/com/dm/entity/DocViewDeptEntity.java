package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 可见部门管理 Entity
 * @author 张丽
 *
 */

public class DocViewDeptEntity extends BaseDto {
    /*
     * 文档编号
     */
	private String documentCode;
	/*
	 * 序号
	 */
	private BigDecimal No;
	/*
	 * 部门id
	 */
	private String deptId;
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public BigDecimal getNo() {
		return No;
	}
	public void setNo(BigDecimal no) {
		No = no;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}



}
