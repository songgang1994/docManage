package com.dm.dto;

import java.math.BigDecimal;
import java.util.List;
import com.dm.dto.PersonMonthHourInfo;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public class MonthHourTableDto extends BaseDto{
	private String lineKey;
	private String lineVal;
	private String[] columnsVal;
	//年
	private String year;
	//月
	private String month;
	//当月工时
	private List<PersonMonthHourInfo> monthHour;
	//工时合计
	private BigDecimal timesTotal;

	public BigDecimal getTimesTotal() {
		return timesTotal;
	}
	public void setTimesTotal(BigDecimal timesTotal) {
		this.timesTotal = timesTotal;
	}
	public List<PersonMonthHourInfo> getMonthHour() {
		return monthHour;
	}
	public void setMonthHour(List<PersonMonthHourInfo> monthHour) {
		this.monthHour = monthHour;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getLineKey() {
		return lineKey;
	}
	public void setLineKey(String lineKey) {
		this.lineKey = lineKey;
	}
	public String[] getColumnsVal() {
		return columnsVal;
	}
	public void setColumnsVal(String[] columnsVal) {
		this.columnsVal = columnsVal;
	}
	public String getLineVal() {
		return lineVal;
	}
	public void setLineVal(String lineVal) {
		this.lineVal = lineVal;
	}



}
