package com.dm.dto;

import com.dm.entity.DocSearchItemSettingEntity;

public class SearchItemsInfoDto extends DocSearchItemSettingEntity{

	private String documentItemName;

	private String documentItemType;
	private String isFromToItem;
	private Integer maxLength;


	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getDocumentItemName() {
		return documentItemName;
	}

	public void setDocumentItemName(String documentItemName) {
		this.documentItemName = documentItemName;
	}

	public String getDocumentItemType() {
		return documentItemType;
	}

	public void setDocumentItemType(String documentItemType) {
		this.documentItemType = documentItemType;
	}

	public String getIsFromToItem() {
		return isFromToItem;
	}

	public void setIsFromToItem(String isFromToItem) {
		this.isFromToItem = isFromToItem;
	}






}
