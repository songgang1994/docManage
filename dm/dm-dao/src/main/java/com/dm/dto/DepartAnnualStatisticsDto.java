package com.dm.dto;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.entity.DepartmentInfoEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
public class DepartAnnualStatisticsDto extends DepartmentInfoEntity{
	/*
	 * 年度
	 */
	@JSONField(format="yyyy")
	public Date years;
	/*
	 * 文件类型
	 */
	public List<AnnualStatisticsDto> annualStatisticsDtoList;

	public Date getYears() {
		return years;
	}

	public void setYears(Date years) {
		this.years = years;
	}

	public List<AnnualStatisticsDto> getAnnualStatisticsDtoList() {
		return annualStatisticsDtoList;
	}

	public void setAnnualStatisticsDtoList(List<AnnualStatisticsDto> annualStatisticsDtoList) {
		this.annualStatisticsDtoList = annualStatisticsDtoList;
	}
}
