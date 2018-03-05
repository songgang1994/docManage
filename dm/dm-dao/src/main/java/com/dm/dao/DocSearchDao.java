package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DocSearchDto;
import com.dm.dto.TableSearchDto;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserDeptEntity;

public interface DocSearchDao {

	List<UserDeptEntity> getDeptIdByUserId(@Param("userId")String UserId);

	List<DocSearchDto> getQueryDataItemByUserId(String UserId);

	List<DocItemDataSourceCodeEntity> getDataSourceByCode(@Param("list")List<DocSearchDto> queryList);

	List<ParmMstEntity> getDataSourceByParm();

	List<TableSearchDto> query(@Param("selectSQL")String selectSQL,@Param("querySQL")String querySQL,@Param("userId")String userId);

	String getQueryUSers(@Param("selectSQL")String selectSQL,@Param("querySQL")String querySQL);

	DocFileEntity getFile(@Param("docCode")String docCode,@Param("fileNo")String fileNo);

	int delDocDetailInfo(String docCode);

	int delDocCustom(String docCode);

	int delDocFile(String docCode);

	int delDocViewDept(String docCode);

	DocDetailInfoEntity getdocEntity(String docCode);

	List<String> getViewDeptIds(String docCode);
}
