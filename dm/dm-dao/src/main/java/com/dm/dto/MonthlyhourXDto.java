package com.dm.dto;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public class MonthlyhourXDto  {
	private String columnKey;
	private MonthlyhourDto columns;
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	public MonthlyhourDto getColumns() {
		return columns;
	}
	public void setColumns(MonthlyhourDto columns) {
		this.columns = columns;
	}



}
