package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.RoleActionDto;
import com.dm.dto.SysMenuDto;
import com.dm.entity.SysRoleEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：权限维护Dao
 */
public interface AuthorityManageDao {

	/**
	 * 获取画面标签数据
	 * @return
	 */
	List<RoleActionDto> getRoleActionList(@Param("roleId")int roleId);

	/**
	 * 获取菜单数据
	 * @return
	 */
	List<SysMenuDto> getMenuList(@Param("roleId")int roleId);

	/**
	 * 获取权限Id集合
	 * @param roleId 角色Id
	 * @return
	 */
	List<String> getActionIdList(@Param("roleId")int roleId);

	/**
	 * 删除角色权限
	 * @param roleId 角色Id
	 * @return
	 */
	boolean actionRelationDelete(@Param("roleId")int roleId);

	/**
	 * 新增角色权限
	 * @param actionSelected 角色选中权限
	 * @param roleId 角色Id
	 * @return
	 */
	boolean actionrelationAdd(@Param("actionSelected")String[] actionSelected,@Param("roleId")int roleId,@Param("userId")String userId);

	/**
	 * 获取角色一览数据
	 * @param roleName 角色名称
	 * @param description 角色描述
	 * @return
	 */
	List<SysRoleEntity> getRoleList(@Param("roleName")String roleName,@Param("description")String description);

	/**
	 * 角色新增
	 * @param roleInfo 角色新增信息
	 * @return
	 */
	boolean roleAdd(@Param("roleInfo")SysRoleEntity roleInfo);

	/**
	 * 角色修改
	 */
	boolean roleUpd(@Param("roleInfo")SysRoleEntity roleInfo);

	/**
	 * 角色删除
	 */
	boolean roleDelete(@Param("roleIds")String[] roleIds);

	/**
	 * 根据角色Id获取员工数量
	 * @param roleIds
	 * @return
	 */
	int userCountByRoleId(@Param("roleIds")String[] roleIds);

	/**
	 * 根据角色Id删除角色权限
	 * @param roleIds
	 * @return
	 */
	boolean roleActionDelete(@Param("roleIds")String[] roleIds);
}
