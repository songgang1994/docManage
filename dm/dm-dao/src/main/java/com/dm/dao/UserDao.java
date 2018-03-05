package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.LoginDto;
import com.dm.entity.MenuEntity;
import com.dm.entity.SysActionEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：登录Dao
 */
public interface UserDao {

	/**
	 * 获取目录菜单
	 * @param roleIds
	 * @return
	 */
	List<MenuEntity> getMenuItemList(@Param("roleIds")List<Integer> roleIds);

	/**
	 * 获取登录用户信息
	 * @param userName
	 * @return
	 */
	List<LoginDto> getUser(@Param("userCode")String userCode);

	/**
	 * 获取权限action
	 * @param roleIds
	 * @return
	 */
	List<String> getSysActions(@Param("roleIds")List<Integer> roleIds);

	/**
	 * 获取所有按钮action
	 * @return
	 */
	List<String> getAllActions();
}
