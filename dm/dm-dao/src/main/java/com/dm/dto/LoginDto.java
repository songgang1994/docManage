package com.dm.dto;
import java.util.List;

import com.dm.entity.SysActionEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.entity.UserEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：登录画面Dto
 */
public class LoginDto extends UserEntity{
	//密码
	private String password;
	//部门信息
	private List<UserDeptEntity> userDept;
	//角色
	private List<Integer> roleIds;
	//拥有权限按钮
	private List<String> sysActions;
	//所有按钮
	private List<String> allActions;
	private String ip;

	public List<String> getAllActions() {
		return allActions;
	}
	public void setAllActions(List<String> allActions) {
		this.allActions = allActions;
	}
	public List<String> getSysActions() {
		return sysActions;
	}
	public void setSysActions(List<String> sysActions) {
		this.sysActions = sysActions;
	}
	public List<Integer> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}
	public List<UserDeptEntity> getUserDept() {
		return userDept;
	}
	public void setUserDept(List<UserDeptEntity> userDept) {
		this.userDept = userDept;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
