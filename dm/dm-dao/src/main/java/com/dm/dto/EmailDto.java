package com.dm.dto;

import java.util.List;

import com.dm.entity.UserEntity;

public class EmailDto extends BaseDto{

	private String docCode;
	private String docType;
	private List<UserEntity> users;
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public List<UserEntity> getUsers() {
		return users;
	}
	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}



}
