package com.dm.dto;

import java.util.List;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public class MonthlyhourYDto {
	private String lineKey;
	private String lineVal;
	private List<MonthlyhourXDto> lines;

	public String getLineKey() {
		return lineKey;
	}
	public void setLineKey(String lineKey) {
		this.lineKey = lineKey;
	}
	public String getLineVal() {
		return lineVal;
	}
	public void setLineVal(String lineVal) {
		this.lineVal = lineVal;
	}
	public List<MonthlyhourXDto> getLines() {
		return lines;
	}
	public void setLines(List<MonthlyhourXDto> lines) {
		this.lines = lines;
	}




}
