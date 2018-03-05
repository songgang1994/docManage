package com.dm.dto;

import java.math.BigDecimal;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public class MonthlyhourDto {

	private String tableKey;
	private BigDecimal tableVal;
	public String getTableKey() {
		return tableKey;
	}
	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}
	public BigDecimal getTableVal() {
		return tableVal;
	}
	public void setTableVal(BigDecimal tableVal) {
		this.tableVal = tableVal;
	}



}
