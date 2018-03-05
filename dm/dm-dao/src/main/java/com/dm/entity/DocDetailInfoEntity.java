package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * 文档管理 Entity
 * @author 张丽
 *
 */

public class DocDetailInfoEntity extends BaseDto{
    /*
     * 文档编号
     */
	private String documentCode;
	/*
	 * 旧文件编号
	 */
	private String documentOldCode;
	/*
	 * 文档类型
	 */
	private String documentType;
	/*
	 * 文档名称
	 */
	private String fileContent;
	/*
	 * 担当部门
	 */
	private String deptId;
	/*
	 * 责任人
	 */
	private String director;
	/*
	 * 文档状态
	 */
	private String approvalStatus;
	/*
	 *审核意见
	 */
	private String approvalComment;
	/*
	 * 审核部门
	 */
	private String approvalDeptId;
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getDocumentOldCode() {
		return documentOldCode;
	}
	public void setDocumentOldCode(String documentOldCode) {
		this.documentOldCode = documentOldCode;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getApprovalComment() {
		return approvalComment;
	}
	public void setApprovalComment(String approvalComment) {
		this.approvalComment = approvalComment;
	}
	public String getApprovalDeptId() {
		return approvalDeptId;
	}
	public void setApprovalDeptId(String approvalDeptId) {
		this.approvalDeptId = approvalDeptId;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}



}
