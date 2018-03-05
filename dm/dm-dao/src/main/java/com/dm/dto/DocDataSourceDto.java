package com.dm.dto;

import java.util.List;

import com.dm.entity.DocItemDataSourceCodeEntity;

public class DocDataSourceDto {
	/*
	 * 数据项名称
	 */
	private String documentItemName;

	private List<DocItemDataSourceCodeEntity> dataSourceList;

	public String getDocumentItemName() {
		return documentItemName;
	}

	public void setDocumentItemName(String documentItemName) {
		this.documentItemName = documentItemName;
	}

	public List<DocItemDataSourceCodeEntity> getDataSourceList() {
		return dataSourceList;
	}

	public void setDataSourceList(List<DocItemDataSourceCodeEntity> dataSourceList) {
		this.dataSourceList = dataSourceList;
	}


}
