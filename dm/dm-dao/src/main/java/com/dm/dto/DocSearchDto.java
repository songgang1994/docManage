package com.dm.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocSearchItemSettingEntity;
import com.dm.entity.ParmMstEntity;

public class DocSearchDto extends DocSearchItemSettingEntity {

	// --- 画面属性 BEGIN ----------- //
	public boolean disabled = false;
	public boolean readonly = false;

	public boolean getDisabled() {
		return disabled;
	}

	public boolean getReadonly() {
		return readonly;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private List<String> values;

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	// ----画面属性 END ------------- //

	private String documentItemType;
	private String documentItemName;
	private String documentItemSourceCode;
	private BigDecimal maxLength;
	private String isPublicItem;
	private String isFixItem;
	private String isBlankItem;
	private String dbItemName;
	private BigDecimal isFromToItem;
	private Integer inputRequire;

	private List<DocItemDataSourceCodeEntity> dataSourceList;
	private List<ParmMstEntity> paramMstList;



	public List<ParmMstEntity> getParamMstList() {
		return paramMstList;
	}

	public void setParamMstList(List<ParmMstEntity> paramMstList) {
		this.paramMstList = paramMstList;
	}

	public Integer getInputRequire() {
		return inputRequire;
	}

	public void setInputRequire(Integer inputRequire) {
		this.inputRequire = inputRequire;
	}

	public List<DocItemDataSourceCodeEntity> getDataSourceList() {
		return dataSourceList;
	}

	public void setDataSourceList(List<DocItemDataSourceCodeEntity> dataSourceList) {
		this.dataSourceList = dataSourceList;
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
