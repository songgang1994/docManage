package com.dm.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.MonthlyhourYDto;
import com.dm.dto.PersonMonthHourInfo;
import com.dm.dto.StaffInfoDto;
import com.dm.dto.WorkingTimesDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.WorkingTimesEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public interface WorkingTimesDao {

	/**
	 * 获取查询人员信息
	 * @param userId
	 * @param deptId
	 * @return list
	 */
	StaffInfoDto getStaffInfo(@Param("userId")String userId,@Param("deptIds")String[] deptId);

	/**
	 * 通过人员id查询工时信息
	 * @param userId
	 * @return list
	 */
	List<WorkingTimesDto> getWork(@Param("userId")String userId,@Param("date")String date);

	/**
	 * 通过课题编号查询工时信息
	 * @param projectNo
	 * @return list
	 */
	List<WorkingTimesDto> getProjectNoInfo(
			@Param("projectNo")String projectNo
			);

	/**
	 * 通过课题编号人员编号当前时间查询工时信息
	 */
	List<WorkingTimesDto> getProjectNoInfoa(
			@Param("projectNo")String projectNo,
			@Param("userId")String userId,
			@Param("dateTm")String dateTm,
			@Param("fyYear")String fyYear
			);

	/**
	 * 根据日期和人员id 删除工时记录
	 *
	 * @param 日期
	 * @param 人员id
	 * @return int
	 */
	int delWorkingTimesByDateAndId(@Param("dateTm") String dateTm, @Param("userId") String userId);

	/**
	 * 根据日期和人员id 课题编号 fy年度 删除工时记录
	 *
	 * @param 日期
	 * @param 人员id
	 * @param 课题编号
	 * @param fy年度
	 * @return int
	 */
	int delWorkingTimesByDateAndIdAndPjnoAndFy(
			@Param("list") List<WorkingTimesEntity> dllist,
			@Param("dateTm") String dateTm,
			@Param("userId") String userId);

	/**
	 *
	 * @param 工时信息
	 * @param 日期
	 * @param 人员id
	 * @return
	 */
	int addWorkingTimes(@Param("list") List<WorkingTimesEntity> wtlist, @Param("dateTm") String dateTm,
			@Param("userId") String userId);

	/**
	 * 根据人员id 获取部门信息
	 *
	 * @param 人员id
	 * @return list
	 */
	List<DepartmentInfoEntity> getDepartByUserId(@Param("userId")String userId);

	/**
	 * 根据部门id和年度，查询部门月度工时
	 *
	 * @param departId
	 * @param fyYear
	 * @return list
	 */
	List<MonthlyhourYDto> queryMonthlyHour(@Param("departId")String departId, @Param("fyYear")String fyYear);

	/**
	 * 根据人员id和年度，查询人员月度工时
	 *
	 * @param userId
	 * @param fyYear
	 * @return list
	 */
	List<PersonMonthHourInfo> getPersonMonthHour(@Param("userId")String userId, @Param("fyYear")String fyYear);

	/*根据人员id查询人员姓名*/

	String getUserName(@Param("userId")String userId);

}
