package com.dm.srv.impl.com;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.ComDao;
import com.dm.dto.DeviceInfoDto;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.StaffDeptInfoDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.SubjectEntity;
import com.dm.srv.com.ComSrv;
import com.dm.srv.impl.BaseSrvImp;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：共通pop
 */
@Service
public class ComSrvImpl extends BaseSrvImp implements ComSrv{

	@Autowired
	private ComDao comDao;

	//人员检索
	@Override
	public List<StaffDeptInfoDto> getStaffDeptInfoDtos(String[] deptIds, int leaderFlg, String staffId,
			String staffName,Integer start,Integer length) {
		return comDao.getStaffDeptInfoDtos(deptIds, leaderFlg, staffId, staffName,start,length);
	}

	//人员总数
	@Override
	public int getStaffDeptInfoDtosCount(String[] deptIds, int leaderFlg, String staffId, String staffName) {
		return comDao.getStaffDeptInfoDtosCount(deptIds, leaderFlg, staffId, staffName);
	}

	//法人检索
	@Override
	public List<ParmMstEntity> getLegals(String legalName,Integer start,Integer length) {
		return comDao.getLegals(legalName, start, length);
	}

	//法人总数
	@Override
	public int getLegalsCount(String legalName) {
		return comDao.getLegalsCount(legalName);
	}

	//部门检索
	@Override
	public List<DepartmentInfoEntity> getDeparts(String level, String departName,Integer start,Integer length) {
		return comDao.getDeparts(level, departName, start, length);
	}

	//部门总数
	@Override
	public int getDepartsCount(String departId, String departName) {
		return comDao.getDepartsCount(departId, departName);
	}

	//课题检索
	@Override
	public List<ProjectInfoDto> getProjectInfoDtos(String projectNo, String fyYear, String projectName, Integer projectType,Integer start,Integer length) {
		return comDao.getProjectInfoDtos(projectNo, fyYear, projectName, projectType, start, length);
	}

	//课题总数
	@Override
	public int getProjectInfoDtosCount(String projectNo, String fyYear, String projectName, Integer projectType) {
		return comDao.getProjectInfoDtosCount(projectNo, fyYear, projectName, projectType);
	}

	//获得所有部门id和name
	@Override
	public List<DepartmentInfoEntity> getAllDept() {
		return comDao.getAllDept();
	}

	@Override
	public List<DeviceInfoDto> getDeviceInfoDtos(String deviceNo, String deviceName,Integer start,Integer length) {
		// TODO Auto-generated method stub
		return comDao.getDeviceInfoDtos(deviceNo, deviceName,start,length);
	}

	@Override
	public int getDeviceInfoDtosCount(String deviceNo, String deviceName) {
		// TODO Auto-generated method stub
		return comDao.getDeviceInfoDtosCount(deviceNo, deviceName);
	}
}
