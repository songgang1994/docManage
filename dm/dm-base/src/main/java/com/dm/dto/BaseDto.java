package com.dm.dto;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.dm.dto.BizCode;

public class BaseDto {

	// ----------------- Entity 共通字段  BEGIN ------------------- //
	/**
	 * 共通消息
	 */

	protected String creator;
	protected Date createDt;
	protected String updater;
	protected Date updateDt;
	protected String deleter;
	protected Date deleteDt;


	public String getDeleter() {
		return deleter;
	}
	public void setDeleter(String deleter) {
		this.deleter = deleter;
	}
	public Date getDeleteDt() {
		return deleteDt;
	}
	public void setDeleteDt(Date deleteDt) {
		this.deleteDt = deleteDt;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}



	// ----------------- Entity 共通字段  END --------------------- //



	/*
	 * 业务执行结果CODE
	 */
	private int bizCode=BizCode.BIZ_CODE_SUCCESS;
	/*
	 * 业务执行结果消息集合
	 */
	private List<String> bizExeMsgs = new ArrayList<String>();


	public int getBizCode() {
		return bizCode;
	}
	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}
	public List<String> getBizExeMsgs() {
		return bizExeMsgs;
	}
	public void setBizExeMsgs(List<String> bizExeMsgs) {
		this.bizExeMsgs = bizExeMsgs;
	}

}
