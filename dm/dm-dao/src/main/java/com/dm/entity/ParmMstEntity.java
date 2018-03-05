package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 区分MST Entity
 * @author 张丽
 *
 */

public class ParmMstEntity extends BaseDto {
    /*
     * 大区分
     */
	private String type1;
	/*
	 * 小区分
	 */
	private String type2;
	/*
	 * value
	 */
	private int value;
	/*
	 * 显示名称
	 */
	private String dispName;





	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}



}
