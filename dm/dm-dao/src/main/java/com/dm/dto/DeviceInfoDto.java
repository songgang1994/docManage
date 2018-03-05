package com.dm.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceInfoDto extends BaseDto{

	/*
	 * 设备编号
	 */
	private String deviceNo;

	/*
	 * 使用期间
	 */
	private String useTime;

	/*
	 * 使用者
	 */
	private String userName;

	/*
	 * 使用编号
	 */
	private String useNo;

	/*
	 * 使用开始时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date startDt;

	/*
	 * 使用结束时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date endDt;

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
	private String state;

	/*
	 * 位置
	 */
	private String location;

	/*
	 * 位置id
	 */
	private String locationId;

	/*
	 * 说明
	 */
	private String commentInfo;
	/*
	 * 区分MST名称
	 */
	private String dispName;
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCommentInfo() {
		return commentInfo;
	}
	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUseNo() {
		return useNo;
	}
	public void setUseNo(String useNo) {
		this.useNo = useNo;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

}
