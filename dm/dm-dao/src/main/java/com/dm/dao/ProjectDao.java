package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.ProjectInfoDto;
import com.dm.dto.SubjectLegalDto;
import com.dm.entity.SubjectDepEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题Dao
 */
public interface ProjectDao {

	/**
	 * 获取课题一览列表数据
	 * @param projectInfo 查询条件
	 * @param type1	课题分类常量
	 * @return 返回课题一览数据
	 */
	List<ProjectInfoDto> getSubjectList(@Param("projectInfo")ProjectInfoDto projectInfo,@Param("type1")String type1,@Param("start")int start,@Param("length")int length);
	/**
	 * 获取课题一览所有列表数据
	 * @param projectInfo
	 * @param type1
	 * @param type2
	 * @return
	 */
	List<ProjectInfoDto> getSubjectListAll(@Param("projectInfo")ProjectInfoDto projectInfo,@Param("type1")String type1,@Param("type2")String type2);
	/**
	 * 获取课题总共记录数
	 * @param projectInfo
	 * @param type1
	 * @return
	 */
	int  getSubjectListCount(@Param("projectInfo")ProjectInfoDto projectInfo,@Param("type1")String type1);

	/**
	 * 新增课题
	 * @param projectForm 课题新增数据
	 * @return 新增结果
	 */
	boolean projectAdd(@Param("projectForm")ProjectInfoDto projectForm);

	/**
	 * 获取课题工时count
	 * @param fyYear 年度
	 * @param projectNo 课题编号
	 * @return 课题工时count
	 */
	int countWorking(@Param("fyYear")String fyYear,@Param("projectNo")String projectNo);

	/**
	 * 删除课题
	 * @param fyYear 年度
	 * @param projectNo 课题编号
	 * @return 删除结果
	 */
	boolean projetctDelete(@Param("fyYear")String fyYear,@Param("projectNo")String projectNo);

	/**
	 * 删除法人
	 * @param fyYear 年度
	 * @param projectNo 课题编号
	 * @return 删除法人结果
	 */
	boolean subjectLegalDelete(@Param("fyYear")String fyYear,@Param("projectNo")String projectNo);

	/**
	 * 课题部署删除
	 * @param fyYear 年度
	 * @param projectNo 课题编号
	 * @return 课题部署删除结果
	 */
	boolean subjectDepDelete(@Param("fyYear")String fyYear,@Param("projectNo")String projectNo);

	/**
	 * 指定课题信息获取
	 * @param fyYear 年度
	 * @param projectNo 课题编号
	 * @param type1 课题分类常量
	 * @return 课题详细信息
	 */
	ProjectInfoDto getSubject(@Param("fyYear")String fyYear,@Param("projectNo")String projectNo,@Param("type1")String type1);

	/**
	 * 获取课题count
	 * @param fyYear 年度
	 * @param projectNo 课题编号
	 * @return 课题count
	 */
	int projectCount(@Param("fyYear")String fyYear,@Param("projectNo")String projectNo);

	/**
	 * 修改课题
	 * @param projectForm 修改数据
	 * @return 修改结果
	 */
	boolean projectUpdate(@Param("projectForm")ProjectInfoDto projectForm);

	/**
	 * 获取法人
	 * @param type1 法人常量
	 * @return 法人名称
	 */
	List<String> getLegalList(@Param("type1")String type1);

	/**
	 * 获取课题编号
	 * @param fyYear 年度
	 * @return 课题编号
	 */
	List<String> getProjectNo(@Param("fyYear")String fyYear);

	/**
	 * 获取课题分类
	 * @param type1 课题分类常量
	 * @return 课题分类名称
	 */
	List<String> getProjectType(@Param("type1")String type1);

	/**
	 * 获取法人信息
	 * @param leals 法人名称集合
	 * @param type1 法人常量
	 * @return
	 */
	List<SubjectLegalDto> getSubjectLegal(@Param("leals")List<String> leals,@Param("type1")String type1);

	/**
	 * 获取部署id集合
	 * @return
	 */
	List<String> getDepatId();

	/**
	 * 新增课题集合
	 * @param projectList 课题数据集合
	 * @return  新增结果
	 */
	boolean projectAddList(@Param("projectList")List<ProjectInfoDto> projectList);

	/**
	 * 新增部署集合
	 * @param subjectdepList 部署数据集合
	 * @return 新增结果
	 */
	boolean subjectdepAdd(@Param("subjectdepList")List<SubjectDepEntity> subjectdepList);

	/**
	 * 新增法人
	 * @param subjectLegalList 法人数据集合
	 * @return  新增结果
	 */
	boolean subjectLegalAdd(@Param("subjectLegalList")List<SubjectLegalDto> subjectLegalList);
}
