package com.dm.dto;

import java.util.List;

import com.dm.entity.DocFileEntity;

public class PersonResDto extends BaseDto{

	/*
	 * 文档编号
	 */
	private String documentCode;
	/*
	 * 文档类型
	 */
	private String documentType;
	/*
	 * 文档状态
	 */
	private String approvalStatus;
	/*
	 * 主文件名称（文档文件表）
	 */
	private String fileName;
	/*
	 * 文件名称（文档管理表）
	 */
	private String fileContent;
	/*
	 * 主文件文件序号
	 */
	private String fileNo;
	/*
	 * 存在子文件，设置为"子文件..."
	 */
	private String childFileName;
	/*
	 * 子文件信息
	 */
	private List<DocFileEntity> docChildList;
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileNo() {
		return fileNo;
	}
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	public String getChildFileName() {
		return childFileName;
	}
	public void setChildFileName(String childFileName) {
		this.childFileName = childFileName;
	}
	public List<DocFileEntity> getDocChildList() {
		return docChildList;
	}
	public void setDocChildList(List<DocFileEntity> docChildList) {
		this.docChildList = docChildList;
	}


}
