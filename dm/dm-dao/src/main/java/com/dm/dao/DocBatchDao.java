package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DocBatchCheckTableDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserEntity;

public interface DocBatchDao {
	/**
	 * 获取文档类型数据项
	 *
	 * @return list
	 */
	List<DocItemsEntity> queryDocItem();

	DocItemsEntity getDocItemsEntity(@Param("docItemCode") String docItemCode);

	/**
	 * 获取所有部门信息
	 *
	 * @return list
	 */
	List<DepartmentInfoEntity> getAllDepart();

	/**
	 * 获取用户所属部门
	 *
	 * @param userId
	 * @return list
	 */
	List<DepartmentInfoEntity> queryDeptListByUserId(@Param("userId") String userId);

	/**
	 * 获取所属部门信息
	 *
	 * @param subDept
	 * @return list
	 */
	List<DepartmentInfoEntity> queryDeptListByDeptId(@Param("list") List<DepartmentInfoEntity> subDept);

	/**
	 * 查询文档类型
	 *
	 * @param docStatus
	 * @return list
	 */
	List<ParmMstEntity> queryDocType(String docStatus);

	/**
	 * 获取子文件信息
	 *
	 * @param 查询信息
	 * @return list
	 */
	List<DocFileEntity> getChildFileByParentFile(@Param("list") List<DocBatchCheckTableDto> list);

	/**
	 * 获取批量查询信息
	 *
	 * @param subDept
	 *            所属部门ID
	 * @param auditDept
	 *            审核部门ID
	 * @param docCode
	 *            文档编号
	 * @param docType
	 *            文档类型
	 * @param deptId
	 *            部门id
	 * @param approvalStatus
	 *            文档状态
	 * @param director
	 *            负责人
	 * @param userId
	 *            登录人id
	 * @return 查询信息
	 */
	List<DocBatchCheckTableDto> batchCheckInit(@Param("subDept") List<DepartmentInfoEntity> subDept,
			@Param("docCode") String docCode, @Param("docType") String docType, @Param("deptId") String deptId,
			@Param("approvalStatus") String approvalStatus, @Param("director") String director,
			@Param("userId") String userId);

	/**
	 * 获取文件内容
	 *
	 * @param docCode
	 * @param fileNo
	 * @param fileType
	 * @return 文件信息
	 */
	DocFileEntity queryFileInfo(@Param("docCode") String docCode, @Param("fileNo") String fileNo,
			@Param("fileType") String fileType);

	/**
	 * 退回/通过 操作
	 *
	 * @param docCode
	 *            编号
	 * @param flag
	 *            退回/通过 标识符
	 * @param comment
	 *            评论
	 * @param userId
	 *            操作人id
	 * @return int
	 */
	int addDocApproveHistory(@Param("docCode") String[] docCode, @Param("flag") String flag,
			@Param("comment") String comment, @Param("userId") String userId);

	/**
	 * 检查是否被其他用户更新
	 *
	 * @param docCode
	 * @param sysTime
	 * @return
	 */
	DocDetailInfoEntity ckeckDocUpdateDT(@Param("docCode") String docCode);

	/**
	 * 更新审核意见
	 *
	 * @param comment
	 * @param userId
	 * @return int
	 */
	int updApproveComment(@Param("docCode") String[] docCode, @Param("comment") String comment,
			@Param("userId") String userId, @Param("sysTime") String sysTime,
			@Param("approvalStatus") int approvalStatus);

	/**
	 * 更新文档管理表的文档状态
	 *
	 * @param approveDeptId
	 * @param sysTime
	 * @param userId
	 * @return
	 */
	int updDocStatus(@Param("approveDeptId") String approveDeptId, @Param("docCode") String docCode,
			@Param("sysTime") String sysTime, @Param("userId") String userId);

	/**
	 * 根据docCode获取docType
	 *
	 * @param docType
	 * @return DocItemsEntity
	 */
	DocDetailInfoEntity getDocTypeByDocCode(@Param("docCode") String docCode);

	/**
	 * 获取上一级部门id
	 *
	 * @param userId
	 * @return
	 */
	List<DepartmentInfoEntity> getParentDeptIds(@Param("list") String[] deptIds);

	/**
	 * 根据部门id获取用户信息
	 *
	 * @param id
	 * @return
	 */
	List<UserEntity> getuserInfoByDeptId(@Param("deptIds") String[] deptId);

	/**
	 * 获取文档类型名
	 *
	 * @param docCode
	 * @return
	 */
	DocItemDataSourceCodeEntity getDocName(@Param("docCode") String docCode);

	/**
	 * 根据文档编号查询责任人信息
	 *
	 * @param deptId
	 * @return
	 */
	UserEntity getuserInfoByDirector(@Param("docCode") String docCode);

	List<String> getApprovalIds(@Param("list") String[] deptIds);

}
