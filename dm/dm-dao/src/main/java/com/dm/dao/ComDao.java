package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.dm.dto.DeviceInfoDto;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.StaffDeptInfoDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.SubjectEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：共通pop
 */
public interface ComDao {

	/**
	 * 人员检索
	 * @param deptIds
	 * @param leaderFlg
	 * @param staffId
	 * @param staffName
	 * @param start
	 * @param length
	 * @return 符合条件的员工信息列表
	 */
	List<StaffDeptInfoDto> getStaffDeptInfoDtos(@Param("deptIds") String[] deptIds,
			@Param("leaderFlg") int leaderFlg, @Param("staffId") String staffId,
			@Param("staffName") String staffName, @Param("start")Integer start,@Param("length") Integer length);

	/**
	 * 人员总数
	 * @param deptIds
	 * @param leaderFlg
	 * @param staffId
	 * @param staffName
	 * @param start
	 * @param length
	 * @return
	 */
	int getStaffDeptInfoDtosCount(@Param("deptIds") String[] deptIds,
			@Param("leaderFlg") int leaderFlg, @Param("staffId") String staffId,
			@Param("staffName") String staffName);


	/**
	 * 法人检索
	 * @param legalName 法人名称
	 * @return
	 */
	List<ParmMstEntity> getLegals(@Param("legalName")String legalName, @Param("start")Integer start,@Param("length") Integer length);

	/**
	 * 法人总数
	 * @param legalName 法人名称
	 * @return
	 */
	int getLegalsCount(@Param("legalName")String legalName);

	/**
	 * 部门检索
	 * @param departId 部门id
	 * @param departName 部门名称
	 * @return
	 */
	List<DepartmentInfoEntity> getDeparts(@Param("level")String level, @Param("departName")String departName,
			@Param("start")Integer start,@Param("length") Integer length);

	/**
	 * 部门总数
	 * @param departId
	 * @param departName
	 * @return
	 */
	int getDepartsCount(@Param("level")String level, @Param("departName")String departName);

	/**
	 * 课题检索
	 * @param projectNo 课题编号
	 * @param fyYear	年度
	 * @param projectName	课题名称
	 * @param projectType	课题类型
	 * @return
	 */
	List<ProjectInfoDto> getProjectInfoDtos(@Param("projectNo") String projectNo, @Param("fyYear") String fyYear,
			@Param("projectName")String projectName, @Param("projectType")Integer projectType,
			@Param("start")Integer start,@Param("length") Integer length);

	/**
	 * 课题总数
	 * @param projectNo
	 * @param fyYear
	 * @param projectName
	 * @param projectType
	 * @return
	 */
	int getProjectInfoDtosCount(@Param("projectNo") String projectNo, @Param("fyYear") String fyYear,
			@Param("projectName")String projectName, @Param("projectType")Integer projectType);

	/**
	 * 设备检索
	 * @param deviceNo 设备编号
	 * @param deviceName 设备名称
	 * @return
	 */
	List<DeviceInfoDto> getDeviceInfoDtos(@Param("deviceNo") String deviceNo, @Param("deviceName") String deviceName,
			@Param("start")Integer start,@Param("length") Integer length);

	/**
	 * 设备总数
	 * @param deviceNo 设备编号
	 * @param deviceName 设备名称
	 * @return
	 */
	int getDeviceInfoDtosCount(@Param("deviceNo") String deviceNo, @Param("deviceName") String deviceName);
	/**
	 * 获得所有部门id和name
	 * @return
	 */
	List<DepartmentInfoEntity> getAllDept();

}
