package com.dm.ctrl.system;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.AuthoritymanageTreeDto;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.entity.SysRoleEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.system.AuthorityManageSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	权限维护
 */
@Controller
@RequestMapping("/system")
public class AuthoritymanageCtrl extends BaseCtrl{
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(AuthoritymanageCtrl.class);
	@Autowired
	private AuthorityManageSrv authorityManageSrv;
	//角色管理一览初始画面显示
		@RequestMapping(value = "/authorityList", method = RequestMethod.GET)
		public ModelAndView roleInit(@RequestParam(value = "roleNameSearch", defaultValue = "") String roleNameSearch,
				@RequestParam(value = "descriptionSearch", defaultValue = "") String descriptionSearch,
				@RequestParam(value = "authorityFlag", defaultValue = "") String authorityFlag) {
			ModelAndView mv = new ModelAndView();
			try {
				roleNameSearch = java.net.URLDecoder.decode(roleNameSearch, "UTF-8");
				descriptionSearch = java.net.URLDecoder.decode(descriptionSearch, "UTF-8");
				mv.addObject("roleNameSearch", roleNameSearch);
				mv.addObject("descriptionSearch", descriptionSearch);
				mv.addObject("authorityFlag", authorityFlag);
				mv.setViewName("system/AuthorityList");
				return mv;
			} catch (UnsupportedEncodingException e) {
				// 出力异常信息到LOG中
				log.error(e.getMessage());
				mv.addObject("exMsg", Constant.MS_ERROR);
				mv.setViewName("util/error");
				return mv;
			}
		}

		//角色管理一览查询
		@RequestMapping(value = "/roleList", method = RequestMethod.POST)
		@ResponseBody
		public ResultDto<SysRoleEntity> roleList(@ModelAttribute("roleInfo") SysRoleEntity roleInfo){
			ResultDto<SysRoleEntity> resultDto = new ResultDto<SysRoleEntity>();
			try {
				List<SysRoleEntity> roleList = authorityManageSrv.getRoleList(roleInfo);
				resultDto.setListData(roleList);
				resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
				return resultDto;
			} catch (Exception e) {
				// 出力异常信息到LOG中
				log.error(e.getMessage());
				resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
				return resultDto;
			}
		}

	//角色新增
	@RequestMapping(value ="/roleAdd",method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> roleAdd(@RequestBody SysRoleEntity roleInfo){
		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		try {
			// 获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			roleInfo.setCreator(user.getUserId());
			BaseDto result = authorityManageSrv.roleAdd(roleInfo);
			resultDto.setData(result);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
	//角色编辑
	@RequestMapping(value ="/roleUpd",method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> roleUpd(@RequestBody SysRoleEntity roleInfo){
		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		try {
			// 获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			roleInfo.setUpdater(user.getUserId());
			BaseDto result = authorityManageSrv.roleUpd(roleInfo);
			resultDto.setData(result);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
	//角色删除
	@RequestMapping(value ="/roleDelete",method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> roleUpd(@RequestParam("roleIds") String[] roleIds){
		ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
		try {
			// 获取登录用户信息
			BaseDto result = authorityManageSrv.roleDelete(roleIds);
			resultDto.setData(result);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
	//权限维护初始画面显示
	@RequestMapping(value = "/authoritymanage", method = RequestMethod.GET)
	public ModelAndView initRole(@RequestParam(value = "roleId", defaultValue = "") String roleId,
			@RequestParam(value = "roleName", defaultValue = "") String roleName,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "roleNameSearch", defaultValue = "") String roleNameSearch,
			@RequestParam(value = "descriptionSearch", defaultValue = "") String descriptionSearch) {
		ModelAndView mv = new ModelAndView();
		try {
			roleName = java.net.URLDecoder.decode(roleName, "UTF-8");
			description = java.net.URLDecoder.decode(description, "UTF-8");
			roleNameSearch = java.net.URLDecoder.decode(roleNameSearch, "UTF-8");
			descriptionSearch = java.net.URLDecoder.decode(descriptionSearch, "UTF-8");
			mv.addObject("roleId", roleId);
			mv.addObject("roleName", roleName);
			mv.addObject("description", description);
			mv.addObject("roleNameSearch", roleNameSearch);
			mv.addObject("descriptionSearch", descriptionSearch);
		mv.setViewName("system/AuthorityManage");
		return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	//权限维护树形目录
	@RequestMapping(value = "/authoritymanageTree/{roleId}",method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AuthoritymanageTreeDto> initTree(@PathVariable("roleId")int roleId){
		ResultDto<AuthoritymanageTreeDto> resultDto = new ResultDto<AuthoritymanageTreeDto>();
		try {
			AuthoritymanageTreeDto  authoritymanageTree = authorityManageSrv.getTree(roleId);
			resultDto.setData(authoritymanageTree);
			return resultDto;
		}catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//权限项保存
	@RequestMapping(value = "/authoritymanageAdd",method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> authorityUpd(@RequestParam("actionSelected")String[] actionSelected,
			@RequestParam("roleId")int roleId){
		ResultDto<BaseDto> baseDto =new ResultDto<BaseDto>();
		try {
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			BaseDto result = authorityManageSrv.authorityUpd(actionSelected, roleId,user.getUserId());
			baseDto.setData(result);
			return baseDto;
		}catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			baseDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return baseDto;
		}
	}
}
