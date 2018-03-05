package com.dm.dto;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
public class AnnualStatisticsDto extends BaseDto{
	/*
	 * 文件类型 值
	 */
	public String itemValue;
	/*
	 * 文件类型 显示名称
	 */
	public String itemName;
	/*
	 * 数量
	 */
	public int cnt;


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
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}
