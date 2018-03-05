package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * 文档审核管理 Entity
 * @author 张丽
 *
 */

public class ApproveManageEntity extends BaseDto {
    /*
     * 文档类型
     */
	private String documentItemType;
	/*
	 * 最终审核人
	 */
	private String lastApproveUser;

	public String getDocumentItemType() {
		return documentItemType;
	}
	public void setDocumentItemType(String documentItemType) {
		this.documentItemType = documentItemType;
	}
	public String getLastApproveUser() {
		return lastApproveUser;
	}
	public void setLastApproveUser(String lastApproveUser) {
		this.lastApproveUser = lastApproveUser;
	}



}
