package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.entity.SysMenuEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	系统菜单管理Dao
 */
public interface MenuManageDao {

	/**
	 * 获取菜单数据
	 * @return
	 */
	List<SysMenuEntity> getMenuList();

	boolean menuUpd(@Param("menuList")List<SysMenuEntity> menuList);
}
