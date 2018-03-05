package com.dm.ctrl.system;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.dto.RoleDto;
import com.dm.dto.SysLogDto;
import com.dm.dto.UserDeptRoleDto;
import com.dm.dto.UserRoleDto;
import com.dm.entity.UserEntity;
import com.dm.srv.system.StaffManageSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：曾雷
 *	人员维护
 */
@Controller
@RequestMapping("/system")
public class StaffCtrl extends BaseCtrl{
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(StaffCtrl.class);

	@Autowired
	private StaffManageSrv sMSrv;


	@RequestMapping(value = "/staffmanage")
	public ModelAndView init(@RequestParam(value = "userId", defaultValue = "") String userId,
											@RequestParam(value = "userName", defaultValue = "") String userName,
											@RequestParam(value = "email", defaultValue = "") String email,
											@RequestParam(value = "roleId", defaultValue = "") String roleId,
											@RequestParam(value = "roleName", defaultValue = "") String roleName,
											@RequestParam(value = "flug", defaultValue = "") String flug
											) {
		ModelAndView mv = new ModelAndView();
		try {
			userName = java.net.URLDecoder.decode(userName, "UTF-8");
			roleName = java.net.URLDecoder.decode(roleName, "UTF-8");
			List<RoleDto> roles = sMSrv.getRoles();
			mv.addObject("roles", roles);
			//设置隐藏参数
			mv.addObject("userId",userId);
			mv.addObject("userName",userName);
			mv.addObject("email",email);
			mv.addObject("roleId",roleId);
			mv.addObject("roleName",roleName);
			mv.addObject("flug",flug);
			//设置跳转地址
			mv.setViewName("system/StaffList");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	//系统日志
	@RequestMapping(value = "/systemlog")
	public ModelAndView SystemLogHandler() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/SystemLog");
		return mv;
	}

	//用户新增模式
	@RequestMapping(value="/staffadd")
	public ModelAndView staffadd(
													@RequestParam(value = "userId1", defaultValue = "") String userId1,
													@RequestParam(value = "userName1", defaultValue = "") String userName1,
													@RequestParam(value = "email1", defaultValue = "") String email1,
													@RequestParam(value = "roleId1", defaultValue = "") String roleId1,
													@RequestParam(value = "roleName1", defaultValue = "") String roleName1
													) {
		ModelAndView mv = new ModelAndView();
		try {
			userName1 = java.net.URLDecoder.decode(userName1, "UTF-8");
			roleName1 = java.net.URLDecoder.decode(roleName1, "UTF-8");
			//设置模式
			request.setAttribute("flag", "2");
			//获取角色下拉框内容
			List<RoleDto> roles = sMSrv.getRoles();
			request.setAttribute("role", roles);
			//参数隐藏传值设置
			mv.addObject("userId1", userId1);
			mv.addObject("userName1", userName1);
			mv.addObject("email1",email1);
			mv.addObject("roleId1", roleId1);
			mv.addObject("roleName1",roleName1);
			mv.setViewName("system/StaffEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

    //用户修改模式
	@RequestMapping(value = "/staffedit")
	public ModelAndView initstaff(String userId,
													@RequestParam(value = "userId1", defaultValue = "") String userId1,
													@RequestParam(value = "userName1", defaultValue = "") String userName1,
													@RequestParam(value = "email1", defaultValue = "") String email1,
													@RequestParam(value = "roleId1", defaultValue = "") String roleId1,
													@RequestParam(value = "roleName1", defaultValue = "") String roleName1
													) {
		ModelAndView mv = new ModelAndView();
		try {
			userName1 = java.net.URLDecoder.decode(userName1, "UTF-8");
			roleName1 = java.net.URLDecoder.decode(roleName1, "UTF-8");
			//设置模式
			request.setAttribute("flag", "1");
			//获取角色下拉框内容
			List<RoleDto> roles = sMSrv.getRoles();
			request.setAttribute("role", roles);
			//获取指定角色信息
			UserDeptRoleDto dto = sMSrv.getStaffInfo(userId);
			mv.addObject("userdto", dto);
			request.setAttribute("user", dto);
			//获取role信息
			List<UserDeptRoleDto> listRole = sMSrv.getRoleInfo(userId);
			request.setAttribute("listRole", listRole);
			//获取角色部门信息
			List<UserDeptRoleDto> deptList = sMSrv.getDeptInfo(userId);
			request.setAttribute("list", deptList);
			mv.addObject("dept", deptList);
			//参数隐藏传值设置
			mv.addObject("userId1", userId1);
			mv.addObject("userName1", userName1);
			mv.addObject("email1",email1);
			mv.addObject("roleId1", roleId1);
			mv.addObject("roleName1",roleName1);
			//设置跳转地址
			mv.setViewName("system/StaffEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	@RequestMapping(value = "/getusers")
	@ResponseBody
	public ResultDto<UserRoleDto> getUserList(String userName,String userId,String emailAddress,String roleId){
		ResultDto<UserRoleDto> resultDto = new ResultDto<>();
		try {
			//开始的数据行数
			String start = request.getParameter("start");
			//每页的数据数
			String length = request.getParameter("length");
			int len=Integer.parseInt(length);
			if(len==-1) {
				len = Integer.MAX_VALUE;
			}
			//DT传递的draw:
			Integer draw = Integer.parseInt(request.getParameter("draw"));

			List<UserRoleDto> users = sMSrv.getUserList(userName, userId, emailAddress, roleId,Integer.parseInt(start),len);
			//分页总数据
			int count = sMSrv.getStaffCount(userName, userId, emailAddress, roleId);
			//新建cookie
			Cookie cookie = new Cookie(Constant.STAFF_PAGE_LENGTH,length);
			//设置cookie存储路径
			cookie.setPath("/");
			//将cookie传入页面
			response.addCookie(cookie);
			//设置每页数据
			resultDto.setListData(users);
			//将draw传入页面
			resultDto.setDraw(draw);
			//设置原始数据总数
			resultDto.setRecordsTotal(count);
			//设置过滤数据总数
			resultDto.setRecordsFiltered(count);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//用户新增
	@RequestMapping(value = "/addUser")
	@ResponseBody
	public ResultDto<BaseDto> addUser(@RequestBody UserDeptRoleDto userDeptRoleDto){
		ResultDto<BaseDto>  resultDto = new ResultDto();
		reserveaddUser(userDeptRoleDto);
		// 合法性check结束
		try {
			//获取登录人Id
			UserEntity user = (UserEntity)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String loginId = user.getUserId();
			//获取部门Id拼接String
			String dept = userDeptRoleDto.getDept();
			//获取是否负责人拼接String
			String Flg = userDeptRoleDto.getFlg();
			//新增用户信息
			BaseDto use= sMSrv.addUser(userDeptRoleDto, loginId,dept,Flg);
			resultDto.setData(use);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//用户修改
	@RequestMapping(value = "/updateUser")
	@ResponseBody
	public ResultDto<BaseDto> updateUser(@RequestBody UserDeptRoleDto userDeptRoleDto){
		ResultDto<BaseDto>  resultDto = new ResultDto();
		reserveaddUser(userDeptRoleDto);
		// 合法性check结束
		try {
			//获取登录人Id
			UserEntity user = (UserEntity)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String loginId = user.getUserId();
			//获取部门Id拼接String
			String dept = userDeptRoleDto.getDept();
			//获取是否负责人拼接String
			String Flg = userDeptRoleDto.getFlg();
			//修改用户信息
			BaseDto update = sMSrv.updateUser(userDeptRoleDto, loginId, dept, Flg);
			resultDto.setData(update);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//删除部门所属id
	@RequestMapping(value = "/deleteDept")
	@ResponseBody
	public ResultDto<BaseDto> deleteDept(String userId,String deptId){
		ResultDto<BaseDto>  resultDto = new ResultDto();
		try {
			//删除所属部门
			BaseDto dept = sMSrv.deleteDept(userId, deptId);
			resultDto.setData(dept);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//删除所在行的用户信息
	@RequestMapping(value = "/deleteFlg")
	@ResponseBody
	public ResultDto<BaseDto> deleteFlg(String userId){
		ResultDto<BaseDto> resultDto = new ResultDto();
		try {
			//获取登录人Id
			UserEntity user = (UserEntity)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String loginId = user.getUserId();
			//删除所在行的用户信息
			BaseDto flg = sMSrv.deleteFlg(userId, loginId);
			resultDto.setData(flg);
			return resultDto;
		}catch(Exception e){
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//统一删除选中行的信息
	@RequestMapping(value = "/deleteAllFlg")
	@ResponseBody
	public ResultDto<BaseDto> deleteAllFlg(String userId){
		ResultDto<BaseDto> resultDto = new ResultDto();
		try {
			//获取登录人Id
			UserEntity user = (UserEntity)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String loginId = user.getUserId();
			//删除选中行信息
			BaseDto use = sMSrv.deleteAllFlg(userId, loginId);
			resultDto.setData(use);
			return resultDto;
		}catch(Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//获取系统日志列表
	@RequestMapping(value = "/getLog")
	@ResponseBody
	 public ResultDto<SysLogDto> getLog(SysLogDto sysLogDto){
		ResultDto<SysLogDto> resultDto = new ResultDto<SysLogDto>();
		try {
			//开始的数据行数
			String start = request.getParameter("start");
			//每页的数据数
			String length = request.getParameter("length");
			int len=Integer.parseInt(length);
			if(len==-1) {
				len = Integer.MAX_VALUE;
			}
			//DT传递的draw:
			Integer draw = Integer.parseInt(request.getParameter("draw"));
			//查询每页数据
			List<SysLogDto> list = sMSrv.getLog(sysLogDto,Integer.parseInt(start),len);
			//分页总数据
			int count = sMSrv.getTatal(sysLogDto);
			//新建cookie
			Cookie cookie = new Cookie(Constant.LOG_PAGE_LENGTH,length);
			//设置cookie存储路径
			cookie.setPath("/");
			//将cookie传入画面
			response.addCookie(cookie);
			resultDto.setListData(list);
			//将draw传入画面
			resultDto.setDraw(draw);
			//设置原始数据总数
			resultDto.setRecordsTotal(count);
			//设置过滤数据总数
			resultDto.setRecordsFiltered(count);
			return resultDto;
		}catch(Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}


//===================================== 数据合法性check区域 ===============================
	//用户信息新增合法信心chech
	public boolean reserveaddUser(UserDeptRoleDto userDeptRoleDto) {
		boolean ok = true;
		//判断用户名是否为空
		if(userDeptRoleDto.getUserCode() == null) {
			ok = false;
		}else {
			//判断用户名长度是否超过数据库限制
			if(userDeptRoleDto.getUserCode().length() > Constant.RESERVE_USERCODE_MAX_LENGTH) {
				ok = false;
			}
		}

		//判断姓名是否为空
		if(userDeptRoleDto.getUserName() == null) {
			ok = false;
		}else {
			//判断姓名长度是否超过数据库限制
			if(userDeptRoleDto.getUserName().length() > Constant.RESERVE_USERNAME_MAX_LENGTH) {
				ok = false;
			}
		}

		//判断邮箱地址是否为空
		if(userDeptRoleDto.getEmail() == null) {
			ok = false;
		}else {
			//判断邮箱长度是否超过数据库限制
			if(userDeptRoleDto.getEmail().length() > Constant.RESERVE_EMAIL_MAX_LENGTH) {
				ok = false;
			}
		}

		//判断角色是否为空
		if(userDeptRoleDto.getRoleId() == Constant.RESERVE_LENGTH_NUM_ZERO) {
			ok = false;
		}

		//判断部门是否为空
		if(userDeptRoleDto.getDeptId() == null) {
			ok = false;
		}

		return ok;
	}



//===================================== 数据合法性check区域结束 ===============================


}
