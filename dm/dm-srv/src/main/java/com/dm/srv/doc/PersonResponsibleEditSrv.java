package com.dm.srv.doc;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.PersonResDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档维护
 */
public interface PersonResponsibleEditSrv {

	/**
	 * 根据登录者id获取登录者所属部门
	 * @param userId
	 * @return
	 */
	List<DepartmentInfoEntity> getDeptByUserId(String userId);

	/**
	 * 获取文档类型
	 * @return
	 */
	List<DocItemDataSourceCodeEntity> getDocSource();

	/**
	 * 获取文档状态
	 * @return
	 */
	List<ParmMstEntity> getDocStatus();
	/**
	 * 查询
	 * @param userId
	 * @param personId
	 * @param docType
	 * @param docStatus
	 * @return
	 */
	List<PersonResDto> query(String userId,String personId,String docType,String docStatus);

	/**
	 * 修改文档责任人
	 * @param docCode
	 * @param appointPersonId
	 * @param userId
	 * @return
	 */
	BaseDto resEditSubmit(String[] docCode, String appointPersonId,String userId);
}
