package com.dm.ctrl.outlay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ResultDto;
import com.dm.dto.WorkingTimesDto;
import com.dm.dto.MonthHourTableDto;
import com.dm.dto.PersonMonthHourInfo;
import com.dm.entity.UserEntity;
import com.dm.srv.outlay.WorkingTimesSrv;
import com.dm.tool.Constant;
import com.dm.tool.RWExcelUtil;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
@Controller
@RequestMapping("/outlay")
public class WorkingTimesCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(WorkingTimesCtrl.class);
	@Autowired
	private WorkingTimesSrv workingTimesSrv;

	/**
	 * 工时填写画面初始化
	 *
	 * @return
	 */
	@RequestMapping("/hourenter")
	private ModelAndView TaskTimeInit(String userId,String[] deptId) {
		ModelAndView mv = new ModelAndView();
		//获取登录人信息
		UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
		userId = user.getUserId();
		String userName = user.getUserName();
		//根据人员id获取人员部门
		String dept = workingTimesSrv.getDepartByUserId(userId);
		//通过id和时间查询工时信息
		List<WorkingTimesDto> list = workingTimesSrv.getWork(userId);
		int size = list.size();
		mv.addObject("size", size);
		mv.addObject("list", list);
		mv.addObject("deptName", dept);
		mv.addObject("userName", userName);
		mv.addObject("userId", userId);
		mv.setViewName("outlay/HourEnter");
		return mv;
	}

	/**
	 * 工时填写画面编辑
	 * @return
	 */
	/*@RequestMapping("/hourenterEdit")
	private ModelAndView hourenterEdit(String projectNo,String userId,String userName) {
		ModelAndView mv = new ModelAndView();
		if (userId == null || userId == "") {
			// 获取系统登录人信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			userId = user.getUserId();
			userName=user.getUserName();
		}
		//根据人员id获取人员部门
		String dept = workingTimesSrv.getDepartByUserId(userId);
		//根据课题编号获取工时信息
		List<WorkingTimesDto> list = workingTimesSrv.getProjectNoInfo(projectNo);
		mv.addObject("list", list);
		mv.addObject("deptName", dept);
		mv.addObject("userName", userName);
		mv.setViewName("outlay/HourEnter");
		return mv;
	}*/

	/**
	 * 工时填写画面编辑
	 * @return
	 */
	@RequestMapping("/hourenterEdit")
	private ModelAndView hourenterEdit(String dateTm,String userId) {
		ModelAndView mv = new ModelAndView();
		//根据人员id获取人员姓名
		String userName = workingTimesSrv.getUserName(userId);
		//根据人员id获取人员部门
		String dept = workingTimesSrv.getDepartByUserId(userId);
		//根据课人员ID和日期获取工时信息
		List<WorkingTimesDto> list = workingTimesSrv.getDetail(userId,dateTm);
		int flug = 2;
		int size = list.size();
		mv.addObject("list", list);
		mv.addObject("list1", list);
		mv.addObject("size", size);
		mv.addObject("time",dateTm);
		mv.addObject("deptName", dept);
		mv.addObject("userId",userId);
		mv.addObject("userName", userName);
		mv.setViewName("outlay/HourEnter");
		mv.addObject("toflug",flug);
		return mv;
	}

	/**
	 * 课题一览跳转工时填写
	 * @return
	 */
	@RequestMapping("/projectHourEdit")
	private ModelAndView projectHourEdit(@RequestParam(value = "projectNo", defaultValue = "")String projectNo,
			@RequestParam(value = "userId", defaultValue = "")String userId,
			@RequestParam(value = "fyYear", defaultValue = "")String fyYear,
			@RequestParam(value = "flug", defaultValue = "")String flug,
			@RequestParam(value = "deptNames", defaultValue = "")String deptNames,
			@RequestParam(value = "deptId", defaultValue = "")String deptId,
			@RequestParam(value = "projectName", defaultValue = "")String projectName,
			@RequestParam(value = "projectType", defaultValue = "")String projectType) {
		ModelAndView mv = new ModelAndView();
		try {
			deptNames = java.net.URLDecoder.decode(deptNames, "UTF-8");
			projectName = java.net.URLDecoder.decode(projectName, "UTF-8");
			//根据人员id获取人员部门
			String dept = workingTimesSrv.getDepartByUserId(userId);
			//根据课题编号获取工时信息
			//List<WorkingTimesDto> list = workingTimesSrv.getProjectNoInfo(projectNo);
			//通过人员id、fy年度、当前时间获取工时信息
			List<WorkingTimesDto> list = workingTimesSrv.getProjectNoInfoa(projectNo, userId, fyYear);
			//通过id和时间查询工时信息
			List<WorkingTimesDto> list1 = workingTimesSrv.getWork(userId);
			String username = workingTimesSrv.getUserName(userId);
			int size = list.size();
			mv.addObject("list", list);
			mv.addObject("list1", list1);
			mv.addObject("size", size);
			mv.addObject("deptName", dept);
			mv.addObject("userName", username);
			mv.addObject("userId",userId);
			mv.addObject("fyYear",fyYear);
			mv.addObject("deptNames",deptNames);
			mv.addObject("deptId",deptId);
			mv.addObject("projectType",projectType);
			mv.addObject("projectName",projectName);
			mv.addObject("flug",flug);
			mv.setViewName("outlay/HourEnter");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	/**
	 * 通过用户id 时间查询工时信息
	 * @return
	 */
	@RequestMapping("/hourSearch")
	@ResponseBody
	private ResultDto<WorkingTimesDto> getDetail(String userId,String time){
		ResultDto<WorkingTimesDto> resultDto = new ResultDto<>();
		WorkingTimesDto baseDto = new WorkingTimesDto();
		try {
			List<WorkingTimesDto> list = workingTimesSrv.getDetail(userId, time);
			resultDto.setListData(list);
			return resultDto;
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			resultDto.setData(baseDto);
			return resultDto;
		}
	}

	/**
	 * 人工工时填写
	 *
	 * @param workingtimes
	 *            工时
	 * @param projectNo
	 *            课题编号
	 * @param fyYear
	 *            Fy年度
	 * @param dateTm
	 *            日期
	 * @return ResultDto
	 */
	@RequestMapping("/hourentersub")
	@ResponseBody
	private ResultDto<BaseDto> fillInTaskTime(BigDecimal[] workingtimes, String[] projectNo, String[] fyYear,
			String dateTm,int flug,String userId) {

		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

		try {
			// 获取系统登录人信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			baseDto = workingTimesSrv.hourEnter(workingtimes, projectNo, fyYear, dateTm, user.getUserId(),flug,userId);
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			resultDto.setData(baseDto);
			return resultDto;
		}

	}

	/**
	 * 月度工时画面初始化
	 *
	 * @return ModelAndView
	 */
	@RequestMapping("/monthlyhour")
	private ModelAndView monthlyHourInit(
			@RequestParam(value = "departId", defaultValue = "")String departId,
			@RequestParam(value = "fyYear", defaultValue = "")String fyYear,
			@RequestParam(value = "deptName", defaultValue = "")String deptName) {
		ModelAndView mv = new ModelAndView();
		try {
			if(deptName != null) {
			deptName = java.net.URLDecoder.decode(deptName, "UTF-8");
			}
			mv.addObject("fyYear", fyYear);
			mv.addObject("departId", departId);
			mv.addObject("deptName", deptName);

			mv.setViewName("outlay/MonthlyHour");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	/**
	 * 查询月度工时
	 *
	 * @param departId
	 *            部门id
	 * @param fyYear
	 *            Fy年度
	 * @return ResultDto
	 */
	@RequestMapping("/querymonthlyhour")
	@ResponseBody
	private ResultDto<MonthHourTableDto> queryMonthlyHour(String departId, String fyYear,String deptId) {
		ResultDto<MonthHourTableDto> resultDto = new ResultDto<MonthHourTableDto>();
		MonthHourTableDto baseDto = new MonthHourTableDto();
		List<MonthHourTableDto> list = new ArrayList<>();


			list = workingTimesSrv.queryMonthlyHour(departId, fyYear);
			//保存月度工时表单数据，以便导出
			session.setAttribute(Constant.SAVE_MONTHLY_HOUR_KEY, list);
			try {resultDto.setListData(list);
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			resultDto.setData(baseDto);
			return resultDto;
		}

	}

	/**
	 * 人员工时画面初始化
	 *
	 * @return
	 */
	@RequestMapping("/personnelhour")
	private ModelAndView personnelHour(String fyYear,String userId,String deptId,String userName,String deptName) {
		ModelAndView mv = new ModelAndView();

		// TODO：参数为空,显示当前登录人信息 从session获取参数
		mv.addObject("fyYear", fyYear);
		mv.addObject("userId", userId);
		mv.addObject("deptId", deptId);
		mv.addObject("userName", userName);
		mv.addObject("deptName", deptName);

		mv.setViewName("outlay/PersonnelHour");

		return mv;
	}

	/**
	 * 个人月度工时查询
	 *
	 * @param FyYear
	 * @return
	 */
	@RequestMapping("/querypersonnelhour")
	@ResponseBody
	private ResultDto<PersonMonthHourInfo> queryMonthHourByUserIdAndFyYear(String userId, String fyYear) {
		ResultDto<PersonMonthHourInfo> resultDto = new ResultDto<PersonMonthHourInfo>();
		PersonMonthHourInfo baseDto = new PersonMonthHourInfo();
		List<PersonMonthHourInfo> list = new ArrayList<>();

		try {
			// 获取系统登录人信息
			list = workingTimesSrv.getPersonMonthHour(userId, fyYear);
			resultDto.setListData(list);
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			resultDto.setData(baseDto);
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			resultDto.setData(baseDto);
			return resultDto;
		}

		return resultDto;

	}


	@RequestMapping("/exportmonthlyhour")
	@ResponseBody
	private ResultDto<BaseDto> exportMonthlyHour(String deptName,String fyYear) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

		try {
			//保存月度工时表单数据
			List<MonthHourTableDto> list =(List<MonthHourTableDto>)session.getAttribute(Constant.SAVE_MONTHLY_HOUR_KEY);
			// 文档导出
			List<Row> rows = workingTimesSrv.exportMonthlyHour(list,deptName,fyYear);
			RWExcelUtil u = new RWExcelUtil();
			u.writeExcel(rows);
			baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
			resultDto.setData(baseDto);
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			resultDto.setData(baseDto);
			return resultDto;
		}

		return resultDto;

	}


}
