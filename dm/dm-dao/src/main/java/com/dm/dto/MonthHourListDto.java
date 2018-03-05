package com.dm.dto;

import java.util.List;

public class MonthHourListDto {
	//课题编号集合
	private List<String> projectNos;
	//当月工时
	private MonthHourTableDto monthHour;

	public List<String> getProjectNos() {
		return projectNos;
	}
	public void setProjectNos(List<String> projectNos) {
		this.projectNos = projectNos;
	}
	public MonthHourTableDto getMonthHour() {
		return monthHour;
	}
	public void setMonthHour(MonthHourTableDto monthHour) {
		this.monthHour = monthHour;
	}
}
