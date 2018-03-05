package com.dm.dto;

import com.dm.dto.BaseDto;

public class ItemSettingUsageDto extends BaseDto {
	
	private String documentItemSourceName;
	
	private String documentItemCode;
	
	public String getDocumentItemSourceName() {
		return documentItemSourceName;
	}
	public void setDocumentItemSourceName(String documentItemSourceName) {
		this.documentItemSourceName = documentItemSourceName;
	}
	public String getDocumentItemCode() {
		return documentItemCode;
	}
	public void setDocumentItemCode(String documentItemCode) {
		this.documentItemCode = documentItemCode;
	}

}
