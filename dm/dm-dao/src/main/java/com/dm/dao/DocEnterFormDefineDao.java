package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DocDataSourceDto;
import com.dm.dto.DocDetailItemSettingDto;
import com.dm.dto.DocumentItemSettingDto;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocDetailItemSettingEntity;
import com.dm.entity.DocItemsEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
public interface DocEnterFormDefineDao {
	/**
	 * 查询文档类型数据
	 * @return
	 */
	DocDataSourceDto queryDocTypeInfo();

	/**
	 * 查询已定义数据项
	 * @param documentType
	 * @return
	 */
	List<DocDetailItemSettingDto> queryDefinedInfo(@Param("docType")String documentType);

	/**
	 * 获取最终审核人
	 * @param documentType
	 * @return
	 */
	List<DocumentItemSettingDto> queryLastApproveUser(@Param("docType")String documentType );

	/**
	 * 查询全部数据项
	 * @return
	 */
	List<DocItemsEntity> queryAllDefineInfo();
	/**
	 * 获取删除文档数据项
	 * @param list
	 * @return list
	 */
	List<DocItemsEntity> queryDataItem(@Param("list")List<DocDetailItemSettingDto> list);
	/**
	 * 获取删除固定数据项
	 * @param delFixList
	 * @return list
	 */
	List<DocDetailInfoEntity> getDelFixList(@Param("list")List<DocItemsEntity> delFixList);
	/**
	 * 获取删除自定义数据项
	 * @param delFixList
	 * @return list
	 */
	List<DocDetailCustomInfoEntity> getDelCustomList(@Param("list")List<DocItemsEntity> delFixList);
	/**
	 * 删除文档录入表单定义表
	 * @param docType
	 * @return int
	 */
	int delDocDetailItemSettingByDoctype(@Param("docType")String docType);
	/**
	 * 插入文档录入表单定义表
	 * @param docSettinglist
	 * @return int
	 */
	int addDocDetailItemSettingByDoctype(@Param("list")List<DocDetailItemSettingEntity> docSettinglist);
	/**
	 * 删除文档审核管理表
	 * @param docType
	 * @return int
	 */
	int delApproveManager(@Param("docType")String docType );
	/**
	 * 插入文档审核管理表
	 * @param lastApproveUser
	 * @param docType
	 * @return int
	 */
	int addApproveManager(@Param("Array")String[] lastApproveUser,@Param("docType")String docType,@Param("creator")String creator );
}
