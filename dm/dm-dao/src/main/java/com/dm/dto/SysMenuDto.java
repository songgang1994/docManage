package com.dm.dto;

import com.dm.entity.SysMenuEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	模块:系统菜单Dto
 */
public class SysMenuDto extends SysMenuEntity{
	//功能Id
	private String actionId;
	//角色Id
	private Integer roleId;
	//菜单选择状态
	private boolean selected=false;

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
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
