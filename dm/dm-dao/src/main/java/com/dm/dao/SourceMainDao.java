package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DocItemDataSourceCodeDto;
import com.dm.dto.ParmMstDto;

/**
 * NSK-NRDC业务系统
 * 作成人：张丽
 *	字典数据维护 Dao
 */
public interface SourceMainDao {

	/**
	 *查询类别
	 *@param type1Name  类型1名称
	 * @return list
	 */
	List<ParmMstDto> getType(@Param("type1Name") String type1Name);

	/**
	 *索引，查询类型名
	 *@param type1  类型1
	 * @return list
	 */
	List<ParmMstDto> getDisName(@Param("type1") String type1);

	/**
	 *新追加行数据插入
	 *@param addList  存放追加行内容的集合
	 *@param userId    登录人id
	 * @return
	 */
	//新追加行数据插入
	int addNew(@Param("list") List<ParmMstDto> addList,@Param("userId") String userId);

	/**
	 *类型数据更新
	 *@param parmMstDto
	 *@param userId    登录人id
	 * @return
	 */
	int updateSource(@Param("par") ParmMstDto parmMstDto,@Param("userId") String userId);

}
