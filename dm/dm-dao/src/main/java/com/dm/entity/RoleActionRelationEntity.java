package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * ROLE_ACTION_RELATION  Entity
 * @author 张丽
 *
 */
public class RoleActionRelationEntity extends BaseDto{

	/*
	 * 主键（角色id）
	 */
	private int roleId;
	/*
	 * 动作id
	 */
	private String actionId;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}


}
