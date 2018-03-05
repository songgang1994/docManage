package com.dm.dto;

import java.math.BigDecimal;

public class DocDetailItemSettingDto {
	 /*
     * 文档类型
     */
	private String documentType;
	/*
	 * 数据项编号
	 */
	private String documentItemCode;
	/*
	 * 数据项序号
	 */
	private BigDecimal documentItemNo;
	/*
	 * 默认值
	 */
	private String defaultValue;
	/*
	 * 画面布局行号
	 */
	private BigDecimal layoutRow;
	/*
	 *画面布局列号
	 */
	private BigDecimal layoutCol;
	/*
	 * 是否必须输入
	 */
	private BigDecimal inputRequire;
	/*
	 * 项目名称
	 */
	private String 	documentItemName;
	/*
	 * DB项目字段名
	 */
	private String dbItemName;
	/*
	 * isFromToItem项目
	 */
	private BigDecimal isFromToItem;



	public BigDecimal getIsFromToItem() {
		return isFromToItem;
	}
	public void setIsFromToItem(BigDecimal isFromToItem) {
		this.isFromToItem = isFromToItem;
	}
	public String getDbItemName() {
		return dbItemName;
	}
	public void setDbItemName(String dbItemName) {
		this.dbItemName = dbItemName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentItemCode() {
		return documentItemCode;
	}
	public void setDocumentItemCode(String documentItemCode) {
		this.documentItemCode = documentItemCode;
	}
	public BigDecimal getDocumentItemNo() {
		return documentItemNo;
	}
	public void setDocumentItemNo(BigDecimal documentItemNo) {
		this.documentItemNo = documentItemNo;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public BigDecimal getLayoutRow() {
		return layoutRow;
	}
	public void setLayoutRow(BigDecimal layoutRow) {
		this.layoutRow = layoutRow;
	}
	public BigDecimal getLayoutCol() {
		return layoutCol;
	}
	public void setLayoutCol(BigDecimal layoutCol) {
		this.layoutCol = layoutCol;
	}
	public BigDecimal getInputRequire() {
		return inputRequire;
	}
	public void setInputRequire(BigDecimal inputRequire) {
		this.inputRequire = inputRequire;
	}
	public String getDocumentItemName() {
		return documentItemName;
	}
	public void setDocumentItemName(String documentItemName) {
		this.documentItemName = documentItemName;
	}


}
