package com.dm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.dto.BaseDto;

/**
 * 设备预约信息 Entity
 * @author 张丽
 *
 */

public class DeviceReserveInfoEntity extends BaseDto {
	/*
	 * 预约编号
	 */
	private BigDecimal reserveNo;
	/*
	 * 设备编号
	 */
	private String deviceNo;
    /*
     * 预约开始时间
     */
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date startDt;
	/*
	 * 预约结束时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date endDt;
	/*
	 * 标题
	 */
	private String title;
	/*
	 * 预约人id
	 */
	private String userId;
	/*
	 * 使用目的
	 */
	private String useGoal;
	/*
	 * 颜色
	 */
	private String color;
	public BigDecimal getReserveNo() {
		return reserveNo;
	}
	public void setReserveNo(BigDecimal reserveNo) {
		this.reserveNo = reserveNo;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUseGoal() {
		return useGoal;
	}
	public void setUseGoal(String useGoal) {
		this.useGoal = useGoal;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
