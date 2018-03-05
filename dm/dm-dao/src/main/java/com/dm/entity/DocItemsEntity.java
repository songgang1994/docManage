package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 文档数据项 Entity
 * @author 张丽
 *
 */

public class DocItemsEntity extends BaseDto {
	/*
	 * 数据项编号
	 */
    private String documentItemCode;
	/*
	 * 数据项序号
	 */
    private BigDecimal documentItemNo;
     /*
      * 数据项类型
      */
     private String documentItemType;
     /*
      * 数据项名称
      */
     private String documentItemName;
     /*
      * 数据源编号
      */
     private String documentItemSourceCode;
     /*
      * 最大长度
      */
     private BigDecimal maxLength;
     /*
      * 是否公共项目
      */
     private String isPublicItem;
     /*
      * 是否固定项目
      */
     private String isFixItem;
     /*
      * 数字类型
      */
     private String numberFormat;
     /*
      * 是否空白项目
      */
     private String isBlankItem;
     /*
      * DB项目字段名
      */
     private String dbItemName;
     /*
      * 是否from_to项目
      */
     private BigDecimal isFromToItem;


	public String getNumberFormat() {
		return numberFormat;
	}
	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
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
	public String getDocumentItemType() {
		return documentItemType;
	}
	public void setDocumentItemType(String documentItemType) {
		this.documentItemType = documentItemType;
	}
	public String getDocumentItemName() {
		return documentItemName;
	}
	public void setDocumentItemName(String documentItemName) {
		this.documentItemName = documentItemName;
	}
	public String getDocumentItemSourceCode() {
		return documentItemSourceCode;
	}
	public void setDocumentItemSourceCode(String documentItemSourceCode) {
		this.documentItemSourceCode = documentItemSourceCode;
	}
	public BigDecimal getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(BigDecimal maxLength) {
		this.maxLength = maxLength;
	}
	public String getIsPublicItem() {
		return isPublicItem;
	}
	public void setIsPublicItem(String isPublicItem) {
		this.isPublicItem = isPublicItem;
	}
	public String getIsFixItem() {
		return isFixItem;
	}
	public void setIsFixItem(String isFixItem) {
		this.isFixItem = isFixItem;
	}
	public String getIsBlankItem() {
		return isBlankItem;
	}
	public void setIsBlankItem(String isBlankItem) {
		this.isBlankItem = isBlankItem;
	}
	public String getDbItemName() {
		return dbItemName;
	}
	public void setDbItemName(String dbItemName) {
		this.dbItemName = dbItemName;
	}
	public BigDecimal getIsFromToItem() {
		return isFromToItem;
	}
	public void setIsFromToItem(BigDecimal isFromToItem) {
		this.isFromToItem = isFromToItem;
	}




}
