package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * 设备信息 Entity
 * @author 张丽
 *
 */

public class DeviceInfoEntity extends BaseDto {
	/*
	 * 设备编号
	 */
	private String deviceNo;
	/*
	 * 设备名称
	 */
	private String deviceName;
	/*
	 * 设备参数
	 */
	private String deviceParm;
	/*
	 * 设备状态
	 */
	private int deviceStatus;
	/*
	 * 位置
	 */
	private String locationId;
	/*
	 * 说明
	 */
	private String commentInfo;


	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceParm() {
		return deviceParm;
	}
	public void setDeviceParm(String deviceParm) {
		this.deviceParm = deviceParm;
	}
	public int getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getCommentInfo() {
		return commentInfo;
	}
	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}




}
