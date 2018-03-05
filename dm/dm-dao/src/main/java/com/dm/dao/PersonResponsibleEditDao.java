package com.dm.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.PersonResDto;
import com.dm.dto.RoleActionDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.SysMenuEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：权限维护Dao
 */
public interface PersonResponsibleEditDao {

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
	List<PersonResDto> query(@Param("userId") String userId, @Param("personId") String personId,
			@Param("docType") String docType, @Param("docStatus") String docStatus);

	/**
	 * 根据主文件获取所有主文件的子文件信息
	 * @param list
	 * @return
	 */
	List<DocFileEntity> getChildFileByParentFile(@Param("list")List<PersonResDto> list);

	/**
	 * 修改文档责任人
	 * @param docCode
	 * @param appointPersonId
	 * @param userId
	 * @param sysDate
	 * @return
	 */
	int resEditSubmit(@Param("docCode")String[] docCode, @Param("appointPersonId")String appointPersonId,@Param("userId")String userId,@Param("sysDate")Date sysDate);
}
