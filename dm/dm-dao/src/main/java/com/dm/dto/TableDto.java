package com.dm.dto;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.entity.UserDeptEntity;

public class TableDto extends BaseDto{

	String[][] table;
	String[][] tableKey;
	String userId;
	List<UserDeptEntity> depts;
	public String[][] getTable() {
		return table;
	}

	public void setTable(String[][] table) {
		this.table = table;
	}

	public String[][] getTableKey() {
		return tableKey;
	}

	public void setTableKey(String[][] tableKey) {
		this.tableKey = tableKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<UserDeptEntity> getDepts() {
		return depts;
	}

	public void setDepts(List<UserDeptEntity> depts) {
		this.depts = depts;
	}





}
