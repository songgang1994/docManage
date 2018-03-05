package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.UserDeptDto;
import com.dm.dto.DepartManageInfoDto;
import com.dm.entity.DepartmentInfoEntity;
/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	组织结构维护Dao
 */
public interface DepartManageDao {

	/**
	 * 获取组织结构目录树数据
	 * @return
	 */
	List<DepartmentInfoEntity> getdepartManageTree();

	/**
	 * 根据部门Id获取Count
	 * @param deptId 部门Id
	 * @return
	 */
	int departCount(@Param("deptId")String deptId);

	/**
	 * 新增部门
	 * @param departInfo 新增部门信息
	 * @return
	 */
	int departAdd(@Param("departInfo")DepartManageInfoDto departInfo);

	/**
	 * 新增部门负责
	 * @param userdeptDtos 部门负责人信息
	 * @return
	 */
	boolean userDeptAdd(@Param("userdeptDtos")List<UserDeptDto> userdeptDtos);

	/**
	 * 获取负责人
	 * @param deptId 部门Id
	 * @return
	 */
	int userCount(@Param("deptIds")List<String> deptIds);

	/**
	 * 获取部门详细信息
	 * @param departId 部门Id
	 * @return
	 */
	DepartManageInfoDto getDepartDetail(@Param("departId")String departId);

	/**
	 * 修改部门详细信息
	 * @param departInfo 部门信息
	 * @return
	 */
	boolean departUpd(@Param("departInfo")DepartManageInfoDto departInfo);

	/**
	 * 删除负责人
	 * @param departId 部门Id
	 * @return
	 */
	boolean userDeptDelete(@Param("departId")String departId);

	/**
	 * 删除部门
	 * @param departId 部门Id
	 * @return
	 */
	boolean departDelete(@Param("deptIds")List<String> deptIds);
}
