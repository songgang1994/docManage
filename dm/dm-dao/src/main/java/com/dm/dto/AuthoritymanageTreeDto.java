package com.dm.dto;

import java.util.List;
import com.dm.dto.SysMenuDto;
import com.dm.dto.RoleActionDto;
/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	模块:权限设定Dto
 */
public class AuthoritymanageTreeDto extends BaseDto{
	private List<RoleActionDto> roleActionList;
	private List<SysMenuDto> sysMenuList;
	public List<RoleActionDto> getRoleActionList() {
		return roleActionList;
	}
	public void setRoleActionList(List<RoleActionDto> roleActionList) {
		this.roleActionList = roleActionList;
	}
	public List<SysMenuDto> getSysMenuList() {
		return sysMenuList;
	}
	public void setSysMenuList(List<SysMenuDto> sysMenuList) {
		this.sysMenuList = sysMenuList;
	}
}
