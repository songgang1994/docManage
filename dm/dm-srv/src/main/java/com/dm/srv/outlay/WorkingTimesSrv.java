package com.dm.srv.outlay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.omg.CORBA.StringHolder;

import com.dm.dto.BaseDto;
import com.dm.dto.MonthHourTableDto;
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
public interface WorkingTimesSrv {

	/**
	 * 获取查询人员信息
	 * @param userId
	 * @param deptId
	 * @return list
	 */
	StaffInfoDto getStaffInfo(String userId,String[] deptId);
	/**
	 * 工时填写
	 *
	 * @param workingtimes
	 * @param projectNo
	 * @param fyYear
	 * @param dateTm
	 * @param userId
	 * @return BaseDto
	 */
	BaseDto hourEnter(BigDecimal[] workingtimes, String[] projectNo, String[] fyYear, String dateTm, String userId,
			int flug, String newuserId);


	/**
	 * 根据用户id 获得用户所属部门
	 *
	 * @param userId
	 * @return
	 */
	String getDepartByUserId(String userId);

	/**
	 * 根据部门id和年度，查询部门月度工时
	 *
	 * @param departId
	 * @param fyYear
	 * @return list
	 */
	List<MonthHourTableDto> queryMonthlyHour(String departId, String fyYear);

	/**
	 * 根据人员id和年度，查询人员月度工时
	 *
	 * @param userId
	 * @param fyYear
	 * @return list
	 */
	List<PersonMonthHourInfo> getPersonMonthHour(String userId, String fyYear);

	/**
	 * 获取导出行数据
	 * @param list
	 * @param deptName
	 * @param fyYear
	 * @return row
	 */
	List<Row> exportMonthlyHour(List<MonthHourTableDto> list,String deptName,String fyYear);

	/**
	 * 根据人员id查询工时
	 * @param userId 用户id
	 * @return list
	 */
	List<WorkingTimesDto> getWork(String userId);

	/**
	 * 根据人员id和时间查询工时
	 * @param userId 用户id
	 * @param time   时间
	 * @return list
	 */
	List<WorkingTimesDto> getDetail(String userId,String time);

	/**
	 * 根据课题编号查询工时信息
	 * @param projectNo 课题编号
	 * @return
	 */

	List<WorkingTimesDto> getProjectNoInfo(String projectNo);
	String getUserName(String userId);
	List<WorkingTimesDto> getProjectNoInfoa(String projectNo, String userId, String fyYear);


}
