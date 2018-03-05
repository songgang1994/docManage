package com.dm.dto;

import java.util.List;

import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocFileEntity;

public class DocBatchCheckTableDto extends DocDetailInfoEntity {

	/*
	 * 审核操作
	 */
	private String approve;
	/*
	 * 审核日期
	 */
	private String approveDate;
	/*
	 * 文档状态名称
	 */
	private String dispName;
	/*
	 * 责任人名称
	 */
	private String userId;
	private String userName;
	private String documentTypeName;
	/*
	 * 所属部门名称
	 */
	private String deptName;
	/*
	 * 文档类型名称
	 */
	private String itemName;
	/*
	 * 主文件名称
	 */
	private String fileName;
	/*
	 * 主文件文件序号
	 */
	private String fileNo;
	/*
	 * 文件内容
	 */
	private String fileContent;
	/*
	 * 存在子文件，设置为"子文件..."
	 */
	private String childFileName;
	/*
	 * 子文件信息
	 */
	private List<DocFileEntity> docChildList;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
