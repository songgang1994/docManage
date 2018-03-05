package com.dm.srv.util;

import java.util.List;

import com.dm.dto.ParmMstDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：共通Server
 */
public interface FindParmSrv {
	//查询课题分类
	List<ParmMstEntity> getProjectTypeList();

	//查询使用类别
	List<ParmMstEntity> getUserType();

	//查询设备信息的初始状态
	List<ParmMstEntity> getstate();

	//查询设备信息的位置
	List<ParmMstEntity> getplace();

	// 获取Item Type
	List<ParmMstEntity> listItemTypes();

	//获取法人信息
	List<ParmMstDto> getLegal();

	//获取操作内容
	List<ParmMstDto> getOperate();

	//获取文档类型
	List<DocItemDataSourceCodeEntity> getDocType();

}
