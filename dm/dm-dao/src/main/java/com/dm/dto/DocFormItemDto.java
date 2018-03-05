package com.dm.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;

public class DocFormItemDto extends BaseDto {

	// --- 画面属性  BEGIN ----------- //
	public boolean readonly = false;
	public boolean getReadonly() { return readonly; }

	private String key;
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	private String value;
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }
	private List<String> values;
	public List<String> getValues() { return values; }
	public void setValues(List<String> values) { this.values = values; }
	// ----画面属性  END ------------- //

    /*
     * 文档类型
     */
	private String documentType;
	/*
	 * 数据项编号
	 */
	private String documentItemCode;
	/*
	 * 数据源名称
	 */
	private String documentItemSourceName;
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
	 *右侧是否存在数据
	 */
	private boolean leftLayoutCol;
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
     private BigDecimal isPublicItem;
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

     private Integer inputRequire;

     private List<DocItemDataSourceCodeEntity> dataSourceList;

     private List<ParmMstEntity> paramMstList;


     public boolean isLeftLayoutCol() {
		return leftLayoutCol;
	}
	public void setLeftLayoutCol(boolean leftLayoutCol) {
		this.leftLayoutCol = leftLayoutCol;
	}
	public List<ParmMstEntity> getParamMstList() {
		return paramMstList;
	}
	public void setParamMstList(List<ParmMstEntity> paramMstList) {
		this.paramMstList = paramMstList;
	}
	public List<DocItemDataSourceCodeEntity> getDataSourceList() {
		return dataSourceList;
     }
     public void setDataSourceList(List<DocItemDataSourceCodeEntity> dataSourceList) {
		this.dataSourceList = dataSourceList;
     }

	public Integer getInputRequire() {
		return inputRequire;
	}
	public void setInputRequire(Integer inputRequire) {
		this.inputRequire = inputRequire;
	}
	/*
      * 是否from_to项目
      */
     private BigDecimal isFromToItem;
	public String getDocumentItemSourceName() {
		return documentItemSourceName;
	}
	public void setDocumentItemSourceName(String documentItemSourceName) {
		this.documentItemSourceName = documentItemSourceName;
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
	public BigDecimal getIsPublicItem() {
		return isPublicItem;
	}
	public void setIsPublicItem(BigDecimal isPublicItem) {
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
