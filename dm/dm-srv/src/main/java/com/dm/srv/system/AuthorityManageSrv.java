package com.dm.srv.system;

import java.util.List;

import com.dm.dto.AuthoritymanageTreeDto;
import com.dm.dto.BaseDto;
import com.dm.entity.SysRoleEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	权限维护Srv
 */
public interface AuthorityManageSrv {
	//获取权限维护目录树数据
	AuthoritymanageTreeDto getTree(int roleId);
	//权限项保存
	BaseDto authorityUpd(String[] actionSelected,int roleId,String userId);
	//获取角色数据
	List<SysRoleEntity> getRoleList(SysRoleEntity roleInfo);
	//角色新增
	BaseDto roleAdd(SysRoleEntity roleInfo);
	//角色修改
	BaseDto roleUpd(SysRoleEntity roleInfo);
	//角色删除
	BaseDto roleDelete(String[] roleIds);
}
