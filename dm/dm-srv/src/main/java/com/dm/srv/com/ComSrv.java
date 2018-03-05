package com.dm.srv.com;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.dm.dto.BaseDto;
import com.dm.dto.DeviceInfoDto;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.StaffDeptInfoDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DeviceInfoEntity;
import com.dm.entity.DeviceReserveInfoEntity;
import com.dm.entity.DeviceUseInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.SubjectEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：共通pop
 */
public interface ComSrv {

	/**
	 * 人员检索
	 * @param deptIds	部门id
	 * @param leaderFlg	是否是领导
	 * @param staffId	员工id
	 * @param staffName	员工姓名
	 * @param start		后台分页开始条数
	 * @param length	后台分页每条条数
	 * @return	符合条件的员工信息列表
	 */
	List<StaffDeptInfoDto> getStaffDeptInfoDtos(String[] deptIds, int leaderFlg, String staffId, String staffName,Integer start,Integer length);

	/**
	 * 人员总数
	 * @param deptIds	部门id
	 * @param leaderFlg	是否是领导
	 * @param staffId	员工id
	 * @param staffName	员工姓名
	 * @param start		后台分页开始条数
	 * @param length	后台分页每条条数
	 * @return	符合条件的员工信息列表
	 */
	int getStaffDeptInfoDtosCount(String[] deptIds, int leaderFlg, String staffId, String staffName);

	/**
	 * 法人检索
	 * @param legalName 法人名称
	 * @param start		后台分页开始条数
	 * @param length	后台分页每条条数
	 * @return
	 */
	List<ParmMstEntity> getLegals(String legalName,Integer start,Integer length);

	/**
	 * 法人总数
	 * @param legalName 法人名称
	 * @return
	 */
	int getLegalsCount(String legalName);

	/**
	 * 部门检索
	 * @param departId 部门id
	 * @param departName 部门名称
	 * @param start		后台分页开始条数
	 * @param length	后台分页每条条数
	 * @return
	 */
	List<DepartmentInfoEntity> getDeparts(String departId, String departName,Integer start,Integer length);

	/**
	 * 部门总数
	 * @param departId
	 * @param departName
	 * @return
	 */
	int getDepartsCount(String departId, String departName);

	/**
	 * 课题检索
	 * @param projectNo 课题编号
	 * @param fyYear	年度
	 * @param projectName	课题名称
	 * @param projectType	课题类型
	 * @param start		后台分页开始条数
	 * @param length	后台分页每条条数
	 * @return
	 */
	List<ProjectInfoDto> getProjectInfoDtos(String projectNo,String fyYear, String projectName,Integer projectType,Integer start,Integer length);

	/**
	 * 课题总数
	 * @param projectNo
	 * @param fyYear
	 * @param projectName
	 * @param projectType
	 * @return
	 */
	int getProjectInfoDtosCount(String projectNo,String fyYear, String projectName,Integer projectType);
	/**
	 * 设备检索
	 * @param deviceNo 设备编号
	 * @param deviceName 设备名称
	 * @return
	 */
	List<DeviceInfoDto> getDeviceInfoDtos(String deviceNo,String deviceName,Integer start,Integer length);

	/**
	 * 设备总数
	 * @param deviceNo 设备编号
	 * @param deviceName 设备名称
	 * @return
	 */
	int getDeviceInfoDtosCount(String deviceNo,String deviceName);

	/**
	 * 获得所有部门id和name
	 * @return
	 */
	List<DepartmentInfoEntity> getAllDept();
}
