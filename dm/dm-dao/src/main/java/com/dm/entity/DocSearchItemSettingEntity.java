package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 文档一览检索数据项定义 Entity
 * @author 张丽
 *
 */

public class DocSearchItemSettingEntity extends BaseDto {
	/*
	 * 用户编号
	 */
	private String userId;
	/*
	 * 数据项编号
	 */
	private String documentItemCode;
	/*
	 * 数据项序号
	 */
	private BigDecimal documentItemNo;
	/*
	 * 是否一览项目
	 */
	private BigDecimal isListItem;
	/*
	 * 是否查询项目
	 */
	private BigDecimal isSearchItem;
	/*
	 * 匹配方式
	 */
	private String  matching;
	/*
	 * 默认值
	 */
	private String defaultItemValue;
	/*
	 * 画面布局行号
	 */
	private BigDecimal layoutRow;
	/*
	 * 画面布局列号
	 */
	private BigDecimal layoutCol;
	/*
	 * numberFormat
	 */
	private String numberFormat;



	public String getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getDefaultItemValue() {
		return defaultItemValue;
	}
	public void setDefaultItemValue(String defaultItemValue) {
		this.defaultItemValue = defaultItemValue;
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
	public BigDecimal getIsListItem() {
		return isListItem;
	}
	public void setIsListItem(BigDecimal isListItem) {
		this.isListItem = isListItem;
	}
	public BigDecimal getIsSearchItem() {
		return isSearchItem;
	}
	public void setIsSearchItem(BigDecimal isSearchItem) {
		this.isSearchItem = isSearchItem;
	}
	public String getMatching() {
		return matching;
	}
	public void setMatching(String matching) {
		this.matching = matching;
	}




}
