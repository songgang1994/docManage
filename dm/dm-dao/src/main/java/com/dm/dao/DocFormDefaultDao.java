package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.SearchItemsInfoDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.DocSearchItemSettingEntity;
import com.dm.entity.ParmMstEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
public interface DocFormDefaultDao {

	List<DocItemsEntity> getAllDocItems();

	List<DocSearchItemSettingEntity> isCustomDefine(String userId);

	List<SearchItemsInfoDto> getSearchItems(String userId);

	List<ParmMstEntity> getParams();

	List<DocItemDataSourceCodeEntity> getDocSource(@Param("docCode")String docCode, @Param("docNo")String docNo);

	DocItemsEntity getDocType(@Param("docCode")String docCode);

	int delDocSearchItemSetting(String defaultId);

	int addDocSearchItemSetting(@Param("list")List<DocSearchItemSettingEntity> list);
}
