package com.dm.dto;

import com.dm.entity.SubjectLegalEntity;

public class SubjectLegalDto extends SubjectLegalEntity{
	private String dispName;
	private String value;
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
