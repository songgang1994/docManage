package com.dm.ctrl.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.ctrl.util.LoginCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.ErrCode;
import com.dm.dto.ParmMstDto;
import com.dm.dto.ResultDto;
import com.dm.entity.UserEntity;
import com.dm.srv.main.SourceMainSrv;
import com.dm.tool.Constant;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：字典数据维护
 */
@Controller
@RequestMapping("/main")
public class DataSourceMainCtrl extends BaseCtrl {
// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(LoginCtrl.class);
	@Autowired
	private SourceMainSrv sourceMainSrv;

//*******************************************************字典数据维护进入画面***********************************************************//
	//进入画面地址
	@RequestMapping("/datasourcemaintain")
	public ModelAndView datasourcemaintain() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/DataSourceMaintain");
		return mv;
	}

	//查询类型
	@RequestMapping("/getType")
	@ResponseBody
	public ResultDto<BaseDto> getType(String type1Name){
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		//检查参数是否为空
		if(type1Name == null) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_INVALID_PARAMS);
			return resultDto;
		}
		try {
			//查询
			BaseDto type = sourceMainSrv.getType(type1Name);
			resultDto.setData(type);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	//索引，查询类型名
	@RequestMapping("/getDisName")
	@ResponseBody
	public List<ParmMstDto> getDisName(String type1,HttpServletRequest request){
		List<ParmMstDto> list = sourceMainSrv.getDisName(type1);
		request.setAttribute("list", list);
		return list;
	}

	//插入与更新类型数据
	@RequestMapping("/changeSource")
	@ResponseBody
	public ResultDto<BaseDto> changeSource(String type1,String addType,String nowNum,String type){
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		reservechangeSource(addType);
		// 合法性check结束
		BaseDto baseDto = new BaseDto();
		try {
			//获取登录人Id
			UserEntity user = (UserEntity)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			//调用service层方法
			BaseDto base = sourceMainSrv.addNew(type1, nowNum, addType, userId,type);
			resultDto.setData(base);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
//*******************************************************字典数据维护进入画面结束***********************************************************//

//===================================== 数据合法性check区域 ===============================
	// 字典数据维护合法性check
	public boolean reservechangeSource(String addType) {
		boolean ok = true;
		//判断新增内容是否为空
		if(addType == null) {
			ok = false;
		}
		return ok;
	}


//===================================== 数据合法性check区域 ===============================


}
