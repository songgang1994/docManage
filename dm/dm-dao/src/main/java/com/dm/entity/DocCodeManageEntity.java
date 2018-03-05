package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 文档编号管理 Entity
 * @author 张丽
 *
 */

public class DocCodeManageEntity extends BaseDto {
    /*
     * 文档类型
     */
	private String documentType;
	/*
	 * FY年度
	 */
	private String docunmentFy;
	/*
	 * 最后取票
	 */
	private BigDecimal lastNo;
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocunmentFy() {
		return docunmentFy;
	}
	public void setDocunmentFy(String docunmentFy) {
		this.docunmentFy = docunmentFy;
	}
	public BigDecimal getLastNo() {
		return lastNo;
	}
	public void setLastNo(BigDecimal lastNo) {
		this.lastNo = lastNo;
	}




}
