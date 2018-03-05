package com.dm.dto;

import java.util.List;

import com.dm.entity.UserEntity;

public class UserRoleDto extends UserEntity{

	private Integer roleId;
	private String roleName;


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}
