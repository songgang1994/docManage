package com.dm.dto;

import com.dm.entity.SysActionEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	模块:功能菜单Dto
 */
public class RoleActionDto extends SysActionEntity{
	//角色Id
	private Integer roleId;
	//菜单选择状态
	private boolean selected=false;
	//区分名称
	private String dispName;

	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
