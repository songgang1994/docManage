package com.dm.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class SysLogDto extends BaseDto{

	/*
	 * ID
	 */
	private int id;
	/*
	 * 时间
	 */
	@JSONField(format="yyyy/MM/dd HH:mm")
	private Date oprDatetime;
	/*
	 * 使用开始时间
	 */
	@JSONField(format="yyyy/MM/dd")
	private String startDt;
	/*
	 * 使用结束时间
	 */
	@JSONField(format="yyyy/MM/dd")
	private String endDt;
	/*
	 * 用户id
	 */
	private String userId;
	/*
	 * ip
	 */
	private String ip;
	/*
	 * 文件编号
	 */
	private String documentCode;
	/*
	 * 操作内容
	 */
	private String oprContent;
	/*
	 * 显示名称
	 */
	private String dispName;
	/*
	 * 用户名
	 */
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getOprContent() {
		return oprContent;
	}
	public void setOprContent(String oprContent) {
		this.oprContent = oprContent;
	}
	public Date getOprDatetime() {
		return oprDatetime;
	}
	public void setOprDatetime(Date oprDatetime) {
		this.oprDatetime = oprDatetime;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

}
