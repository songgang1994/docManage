package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * 区分主分类 Entity
 * @author 张丽
 */
public class ParmMstMainEntity extends BaseDto{

	/*
	 * 大区分
	 */
	private String type1;
	/*
	 * 大区分名
	 */
	private String type1Name;
	/*
	 * 可维护FLG
	 */
	private String editFlg;
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType1Name() {
		return type1Name;
	}
	public void setType1Name(String type1Name) {
		this.type1Name = type1Name;
	}
	public String getEditFlg() {
		return editFlg;
	}
	public void setEditFlg(String editFlg) {
		this.editFlg = editFlg;
	}



}
