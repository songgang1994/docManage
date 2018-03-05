package com.dm.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.entity.DeviceUseInfoEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
public class DeviceStatisticsDto extends DeviceUseInfoEntity{
	/*
	 * 设备名称
	 */
	public String deviceName;
	/*
	 * 年月
	 */
	@JSONField(format="yyyy-MM")
	public Date yearMonth;
	/*
	 * 当月天数转成的分钟数
	 */
	public int allTime;
	/*
	 * 使用时间单位为分钟（工作时间+维护时间）
	 */
	public int useTime;
	/*
	 * 工作时间(小时)
	 */
	public float workingTime;
	/*
	 * 维护时间（小时）
	 */
	public float maintenanceTime;
	/*
	 * 使用率
	 */
	public String usageRate;


	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public Date getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}
	public int getAllTime() {
		return allTime;
	}
	public void setAllTime(int allTime) {
		this.allTime = allTime;
	}
	public int getUseTime() {
		return useTime;
	}
	public void setUseTime(int useTime) {
		this.useTime = useTime;
	}
	public float getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(float workingTime) {
		this.workingTime = Math.round(workingTime*10)/10;
	}
	public float getMaintenanceTime() {
		return maintenanceTime;
	}
	public void setMaintenanceTime(float maintenanceTime) {
		this.maintenanceTime = Math.round(maintenanceTime*10)/10;
	}
	public String getUsageRate() {
		return usageRate;
	}
	public void setUsageRate(String usageRate) {
		this.usageRate = usageRate;
	}


}
