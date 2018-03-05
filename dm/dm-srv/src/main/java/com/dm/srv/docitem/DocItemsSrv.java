package com.dm.srv.docitem;

import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.DocItemDataSourceCodeDto;
import com.dm.dto.DocItemsDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档数据项管理service
 */

public interface DocItemsSrv {

	//获取文档一览数据
	List<DocItemsEntity> search(String docItemsName, String docItemsType,int start,int length);

	//获取文档查询总共记录数
	int getSearchCount(String docItemsName, String docItemsType);

	BaseDto delete(String docItemCode);

//================================新建文档数据项===============================//
	//数据源选择遍历
	List<DocItemDataSourceCodeEntity> getDateList();

	//当数据项编号不为空时，查询所有数据
	DocItemsEntity getDocItem(String documentItemCode);

	//通过数据项编号修改信息（combox未改变的情况下）
	BaseDto updateDocItem(DocItemsDto DocItemsDto,String userId,String numformat);

	//删除文档数据项（修改的情况下【选中--不选中】）
	int deleteDoc(String documentItemCode);

	//查询文档数据项（修改的情况下【不选中--选中】）
	List<DocItemsDto> getDbName();

	//修改信息时插入数据
	BaseDto insertDb(DocItemsDto DocItemsDto,String userId);

	//查询最大数据编号
	DocItemsDto maxDocNo();

	//新增数据项信息
	BaseDto insertAll(DocItemsDto DocItemsDto,String userId,String numformat);

	//数据项新增修改
	BaseDto docItem(DocItemsDto DocItemsDto,String userId,String numformat);
 //================================新建文档数据项===============================//

//=====================================新建修改查询数据源区域===============================================
	//初始化时获取文档项目数据源列表
	List<DocItemDataSourceCodeDto> getInitDataSrcList();

	//获取文档项目数据源列表
	List<DocItemDataSourceCodeDto> getDataSrcList(String documentItemSourceName,int start,int length);

	// 获取文档项目数据源总共记录数
	int getDataSrcListCount(String documentItemSourceName);

	//列表数据删除
	BaseDto DataSrcListDelete(String documentItemSourceCode);

	//获取单个数据源信息
	DocItemDataSourceCodeDto getDetailDataSrc(String documentItemSourceCode);

	//数据源修改/新建
	BaseDto DataSrcAddOrEdit(String documentItemSourceName,String detail,String otherDetail,String itemVal,String documentItemSourceCode);

	//数据源删除
	BaseDto DataSrcDelete(String documentItemSourceCode,String itemVal);
//=====================================新建修改查询数据源区域结束===============================================
}
