package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.ProjectInfoDto;
import com.dm.dto.DepartManageInfoDto;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题人工统计月度报表Dao
 */
public interface ProjectHourMonthlyReportDao {

	/**
	 * 获取课题人工统计月度报表数据
	 * @param projectInfo 查询数据
	 * @param type1 课题分类常量
	 * @return
	 */
	List<ProjectInfoDto> getProjectHourMonthlyList(@Param("projectInfo")ProjectInfoDto projectInfo,@Param("type1")String type1,@Param("type2")String type2);

	List<DepartManageInfoDto> getDeptTimeTotal(@Param("deptIdList")String[] deptIdList,@Param("projectDate")String projectDate);
}
