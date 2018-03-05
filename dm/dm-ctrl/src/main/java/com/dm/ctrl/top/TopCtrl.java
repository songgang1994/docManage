package com.dm.ctrl.top;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DocWillHandleDto;
import com.dm.dto.LoginDto;
import com.dm.dto.MonthHourListDto;
import com.dm.srv.top.TopSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 * 首页
 */
@Controller
@RequestMapping("/top")
public class TopCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(TopCtrl.class);
	// 首页用Service
	@Autowired
	private TopSrv topSrv;

	// 首页初始画面
	@RequestMapping(value = "/init")
	public ModelAndView init() {
		ModelAndView mv = new ModelAndView();
		try {
			// 获取登录用户信息
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			// 获取当月工时
			MonthHourListDto monthHourList = topSrv.getMonthHourList(user.getUserId());
			// 获取我的待处理文档
			List<DocWillHandleDto> willHandleDocList = topSrv.getWillHandleDocList(user.getUserId());
			// 获取待审核文件
			List<DocWillHandleDto> willApproveDocList = topSrv.getWillApproveDocList(user.getUserDept());
			// 获取已预约设备信息
			List<DeviceReserveInfoDto> deviceReserveInfoList = topSrv.getDeviceReserveList(user.getUserId());
			String leaderFlag = "0";
			for (int i = 0; i < user.getUserDept().size(); i++) {
				// 当登录用户为部门领导的场合
				if (user.getUserDept().get(i).getLeaderFlg().trim().equals("1")) {
					leaderFlag = "1";
					break;
				}
			}
			// 获取登录用户Id
			String userId = user.getUserId();
			// 获取用户所属部门
			List<String> deptIds = new ArrayList<String>();
			if (user.getUserDept() != null && user.getUserDept().size() != 0) {
				for (int i = 0; i < user.getUserDept().size(); i++) {
					deptIds.add(user.getUserDept().get(i).getDeptId());
				}
			}
			mv.addObject("topUserId", userId);
			mv.addObject("topdeptIds", deptIds);
			mv.addObject("monthHourList", monthHourList);
			mv.addObject("leaderFlg", leaderFlag);
			mv.addObject("willHandleDocList", willHandleDocList);
			mv.addObject("willApproveDocList", willApproveDocList);
			mv.addObject("deviceReserveInfoList", deviceReserveInfoList);
			mv.setViewName("top/Top");
			return mv;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}
}
