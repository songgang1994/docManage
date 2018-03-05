package com.dm.entity;

import java.math.BigDecimal;

import com.dm.dto.BaseDto;

/**
 * 用户所属 Entity
 * @author  张丽
 *
 */

public class UserDeptEntity extends BaseDto {
	/*
	 * userId
	 */
	private String userId;
	/*
	 * 部门id
	 */
	private String deptId;
	/*
	 * 是否负责人
	 */
	private String leaderFlg;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getLeaderFlg() {
		return leaderFlg;
	}
	public void setLeaderFlg(String leaderFlg) {
		this.leaderFlg = leaderFlg;
	}




}
