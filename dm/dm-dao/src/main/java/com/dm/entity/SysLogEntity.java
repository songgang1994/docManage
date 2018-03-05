package com.dm.entity;
/**
 * 系统日志 Entity
 * @author 张丽
 *
 */

import java.util.Date;

import com.dm.dto.BaseDto;

public class SysLogEntity extends BaseDto{

	/*
	 * ID
	 */
	private int id;
	/*
	 * 时间
	 */
	private Date oprDatetime;
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
	private int oprContent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getOprDatetime() {
		return oprDatetime;
	}
	public void setOprDatetime(Date oprDatetime) {
		this.oprDatetime = oprDatetime;
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
	public int getOprContent() {
		return oprContent;
	}
	public void setOprContent(int oprContent) {
		this.oprContent = oprContent;
	}


}
