package com.dm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.dm.dto.BaseDto;

/**
 * 文档数据源 Entity
 * @author 张丽
 *
 */

public class DocItemDataSourceCodeEntity extends BaseDto {

	/*
	 * 数据源编号
	 */
	private String documentItemSourceCode;
	/*
	 * 数据源名称
	 */
    private String documentItemSourceName;
     /*
      * 数据源条目序号
      */
     private BigDecimal documentItemSourceNo;
     /*
      * 数据源条目值
      */
     private String itemValue;
     /*
      * 数据源条目名称
      */
     private String itemName;
     /*
      * 数据源条目排序号
      */
     private BigDecimal itemSortNo;
     /*
      * 有效FLG
      */
     private String validFlg;


	public String getDocumentItemSourceCode() {
		return documentItemSourceCode;
	}
	public void setDocumentItemSourceCode(String documentItemSourceCode) {
		this.documentItemSourceCode = documentItemSourceCode;
	}
	public String getDocumentItemSourceName() {
		return documentItemSourceName;
	}
	public void setDocumentItemSourceName(String documentItemSourceName) {
		this.documentItemSourceName = documentItemSourceName;
	}
	public BigDecimal getDocumentItemSourceNo() {
		return documentItemSourceNo;
	}
	public void setDocumentItemSourceNo(BigDecimal documentItemSourceNo) {
		this.documentItemSourceNo = documentItemSourceNo;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getItemSortNo() {
		return itemSortNo;
	}
	public void setItemSortNo(BigDecimal itemSortNo) {
		this.itemSortNo = itemSortNo;
	}
	public String getValidFlg() {
		return validFlg;
	}
	public void setValidFlg(String validFlg) {
		this.validFlg = validFlg;
	}


}
