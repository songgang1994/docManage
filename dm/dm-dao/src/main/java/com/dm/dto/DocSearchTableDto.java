package com.dm.dto;


import com.dm.entity.DocDetailCustomInfoEntity;

public class DocSearchTableDto extends DocDetailCustomInfoEntity {

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
	/*
	 * 文档可查看部门
	 */
	private String docReadDept;
	/*
	 * 主文件
	 */
	private String parentFile;
	/*
	 * 子文件
	 */
	private String childFile;


	public String getDocReadDept() {
		return docReadDept;
	}
	public void setDocReadDept(String docReadDept) {
		this.docReadDept = docReadDept;
	}
	public String getParentFile() {
		return parentFile;
	}
	public void setParentFile(String parentFile) {
		this.parentFile = parentFile;
	}
	public String getChildFile() {
		return childFile;
	}
	public void setChildFile(String childFile) {
		this.childFile = childFile;
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
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
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


}
