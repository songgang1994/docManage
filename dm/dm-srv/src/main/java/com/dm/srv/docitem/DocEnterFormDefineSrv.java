package com.dm.srv.docitem;

import com.dm.dto.BaseDto;
import com.dm.dto.DocumentItemSettingDto;
import com.dm.dto.DocumentItemSettingFormDto;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
public interface DocEnterFormDefineSrv  {
	/**
	 * 跳转自定义表单设置页面
	 * @return
	 */
	DocumentItemSettingDto docItemSettingInit(String docType);

	/**
	 * 自定义表单页面设置
	 * @param docForm
	 * @return BaseDto
	 */
	BaseDto docItemSettingDefine(DocumentItemSettingFormDto docForm);
}
