package com.dm.srv.docitem;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.DocDefaultSettingFormDto;
import com.dm.dto.DocumentItemSettingDto;
import com.dm.dto.SearchItemsDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.DocSearchItemSettingEntity;
import com.dm.entity.ParmMstEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
public interface DocFormDefaultSrv  {

	List<DocItemsEntity> getItems();

	List<DocSearchItemSettingEntity> isCustomDefine(String userId);

	SearchItemsDto getSearchItems(String userId);

	List<DocItemDataSourceCodeEntity> getDocSource(String docCode,String docNo);

	List<ParmMstEntity> getDocStatus(String type1);

	BaseDto defaultFormSubmit(DocDefaultSettingFormDto doc,String userId);
}
