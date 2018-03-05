package com.dm.dto;

import com.dm.entity.UserEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：共通pop
 */
public class StaffDeptInfoDto extends UserEntity{
	/*
	 * 部门id
	 */
	private String deptId;
	/*
	 * 部门名
	 */
	private String deptName;
	/*
	 * 是否负责人
	 */
	private String leaderFlg;


	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getLeaderFlg() {
		return leaderFlg;
	}
	public void setLeaderFlg(String leaderFlg) {
		this.leaderFlg = leaderFlg;
	}
}
