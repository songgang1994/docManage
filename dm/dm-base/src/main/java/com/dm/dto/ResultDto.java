package com.dm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.ErrCode;

public class ResultDto<T extends BaseDto> implements Serializable {

	private static final long serialVersionUID = 3224662394458261790L;

	/*
	 * 业务数据（单条记录）
	 */
	private T data;
	/*
	 * 业务数据集合
	 */
	private List<T> listData = new ArrayList<>();
	/*
	 * 业务处理结果编码
	 */
	private int rtnCode = ErrCode.RTN_CODE_SUCCESS;
	/*
	 * 业务处理消息
	 */
	private String message;


	/**
	 * datatable后台分页所需返回的部分属性
	 */
	protected Integer draw;
	protected Integer recordsTotal;
	protected Integer recordsFiltered;

	public T getData() {
		return data;
	}

	public void setData(T result) {
		this.data = result;
	}

	public int getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(int rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}


}
