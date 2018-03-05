package com.dm.srv.doc;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.DocBatchCheckTableDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.ParmMstEntity;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档维护
 */
public interface DocBatchCheckSrv {
	/**
	 * 获取所有文档类型
	 *
	 * @return
	 */
	List<DocItemsEntity> queryDocItem();

	DocItemsEntity getDocItemEntity(String docItemCode);

	/**
	 * 查询文档类型
	 *
	 * @param docStatus
	 * @return
	 */
	List<ParmMstEntity> queryDocType(String docStatus);

	/**
	 * 获取登录人所属部门信息
	 *
	 * @param userId
	 * @return 部门信息
	 */
	List<DepartmentInfoEntity> queryDeptListByUserId(String userId);

	/**
	 * 获取所属部门信息
	 *
	 * @param 所属部门ID
	 * @return 部门信息
	 */
	List<DepartmentInfoEntity> queryDeptListByDeptId(List<DepartmentInfoEntity> subDept);

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
	List<DocBatchCheckTableDto> batchCheckInit(List<DepartmentInfoEntity> subDept,
			String docCode, String docType, String deptId, String approvalStatus, String director, String userId);

	/**
	 * 获取文件内容
	 *
	 * @param docCode
	 *            文档编号
	 * @param fileNo
	 *            文件序号
	 * @param fileType
	 *            文件类型
	 * @return 文件信息
	 */
	DocFileEntity queryFileInfo(String docCode, String fileNo, String fileType);

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
	 * @return baseDto
	 */
	BaseDto passOrRet(String[] docCode, String[] updateTime,String flag, String comment, String userId);
}
