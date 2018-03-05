package com.dm.dto;


public class DocumentItemSettingFormDto extends BaseDto {

	/*
     * 文档类型
     */
	private String documentType;
	/*
	 * 数据项编号
	 */
	private String[] documentItemCode;
	/*
	 * 数据项序号
	 */
	private String[] documentItemNo;
	/*
	 * 默认值
	 */
	private String[] defaultValue;
	/*
	 * 画面布局行号
	 */
	private String[] layoutRow;
	/*
	 *画面布局列号
	 */
	private String[] layoutCol;
	/*
	 * 是否必须输入
	 */
	private String[] inputRequire;
	/*
	 * 最终负责人id
	 */
	private String[] lastApprovalUserId;
	/*
	 * 是否fromto项目
	 */
	private String[] isFromToItem;



	public String[] getIsFromToItem() {
		return isFromToItem;
	}
	public void setIsFromToItem(String[] isFromToItem) {
		this.isFromToItem = isFromToItem;
	}
	public String[] getLastApprovalUserId() {
		return lastApprovalUserId;
	}
	public void setLastApprovalUserId(String[] lastApprovalUserId) {
		this.lastApprovalUserId = lastApprovalUserId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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
	public String[] getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String[] defaultValue) {
		this.defaultValue = defaultValue;
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
	public String[] getInputRequire() {
		return inputRequire;
	}
	public void setInputRequire(String[] inputRequire) {
		this.inputRequire = inputRequire;
	}





}
