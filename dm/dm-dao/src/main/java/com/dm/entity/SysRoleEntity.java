package com.dm.entity;

import com.dm.dto.BaseDto;

/**
 * SYS_ROLE Entity
 * @author 张丽
 *
 */
public class SysRoleEntity extends BaseDto{

	/*
	 * 角色Id
	 */
	private int roleId;
	/*
	 * 角色名称
	 */
	private String roleName;
	/*
	 * 角色描述
	 */
	private String description;
	/*
	 * 删除（1.删除2.未删）
	 */
	private String deleteFlg;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}


}
