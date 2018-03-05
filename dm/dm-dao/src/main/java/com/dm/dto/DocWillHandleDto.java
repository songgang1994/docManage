package com.dm.dto;

import com.dm.entity.DocDetailInfoEntity;

public class DocWillHandleDto extends DocDetailInfoEntity{
	//审核人名
	private String userName;
	//审核人Id
	private String approveUserId;
	//审核状态
	private String dispName;

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
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}

}
