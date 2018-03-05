package com.dm.ctrl.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BizCode;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.dto.DepartManageInfoDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.departManage.DepartManageSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 * 组织结构维护
 */
@Controller
@RequestMapping("/system")
public class DepartManageCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(DepartManageCtrl.class);
	// 组织结构维护Service
	@Autowired
	private DepartManageSrv departManageSrv;

	// 组织结构维护画面显示
	@RequestMapping(value = "/departmanage")
	public ModelAndView init() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/DepartManage");
		return mv;
	}

	// 组织结构树目录显示
	@RequestMapping(value = "/departmanageTree")
	@ResponseBody
	public ResultDto<DepartmentInfoEntity> initTree() {
		ResultDto<DepartmentInfoEntity> resultDto = new ResultDto<DepartmentInfoEntity>();
		try {
			// 获取组织结构树数据
			List<DepartmentInfoEntity> departTree = departManageSrv.getdepartManageTree();
			resultDto.setListData(departTree);
			resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 新增部门
	@RequestMapping(value = "/departAdd", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<DepartmentInfoEntity> departAdd(@RequestBody DepartManageInfoDto departInfo) {
		ResultDto<DepartmentInfoEntity> resultDto = new ResultDto<DepartmentInfoEntity>();
		try {
			// 获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			//设置登录用户Id
			departInfo.setCreator(user.getUserId());
			//新增部门
			DepartmentInfoEntity result = departManageSrv.departAdd(departInfo);
			resultDto.setData(result);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 修改部门画面显示
	@RequestMapping(value = "/departDetail/{departId}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<DepartManageInfoDto> departDetail(@PathVariable("departId") String departId) {
		ResultDto<DepartManageInfoDto> resultDto = new ResultDto<DepartManageInfoDto>();
		try {
			//根据部门Id获取部门信息
			DepartManageInfoDto departInfo = departManageSrv.departDetail(departId);
			departInfo.setDepartManageFlag("upd");
			resultDto.setData(departInfo);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 修改部门
	@RequestMapping(value = "/departUpd", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<DepartManageInfoDto> departUpd(@RequestBody DepartManageInfoDto departInfo) {
		ResultDto<DepartManageInfoDto> resultDto = new ResultDto<DepartManageInfoDto>();
		try {
			//获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			//设置登录用户Id
			departInfo.setUpdater(user.getUserId());
			//修改部门信息
			DepartManageInfoDto result = departManageSrv.departUpd(departInfo);
			resultDto.setData(result);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 删除部门
	@RequestMapping(value = "/departDelete/{departId}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<DepartManageInfoDto> departDelete(@PathVariable("departId") String departId) {
		ResultDto<DepartManageInfoDto> resultDto = new ResultDto<DepartManageInfoDto>();
		try {
			//删除部门
			DepartManageInfoDto result = departManageSrv.departDelete(departId);
			resultDto.setData(result);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
}
