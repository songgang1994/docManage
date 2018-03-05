package com.dm.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.entity.DeviceReserveInfoEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：实验设备
 */
public class DeviceReserveInfoDto extends DeviceReserveInfoEntity{
	/*
	 * 设备预约操作模式：1：新增，2：修改，3：删除
	 */
	public int pattern;
	/*
	 * 用户id
	 */
	public String userId;
	/*
	 * 用户姓名
	 */
	public String userName;
	/*
	 * 设备名称
	 */
	public String deviceName;

	public int getPattern() {
		return pattern;
	}

	public void setPattern(int pattern) {
		this.pattern = pattern;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

}
