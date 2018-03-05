package com.dm.srv.system;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.entity.SysMenuEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	系统菜单管理Server
 */
public interface MenuManageSrv {
	//获取系统菜单树目录数据
	List<SysMenuEntity> getmenuManageTree();
	//系统菜单修改保存
	BaseDto menuUpd(String[] menuIds,String[] texts,String[] sortNos,String updater);
}
