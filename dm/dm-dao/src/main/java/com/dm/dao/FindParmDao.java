package com.dm.dao;

import java.util.List;

import com.dm.dto.ParmMstDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：共通Dao
 */
public interface FindParmDao {
	//课题分类
	List<ParmMstEntity> getProjectTypeList();

	//设备使用类别
	List<ParmMstEntity> getUserType();

	//查询设备信息中的设备状态
	List<ParmMstEntity> getstate();

	//查询设备信息中的位置
	List<ParmMstEntity> getplace();

	// 获取文档项目类型列表
	List<ParmMstEntity> getItemTypes();

	//获取法人类别
	List<ParmMstDto>  getLegal();

	//获取操作内容
	List<ParmMstDto> getOperate();

	//获取文档类型
	List<DocItemDataSourceCodeEntity> getDocType();

}
