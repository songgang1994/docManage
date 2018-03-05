package com.dm.dto;

public class DocDefaultSettingFormDto {

	private String[] documentItemCode;
	private String[] documentItemNo;
	private String[] documentItemType;
	private String[] layoutRow;
	private String[] layoutCol;
	private String[] isFromToItem;
	private String[] isListItem;
	private String[] isSearchItem;
	private String[] matching;
	private String[] defaultVal;
	private String locationType;


	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String[] getDocumentItemType() {
		return documentItemType;
	}

	public void setDocumentItemType(String[] documentItemType) {
		this.documentItemType = documentItemType;
	}

	public String[] getIsFromToItem() {
		return isFromToItem;
	}

	public void setIsFromToItem(String[] isFromToItem) {
		this.isFromToItem = isFromToItem;
	}

	public String[] getDocumentItemCode() {
		return documentItemCode;
	}

	public void setDocumentItemCode(String[] documentItemCode) {
		this.documentItemCode = documentItemCode;
	}

	public String[] getDocumentItemNo() {
		return documentItemNo;
	}

	public void setDocumentItemNo(String[] documentItemNo) {
		this.documentItemNo = documentItemNo;
	}

	public String[] getLayoutRow() {
		return layoutRow;
	}

	public void setLayoutRow(String[] layoutRow) {
		this.layoutRow = layoutRow;
	}

	public String[] getLayoutCol() {
		return layoutCol;
	}

	public void setLayoutCol(String[] layoutCol) {
		this.layoutCol = layoutCol;
	}

	public String[] getIsListItem() {
		return isListItem;
	}

	public void setIsListItem(String[] isListItem) {
		this.isListItem = isListItem;
	}

	public String[] getIsSearchItem() {
		return isSearchItem;
	}

	public void setIsSearchItem(String[] isSearchItem) {
		this.isSearchItem = isSearchItem;
	}

	public String[] getMatching() {
		return matching;
	}

	public void setMatching(String[] matching) {
		this.matching = matching;
	}

	public String[] getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String[] defaultVal) {
		this.defaultVal = defaultVal;
	}

}
