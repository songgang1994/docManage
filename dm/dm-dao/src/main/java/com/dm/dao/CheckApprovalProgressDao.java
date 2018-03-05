package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.entity.ApproveManageEntity;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.UserDeptEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：共通Dao
 */
public interface CheckApprovalProgressDao {

	/**
	 * 取得所属部门的所有负责人
	 * @param list 部门id
	 * @return
	 */
	List<UserDeptEntity> getLeadersByDeptId(@Param("list")List<DepartmentInfoEntity> list);

	List<DepartmentInfoEntity> queryDeptListByUserId(@Param("userId")String userId);

	/**
	 * 取得文档类型的最终审核人
	 * @param docType
	 * @return ApproveManageEntity
	 */
	List<String> getLastApproveUser(@Param("docType")String docType);

	/**
	 * 获取上一级部门信息
	 * @param deptId
	 * @return
	 */
	List<DepartmentInfoEntity> getParentDeptByDeptId(@Param("deptIds")List<String> deptId);
	/**
	 * 获取上一级部门负责人信息
	 * @param parentId
	 * @return
	 */
	List<UserDeptEntity> getLeadersByParentDeptId(@Param("parentDeptIds")List<String> parentId);
}
