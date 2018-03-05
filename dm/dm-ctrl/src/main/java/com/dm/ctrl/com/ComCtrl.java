package com.dm.ctrl.com;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.ctrl.BaseCtrl;
import com.dm.ctrl.util.LoginCtrl;
import com.dm.dto.DeviceInfoDto;
import com.dm.dto.ErrCode;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.ResultDto;
import com.dm.dto.StaffDeptInfoDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.SubjectEntity;
import com.dm.srv.com.ComSrv;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：共通pop
 */
@Controller
@RequestMapping("/com")
public class ComCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(LoginCtrl.class);

	@Autowired
	private ComSrv comSrv;

	//人员检索
	@RequestMapping(value = "/staffSearch")
	@ResponseBody
	public ResultDto<StaffDeptInfoDto> getStaffDeptInfoDtos(@RequestParam("deptIds") String[] deptIds,
			@RequestParam("leaderFlg") int leaderFlg, @RequestParam("staffId") String staffId,
			@RequestParam("staffName") String staffName,@RequestParam("draw") Integer draw,
			@RequestParam("start") Integer start,@RequestParam("length") Integer length) {

		ResultDto<StaffDeptInfoDto> resultDto = new ResultDto<>();

		try {
			// 业务处理
			List<StaffDeptInfoDto> listData = comSrv.getStaffDeptInfoDtos(deptIds, leaderFlg, staffId, staffName,start,length);
			int totle = comSrv.getStaffDeptInfoDtosCount(deptIds, leaderFlg, staffId, staffName);
			resultDto.setRecordsTotal(totle);
			resultDto.setRecordsFiltered(totle);
			resultDto.setDraw(draw+1);
			// 设置业务处理结果
			resultDto.setListData(listData);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//法人检索
	@RequestMapping(value = "/legalSearch")
	@ResponseBody
	public ResultDto<ParmMstEntity> getLegal(@RequestParam("legalName") String legalName,@RequestParam("draw") Integer draw,
			@RequestParam("start") Integer start,@RequestParam("length") Integer length) {

		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		try {
			// 业务处理
			List<ParmMstEntity> listData = comSrv.getLegals(legalName,start,length);
			int totle = comSrv.getLegalsCount(legalName);
			// 设置业务处理结果
			resultDto.setRecordsTotal(totle);
			resultDto.setRecordsFiltered(totle);
			resultDto.setDraw(draw+1);
			resultDto.setListData(listData);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//部门检索
	@RequestMapping(value = "/departSearch")
	@ResponseBody
	public ResultDto<DepartmentInfoEntity> getDepart(@RequestParam("level") String level,@RequestParam("deptName") String departName,
			@RequestParam("draw") Integer draw,@RequestParam("start") Integer start,@RequestParam("length") Integer length) {

		ResultDto<DepartmentInfoEntity> resultDto = new ResultDto<>();
		try {
			// 业务处理
			List<DepartmentInfoEntity> listData = comSrv.getDeparts(level, departName,start,length);
			int totle = comSrv.getDepartsCount(level, departName);
			// 设置业务处理结果
			resultDto.setRecordsTotal(totle);
			resultDto.setRecordsFiltered(totle);
			resultDto.setDraw(draw+1);
			resultDto.setListData(listData);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//课题检索
	@RequestMapping(value = "/projectSearch")
	@ResponseBody
	public ResultDto<ProjectInfoDto> getProjectInfoDtos(@RequestParam("projectNo") String projectNo,
			@RequestParam("fyYear") String fyYear, @RequestParam("projectName") String projectName,
			@RequestParam("projectType") Integer projectType,@RequestParam("draw") Integer draw,
			@RequestParam("start") Integer start,@RequestParam("length") Integer length) {

		ResultDto<ProjectInfoDto> resultDto = new ResultDto<>();
		try {
			// 业务处理
			List<ProjectInfoDto> listData = comSrv.getProjectInfoDtos(projectNo, fyYear, projectName, projectType,start,length);
			int totle = comSrv.getProjectInfoDtosCount(projectNo, fyYear, projectName, projectType);
			// 设置业务处理结果
			resultDto.setRecordsTotal(totle);
			resultDto.setRecordsFiltered(totle);
			resultDto.setDraw(draw+1);
			resultDto.setListData(listData);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//设备检索
		@RequestMapping(value = "/deviceSearch")
		@ResponseBody
		public ResultDto<DeviceInfoDto> getdeviceInfoDtos(@RequestParam("deviceNo") String deviceNo, @RequestParam("deviceName") String deviceName,
				@RequestParam("draw") Integer draw,@RequestParam("start") Integer start,@RequestParam("length") Integer length) {
			ResultDto<DeviceInfoDto> resultDto = new ResultDto<>();
			try {
				// 业务处理
				List<DeviceInfoDto> listData = comSrv.getDeviceInfoDtos(deviceNo, deviceName,start,length);
				int totle = comSrv.getDeviceInfoDtosCount(deviceNo, deviceName);
				// 设置业务处理结果
				//设置数据总数
				resultDto.setRecordsTotal(totle);
				//设置过滤后数据总数
				resultDto.setRecordsFiltered(totle);
				//讲draw传到页面
				resultDto.setDraw(draw+1);
				resultDto.setListData(listData);
				return resultDto;
			} catch (Exception e) {
				// 出力异常信息到LOG中
				log.error(e.getMessage());
				resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
				return resultDto;
			}
		}

	//获得所有部门id和name
	@RequestMapping(value = "/getAllDept")
	@ResponseBody
	public ResultDto<DepartmentInfoEntity> getAllDept(){
		ResultDto<DepartmentInfoEntity> resultDto = new ResultDto<>();
		try {
			// 业务处理
			List<DepartmentInfoEntity> listData = comSrv.getAllDept();
			// 设置业务处理结果
			resultDto.setListData(listData);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

}
