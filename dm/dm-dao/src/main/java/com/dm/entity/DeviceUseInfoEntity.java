package com.dm.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.dto.BaseDto;

/**
 * 设备使用信息Entity
 * @author 张丽
 *
 */

public class DeviceUseInfoEntity extends BaseDto {
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
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date startDt;
	/*
	 * 使用结束时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date endDt;
	/*
	 * 使用人id
	 */
	private String userId;
	/*
	 * 使用类型
	 */
	private int useType;

	/*
	 *备注
	 */
	private String commentInfo;


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

	public int getUseType() {
		return useType;
	}
	public void setUseType(int useType) {
		this.useType = useType;
	}


}
