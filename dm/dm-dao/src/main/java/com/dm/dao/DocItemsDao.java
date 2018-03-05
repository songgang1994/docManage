package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DocItemDataSourceCodeDto;
import com.dm.dto.DocItemsDto;
import com.dm.dto.ItemSettingUsageDto;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档数据项管理dao
 */

public interface DocItemsDao {

	List<DocItemsEntity> search(
			@Param("docItemsName") String docItemsName,
			@Param("docItemsType") String docItemsType,@Param("start")int start,@Param("length")int length);

	int getSearchCount(	@Param("docItemsName") String docItemsName,@Param("docItemsType") String docItemsType);

	List<ItemSettingUsageDto> itemSettingUsages(String docItemCode);

	int searchItemSettingUsage(String docItemCode);

	int delete(String docItemCode);
//==================================新建修改数据源文档==============================//
	/**
	 * 数据源选择遍历
	 * @return list
	 */
	List<DocItemDataSourceCodeEntity> getDateList();

	/**
	 * 当数据项编号不为空时，查询所有数据
	 * @param
	 * @return list
	 */
	DocItemsEntity getDocItem(@Param("documentItemCode") String documentItemCode);

	/**
	 * 通过数据项编号修改信息（combox未改变的情况下）
	 * @param DocItemsDto
	 * @param userId  登录人id
	 * @return
	 */
	int updateDocItem(@Param("doc")DocItemsDto DocItemsDto,@Param("userId") String c,@Param("numformat") String numformat);

	/**
	 * 删除文档数据项（修改的情况下【选中--不选中】）
	 * @param documentItemCode  数据项编号
	 * @return
	 */
	int deleteDoc(@Param("documentItemCode") String documentItemCode);

	/**
	 * 查询文档数据项（修改的情况下【不选中--选中】）
	 * @return list
	 */
	List<DocItemsDto> getDbName();

	/**
	 * 修改信息时插入数据
	 * @param DocItemsDto
	 * @param DBName  DB名称
	 * @param userId    登录人id
	 * @return
	 */
	int insertDb(@Param("doc")DocItemsDto DocItemsDto,@Param("DBName")String DBName,@Param("userId")String userId);

	/**
	 *查询数据库最大数据项编号
	 * @return DocItemsDto
	 */
	DocItemsDto maxDocNo();

	/**
	 *新增数据项信息（当fromto项目未选中时）
	 *@param DocItemsDto
	 *@param userId 登录人id
	 *@param Code  数据项编号
	 *@param first  itemDb名字
	 * @return
	 */
	int insertAll(@Param("doc")DocItemsDto DocItemsDto,
			@Param("userId")String userId,
			@Param("Code")String Code,
			@Param("DBName")String first,
			@Param("numformat")String numformat);

	/**
	 *新增数据项信息（当fromto项目选中时）
	 *@param DocItemsDto
	 *@param userId 登录人id
	 *@param Code  数据项编号
	 *@param second  itemDb名字
	 * @return
	 */
    int insertAlls(@Param("doc")DocItemsDto DocItemsDto,
    		@Param("userId")String userId,
    		@Param("Code")String Code,
    		@Param("DBName")String second,
    		@Param("numformat")String numformat);
//==================================新建修改数据源文档结束==============================//
//=====================================新建修改查询数据源区域===============================================
    //获取文档项目数据源列表
  	List<DocItemDataSourceCodeDto> getInitDataSrcList();

    //获取文档项目数据源列表
  	List<DocItemDataSourceCodeDto> getDataSrcList(@Param("documentItemSourceName") String documentItemSourceName,@Param("start")int start,@Param("length")int length);

    // 获取文档项目数据源总共记录数
 	int getDataSrcListCount(@Param("documentItemSourceName") String documentItemSourceName);

  	//删除数据源列表数据
  	int	DataSrcListDelete(@Param("documentItemSourceCode") String documentItemSourceCode);

  	//获取单个数据源信息
  	DocItemDataSourceCodeDto getDetailDataSrc(String documentItemSourceCode);

  	//查询最大的数据源编号
  	List<DocItemDataSourceCodeDto> getDocumentItemSourceCode();

  	//查询一个数据源编号下所有的条目值
  	List<DocItemDataSourceCodeDto> getDocumentItemSourceNo(@Param("documentItemSourceCode") String documentItemSourceCode);

  	//修改数据源
  	int updateDataSrc(@Param("ddsc") DocItemDataSourceCodeDto docItemDataSourceCodeDto);

  	//修改数据源时添加
  	int insertDataSrc(@Param("list") List<DocItemDataSourceCodeDto> addList);

  	//查询数据项
  	List<DocItemsEntity> getItems(@Param("documentItemSourceCode") String documentItemSourceCode);

  	//删除条目
  	int	deleteItem(@Param("documentItemSourceCode") String documentItemSourceCode,@Param("itemVal") String itemVal);

  	//查询文档管理表
  	List<DocDetailInfoEntity> getDetailInfo(@Param(value = "list") List<DocItemsEntity> docItemList);

  	//查询文档自定义数据项表
  	List<DocDetailCustomInfoEntity> getDetailCustomInfo(@Param(value = "list") List<DocItemsEntity> docCustomItemList);
//=====================================新建修改查询数据源区域结束===============================================

}
