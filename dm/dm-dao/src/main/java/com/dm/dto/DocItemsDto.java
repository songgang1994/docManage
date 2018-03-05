package com.dm.dto;

import java.math.BigDecimal;

public class DocItemsDto extends BaseDto {

	private String documentItemSourceName;
	private String docItemTypeName;
	private String itemTypeDispName;

	public String getDocumentItemSourceName() {
		return documentItemSourceName;
	}
	public void setDocumentItemSourceName(String documentItemSourceName) {
		this.documentItemSourceName = documentItemSourceName;
	}
	public String getDocItemTypeName() {
		return docItemTypeName;
	}
	public void setDocItemTypeName(String docItemTypeName) {
		this.docItemTypeName = docItemTypeName;
	}


	public String getItemTypeDispName() {
		return itemTypeDispName;
	}
	public void setItemTypeDispName(String itemTypeDispName) {
		this.itemTypeDispName = itemTypeDispName;
	}


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
     /*
      * 数字类型
      */
     private String numFormat;
     /*
      * 判断isfromto状态
      */
     private String state;
     /*
      * 判断模式
      */
     private int flag;



	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
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
