package com.dm.srv.main;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.ParmMstDto;

/**
 * NSK-NRDC业务系统
 * 作成人：张丽
 *	字典数据维护service
 */
public interface SourceMainSrv {

	//查询类型
	BaseDto getType(String type1Name);

	//索引，查询类型名
	List<ParmMstDto> getDisName(String type1);

	//插入与更新类型数据
	BaseDto addNew(String type1,String nowNum,String addType,String userId,String type);
}
