package com.dm.dto;

import java.util.List;

import com.dm.entity.DocItemsEntity;
import com.dm.entity.ParmMstEntity;

public class SearchItemsDto {

	private List<SearchItemsInfoDto> leftList;
	private List<SearchItemsInfoDto> rightList;

	private List<DocItemsEntity> items;
	private List<ParmMstEntity> params;
	public List<SearchItemsInfoDto> getLeftList() {
		return leftList;
	}
	public void setLeftList(List<SearchItemsInfoDto> leftList) {
		this.leftList = leftList;
	}
	public List<SearchItemsInfoDto> getRightList() {
		return rightList;
	}
	public void setRightList(List<SearchItemsInfoDto> rightList) {
		this.rightList = rightList;
	}
	public List<DocItemsEntity> getItems() {
		return items;
	}
	public void setItems(List<DocItemsEntity> items) {
		this.items = items;
	}
	public List<ParmMstEntity> getParams() {
		return params;
	}
	public void setParams(List<ParmMstEntity> params) {
		this.params = params;
	}



}
