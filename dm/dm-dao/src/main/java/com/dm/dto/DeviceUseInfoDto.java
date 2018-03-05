package com.dm.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceUseInfoDto extends BaseDto{

	 /*
     * 使用编号
     */
	private BigDecimal useNo;
	/*
	 * 设备编号
	 */
	private String deviceNo;
	/*
	 * 使用开始时间
	 */
	@JSONField (format="yyyy/MM/dd HH:mm")
	private String startDt;
	/*
	 * 使用结束时间
	 */
	@JSONField (format="yyyy/MM/dd HH:mm")
	private String endDt;
	/*
	 * 使用人id
	 */
	private String userId;
	/*
	 *备注
	 */
	private String commentInfo;
	/*
	 * 使用人姓名
	 */
	private String userName;
	public int getUseType() {
		return useType;
	}
	public void setUseType(int useType) {
		this.useType = useType;
	}
	/*
	 * 使用类型
	 */
	private int useType;
	public BigDecimal getUseNo() {
		return useNo;
	}
	public void setUseNo(BigDecimal useNo) {
		this.useNo = useNo;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommentInfo() {
		return commentInfo;
	}
	public void setCommentInfo(String commentInfo) {
		this.commentInfo = commentInfo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
