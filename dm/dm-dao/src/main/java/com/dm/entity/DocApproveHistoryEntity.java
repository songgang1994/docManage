package com.dm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.dm.dto.BaseDto;

/**
 * 文档审核履历 Entity
 * @author 张丽
 *
 */

public class DocApproveHistoryEntity extends BaseDto {
    /*
     * 履历Id
     */
	private BigDecimal approveHistoryId;
	/*
	 * 文档编号
	 */
	private String documentCode;
	/*
	 * 审核日期
	 */
	private Date approveDate;
	/*
	 * 审核人
	 */
	private String approveUserId;
	/*
	 *审核意见
	 */
	private String approveOpinion;
	/*
	 * 审核状态
	 */
	private String approveStatus;
	public BigDecimal getApproveHistoryId() {
		return approveHistoryId;
	}
	public void setApproveHistoryId(BigDecimal approveHistoryId) {
		this.approveHistoryId = approveHistoryId;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public Date getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}
	public String getApproveOpinion() {
		return approveOpinion;
	}
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}



}
