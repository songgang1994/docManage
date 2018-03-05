package com.dm.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dm.entity.DepartmentInfoEntity;
/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	组织结构维护Dto
 */
public class DepartManageInfoDto extends DepartmentInfoEntity{
	/*
	 * userId
	 */
	private String userId;
	/*
	 * 用户名
	 */
	private String userName;
	/*
	 * flag位
	 */
	private String departManageFlag;
	/*
	 * 部门工时合计
	 */
	private BigDecimal timesTotal;
	//负责人信息
	private List<UserDeptDto>  userdeptDtos;

	public BigDecimal getTimesTotal() {
		return timesTotal;
	}
	public void setTimesTotal(BigDecimal timesTotal) {
		this.timesTotal = timesTotal;
	}
	public List<UserDeptDto> getUserdeptDtos() {
		return userdeptDtos;
	}
	public void setUserdeptDtos(List<UserDeptDto> userdeptDtos) {
		this.userdeptDtos = userdeptDtos;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDepartManageFlag() {
		return departManageFlag;
	}
	public void setDepartManageFlag(String departManageFlag) {
		this.departManageFlag = departManageFlag;
	}
}
