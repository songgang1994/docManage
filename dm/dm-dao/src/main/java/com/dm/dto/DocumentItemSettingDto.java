package com.dm.dto;

import java.util.List;

import com.dm.entity.DocItemsEntity;

public class DocumentItemSettingDto extends BaseDto {
	/*
	 * 文档数据类型
	 */
	private DocDataSourceDto docDataSource;
	/*
	 * 左侧显示项
	 */
	private List<DocDetailItemSettingDto> leftDefinedInfo;
	/*
	 * 右侧显示项
	 */
	private List<DocDetailItemSettingDto> rightDefinedInfo;
	/*
	 * 原数据项
	 */
	private List<DocItemsEntity> unDefineInfo;
	/*
	 * 最终审核人id
	 */
	private String lastApproveUser;
	/*
	 * 最终审核人姓名
	 */
	private String userName;
	public DocDataSourceDto getDocDataSource() {
		return docDataSource;
	}
	public void setDocDataSource(DocDataSourceDto docDataSource) {
		this.docDataSource = docDataSource;
	}
	public List<DocDetailItemSettingDto> getLeftDefinedInfo() {
		return leftDefinedInfo;
	}
	public void setLeftDefinedInfo(List<DocDetailItemSettingDto> leftDefinedInfo) {
		this.leftDefinedInfo = leftDefinedInfo;
	}
	public List<DocDetailItemSettingDto> getRightDefinedInfo() {
		return rightDefinedInfo;
	}
	public void setRightDefinedInfo(List<DocDetailItemSettingDto> rightDefinedInfo) {
		this.rightDefinedInfo = rightDefinedInfo;
	}
	public List<DocItemsEntity> getUnDefineInfo() {
		return unDefineInfo;
	}
	public void setUnDefineInfo(List<DocItemsEntity> unDefineInfo) {
		this.unDefineInfo = unDefineInfo;
	}
	public String getLastApproveUser() {
		return lastApproveUser;
	}
	public void setLastApproveUser(String lastApproveUser) {
		this.lastApproveUser = lastApproveUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}






}
