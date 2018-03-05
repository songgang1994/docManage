package com.dm.ctrl.device;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.ctrl.BaseCtrl;
import com.dm.ctrl.util.LoginCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DeviceInfoDto;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DeviceUseInfoDto;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.entity.DeviceInfoEntity;
import com.dm.entity.DeviceUseInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.device.DeviceSrv;
import com.dm.tool.Constant;
import com.dm.tool.StringUtil;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：实验设备
 */
@Controller
@RequestMapping("/device")
public class DeviceCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(LoginCtrl.class);

	@Autowired
	private DeviceSrv deviceSrv;
	private final int PER_PAGE = Constant.RESERVE_DOCUMENTITEMNAME_MAX_LENGTH;

	//===================================== 数据交互区域 ===============================
	@RequestMapping(value = "/devicelist")
	@ResponseBody
	public ResultDto<DeviceInfoDto> list(DeviceInfoEntity deviceInfo) {
		ResultDto<DeviceInfoDto> resultDto = new ResultDto<>();
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

			//判断参数是否为null，如果是null则赋空值
			String deviceNo = deviceInfo.getDeviceNo();
			String deviceName = deviceInfo.getDeviceName();
			String deviceLocation = deviceInfo.getLocationId();
			if (deviceLocation != null) {
				deviceLocation = deviceLocation.trim();
			} else {
				deviceLocation = "";
			}
			//模糊查询，获取数据
			List<DeviceInfoDto> list = deviceSrv.listByPage(deviceNo, deviceName, deviceLocation,Integer.parseInt(start),len);
			int count = deviceSrv.getlistCount(deviceNo, deviceName, deviceLocation);
			//新建cookie
			Cookie cookie = new Cookie(Constant.DEVICE_PAGE_LENGTH,length);
			//设置cookie存储路径
			cookie.setPath("/");
			//将cookie传入画面
			response.addCookie(cookie);
			//设置每页数据
			resultDto.setListData(list);
			//将draw传入画面
			resultDto.setDraw(draw);
			//设置原始数据总数
			resultDto.setRecordsTotal(count);
			//设置过滤数据总数
			resultDto.setRecordsFiltered(count);
			resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//跳转到设备使用一览页面
	public ModelAndView index(@RequestParam(value = "deviceNo", defaultValue = "") String deviceNo,
												@RequestParam(value = "deviceName", defaultValue = "") String deviceName,
												@RequestParam(value = "locationId", defaultValue = "") String locationId,
												@RequestParam(value = "location", defaultValue = "") String location,
												@RequestParam(value = "flug", defaultValue = "") String flug
												) {
		ModelAndView mv = new ModelAndView();
		try {
			deviceName = java.net.URLDecoder.decode(deviceName, "UTF-8");
			location = java.net.URLDecoder.decode(location, "UTF-8");
			mv.addObject("deviceNo", deviceNo);
			mv.addObject("deviceName", deviceName);
			mv.addObject("locationId", locationId);
			mv.addObject("location", location);
			mv.addObject("flug",flug);
			mv.setViewName("device/DeviceList");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}
	//通过设备编号和用户编号删除一条查询到的数据
	@RequestMapping("delete/{deviceNo}")
	@ResponseBody
	public ResultDto<BaseDto> delete(@PathVariable("deviceNo") String deviceNo) {
		//获得用户ID
		UserEntity userEntity = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
		String userId = userEntity != null ? userEntity.getUserId() : "1";
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			BaseDto deviceInfo = deviceSrv.delete(deviceNo,userId);
			resultDto.setData(deviceInfo);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 设备预约状况 月度页面
	@RequestMapping(value = "/reservemonth")
	public ModelAndView DeviceReserveMonth() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("device/DeviceReserveMonth");
		return mv;
	}

	// 获取 指定期间，指定设备的预约状况
	@RequestMapping(value = "/deviceReserveInfo")
	@ResponseBody
	public ResultDto<DeviceReserveInfoDto> DeviceReserveInfo(@RequestParam("deviceNos") String[] deviceNos,
			@RequestParam("startDt") @JSONField(format = "yyyy/MM/dd HH:mm:ss") Date startDt,
			@RequestParam("endDt") @JSONField(format = "yyyy/MM/dd HH:mm:ss") Date endDt) {

		ResultDto<DeviceReserveInfoDto> resultDto = new ResultDto<>();

		try {
			// 业务处理
			List<DeviceReserveInfoDto> listData = deviceSrv.reserveInfo(deviceNos, startDt, endDt);
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

	// 设备预约状况 周页面
	@RequestMapping(value = "/reserveweek")
	public ModelAndView DeviceReserveWeek() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("device/DeviceReserveWeek");
		return mv;
	}

	// 获取单条设备预约记录
	@RequestMapping(value = "/getReserve")
	@ResponseBody
	public ResultDto<BaseDto> getReserve(@RequestParam("deviceNo") String deviceNo,
			@RequestParam("reserveNo") String reserveNo) {

		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

		try {
			// 业务处理
			baseDto = deviceSrv.getReserve(deviceNo, reserveNo);
			// 设置业务处理结果
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 新增、修改、删除 设备预约
	@RequestMapping(value = "/reserve")
	@ResponseBody
	public ResultDto<BaseDto> reserve(@RequestBody DeviceReserveInfoDto deviceReserveInfoDto) {

		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

		try {
			//获得登录的user
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			if(user==null || user.getUserId().equals("")) { //登录超时，请重新登录
				resultDto.setRtnCode(ErrCode.RTN_CODE_NOT_LOGIN);
				return resultDto;
			}else {
				//设置操作者
				deviceReserveInfoDto.setUserId(user.getUserId());
				deviceReserveInfoDto.setCreator(user.getUserId());
				deviceReserveInfoDto.setUpdater(user.getUserId());
			}

			// 合法性check 验证失败直接返回
			if (!reserveVolid(deviceReserveInfoDto)) {
				resultDto.setRtnCode(ErrCode.RTN_CODE_INVALID_PARAMS);
				return resultDto;
			}

			// 业务处理
			baseDto = deviceSrv.reserve(deviceReserveInfoDto);
			// 设置业务处理结果
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
//===============================设备使用一览=============================//
	//设备使用一览进入画面
	@RequestMapping(value = "/uselist")
	public ModelAndView useList(
			@RequestParam(value = "deviceNo", defaultValue = "") String deviceNo,
			@RequestParam(value = "deviceName", defaultValue = "") String deviceName,
			@RequestParam(value = "locationId", defaultValue = "") String locationId,
			@RequestParam(value = "location", defaultValue = "") String location,
			@RequestParam(value = "useDt", defaultValue = "") String useDt,
			@RequestParam(value = "flug", defaultValue = "") String flug) {
		ModelAndView mv = new ModelAndView();
		try {
			deviceName = java.net.URLDecoder.decode(deviceName, "UTF-8");
			location = java.net.URLDecoder.decode(location, "UTF-8");
			mv.addObject("deviceNo", deviceNo);
			mv.addObject("deviceName", deviceName);
			mv.addObject("locationId", locationId);
			mv.addObject("location", location);
			mv.addObject("useDt", useDt);
			mv.addObject("flug", flug);
			mv.setViewName("device/DeviceUseList");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	//设备使用一览查询数据
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ResultDto<DeviceInfoDto> getList(DeviceInfoDto deviceInfoDto){
		ResultDto<DeviceInfoDto> resultDto = new ResultDto<>();
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
			//查询设备使用数据
			List<DeviceInfoDto> list = deviceSrv.getUseList(deviceInfoDto, Integer.parseInt(start),len);
			//分页总数据
			int count = deviceSrv.getUseTatal(deviceInfoDto);
			//新建cookie
			Cookie cookie = new Cookie(Constant.USE_PAGE_LENGTH,length);
			//设置cookie存储路径
			cookie.setPath("/");
			//将cookie传入页面
			response.addCookie(cookie);
			//设置每页数据
			resultDto.setListData(list);
			//将draw传入页面
			resultDto.setDraw(draw);
			//设置原始数据总数
			resultDto.setRecordsTotal(count);
			//设置过滤数据总数
			resultDto.setRecordsFiltered(count);
			return resultDto;
		}catch(Exception e){
		// 出力异常信息到LOG中
		log.error(e.getMessage());
		resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
		return resultDto;
		}
	}

//===============================设备使用一览结束==========================//

//===============================设备新增修改=============================//
	// 设备新增修改（进入画面）
	@RequestMapping(value = "/edit")
	public ModelAndView manage(
													@RequestParam(value = "deviceNo1", defaultValue = "") String deviceNo1,
													@RequestParam(value = "deviceName", defaultValue = "") String deviceName,
													@RequestParam(value = "locationId", defaultValue = "") String locationId,
													@RequestParam(value = "location", defaultValue = "") String location
													) {
		ModelAndView mv  = new ModelAndView();
		try {
			deviceName = java.net.URLDecoder.decode(deviceName, "UTF-8");
			location = java.net.URLDecoder.decode(location, "UTF-8");
			mv.addObject("deviceNo1", deviceNo1);
			mv.addObject("deviceName", deviceName);
			mv.addObject("locationId", locationId);
			mv.addObject("location", location);
			mv.setViewName("device/DeviceEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	// 添加设备信息
	@RequestMapping(value = "/add")
	@ResponseBody
	public ResultDto<BaseDto> addDevice(@RequestBody DeviceInfoEntity deviceInfoEntity) {
		ResultDto<BaseDto> resultDto = new ResultDto();
		reserveDevice(deviceInfoEntity);
		// 合法性check结束
		try {
			//获取登录人id
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			//新增设备信息
			BaseDto deviceInfo = deviceSrv.addDevice(deviceInfoEntity, userId);
			//设置业务处理结果
			resultDto.setData(deviceInfo);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 查询设备编号是否存在
	@RequestMapping(value = "/search")
	@ResponseBody
	public boolean searchNumber(@RequestParam("deviceNo") String eqnumber) {
		boolean isOnlyOne = true;
		//通过设备编号查询数据库是否有数据
		DeviceInfoEntity number = deviceSrv.searchNumber(eqnumber);
		if (number != null) {
			//数据库存在该条数据
			isOnlyOne = false;
		}
		return isOnlyOne;
	}

	// 通过编号，查询设备信息，反应到画面
	@RequestMapping(value = "/editor" , method = RequestMethod.GET)
	public ModelAndView editorDevice(String deviceNo,
															@RequestParam(value = "deviceNo1", defaultValue = "") String deviceNo1,
															@RequestParam(value = "deviceName", defaultValue = "") String deviceName,
															@RequestParam(value = "locationId", defaultValue = "") String locationId,
															@RequestParam(value = "location", defaultValue = "") String location
															) {
		ModelAndView view = new ModelAndView();
		try {
			deviceName = java.net.URLDecoder.decode(deviceName, "UTF-8");
			location = java.net.URLDecoder.decode(location, "UTF-8");
			// 通过编号查询设备相关信息
			DeviceInfoEntity device = deviceSrv.editorDevice(deviceNo);
			request.setAttribute("deviceinfo", device);
			view.addObject("device", device);
			view.addObject("deviceNo", deviceNo);
			view.addObject("deviceNo1", deviceNo1);
			view.addObject("deviceName", deviceName);
			view.addObject("locationId", locationId);
			view.addObject("location", location);
			request.setAttribute("flag", true);
			request.setAttribute("deviceNo", deviceNo);
			view.setViewName("device/DeviceEdit");
			return view;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			view.addObject("exMsg", Constant.MS_ERROR);
			view.setViewName("util/error");
			return view;
		}
	}
	// 通过设备编号修改设备信息
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultDto<BaseDto> updateDevice(@RequestBody DeviceInfoEntity deviceInfoEntity) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		reserveDevice(deviceInfoEntity);
		// 合法性check结束
		try {
			//获取登陆人id
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			//修改设备信息
			BaseDto updatadev = deviceSrv.updateDevice(deviceInfoEntity, userId);
			// 设置业务处理结果
			resultDto.setData(updatadev);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}
//===============================设备新增修改结束===========================//

//================================使用工时录入=============================//
	// 实验设备使用工时录入(新增功能，进入画面)
	@RequestMapping(value = "/hourenter")
	public ModelAndView hourenter(String deviceNo, String deviceName) {
		ModelAndView mv = new ModelAndView();
		//查询所有设备
		List<DeviceInfoEntity> listDevice = deviceSrv.getDevice();
		// 用于页面判断状态
		request.setAttribute("flag", true);
		// 接收数据展现画面
		request.setAttribute("deviceNo", deviceNo);
		request.setAttribute("deviceName", deviceName);
		request.setAttribute("listdevice", listDevice);
		//设置跳转路径
		mv.setViewName( "device/DeviceUseHourEnter");
		return mv;
	}
	//工时录入（新增，设备信息渲染画面）
	@RequestMapping(value = "/hour")
	@ResponseBody
	public ResultDto<DeviceInfoEntity> getDevice(){
		ResultDto<DeviceInfoEntity> resultDto = new ResultDto();
		//查询所有设备
		List<DeviceInfoEntity> listDevice = deviceSrv.getDevice();
		resultDto.setListData(listDevice);
		return resultDto;
	}

	// 实验设备使用工时录入(修改功能，进入画面)
	@RequestMapping(value = "/hourupdate")
	@ResponseBody
	public DeviceUseInfoDto hourupdate(BigDecimal useNo) {
		//通过使用编号查询设备信息
		DeviceUseInfoDto useInfo = deviceSrv.getInfo(useNo);
		return useInfo;
	}

	// 使用设备工时录入
	@RequestMapping(value = "/hourEnter")
	@ResponseBody
	public ResultDto<BaseDto> addHour(@RequestBody DeviceUseInfoEntity deviceUserInfoEntity) {
		ResultDto<BaseDto> resultDto = new ResultDto();
		reserveHour(deviceUserInfoEntity);
		// 合法性check结束
		try {
			// 获取登录者id
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			// 工时录入
			BaseDto deviceHour = deviceSrv.addHour(deviceUserInfoEntity, userId);
			resultDto.setData(deviceHour);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 设备工时信息删除
	@RequestMapping(value = "/hourDelete")
	@ResponseBody
	public ResultDto<BaseDto> deleteHour(BigDecimal useNo) {
		ResultDto<BaseDto> resultDto = new ResultDto();
		try {
			// 删除信息
			BaseDto deleteHour = deviceSrv.deleteHour(useNo);
			resultDto.setData(deleteHour);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 核对设备使用期间是否有冲突
	@RequestMapping(value = "/hourGet")
	@ResponseBody
	public boolean gettime(@RequestBody DeviceUseInfoEntity deviceUserInfoEntity) {
		boolean isOnlyOne = true;
		DeviceUseInfoEntity number = deviceSrv.gettime(deviceUserInfoEntity);
		// 当查出数据是，表明设备使用期间有冲突，无法新增
		if (number != null) {
			isOnlyOne = false;
		}
		return isOnlyOne;
	}

	// 修改设备使用工时信息
	@RequestMapping(value = "/hourUpdate")
	@ResponseBody
	public ResultDto<BaseDto> updatetime(@RequestBody DeviceUseInfoEntity deviceUserInfoEntity) {
		ResultDto<BaseDto> resultDto = new ResultDto();
		reserveHour(deviceUserInfoEntity);
		// 合法性check结束
		try {
			// 获取登录者id
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			// 修改设备信息
			BaseDto update = deviceSrv.updateHour(deviceUserInfoEntity, userId);
			resultDto.setData(update);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
//=====================================使用工时录入结束===========================//

//=====================================  结束 ==================================


//===================================== 数据合法性check区域 ===============================
	// 设备预约信息合法性check
	public boolean reserveVolid(DeviceReserveInfoDto deviceReserveInfoDto) {
		boolean ok = true;
		// 验证操作模式
		if (deviceReserveInfoDto.getPattern() != Constant.RESERVE_PATTERN_ADD
				&& deviceReserveInfoDto.getPattern() != Constant.RESERVE_PATTERN_UPDATE
				&& deviceReserveInfoDto.getPattern() != Constant.RESERVE_PATTERN_DELETE) {
			// 操作模式异常
			ok = false;
		} else {
			if (deviceReserveInfoDto.getPattern() != Constant.RESERVE_PATTERN_ADD) {
				//在修改和删除模式下，验证ReserveNo的值,此
				if (deviceReserveInfoDto.getReserveNo().compareTo(Constant.RESERVE_NO_MIN) == -1  //no小于1
						|| deviceReserveInfoDto.getReserveNo().compareTo(Constant.RESERVE_NO_MAX) == 1) {//NO 大于9999999999
					// 修改/删除的预约No出现错误
					ok = false;
				}
				//删除模式只验证ReserveNo
				if(deviceReserveInfoDto.getPattern() == Constant.RESERVE_PATTERN_DELETE) {
					if(ok) {
						return true;
					}
				}
			}
		}

		// 验证设备号
		if (StringUtil.isEmpty(deviceReserveInfoDto.getDeviceNo())
				|| deviceReserveInfoDto.getDeviceNo().length() > Constant.DEVICE_NO_MAX_LENGTH) {
			// 设备号错误
			ok = false;
		}

		// 验证标题
		if (StringUtil.isEmpty(deviceReserveInfoDto.getTitle())) {
			// 请输入标题
			ok = false;
		} else {
			if(deviceReserveInfoDto.getTitle().length() > Constant.RESERVE_TITLE_MAX_LENGTH) {
				// 标题 最长输入200个字符
				ok = false;
			}
		}

		// 使用目的
		if (StringUtil.isEmpty(deviceReserveInfoDto.getUseGoal())) {
			// 请输入 使用目的
			ok = false;
		} else {
			if(deviceReserveInfoDto.getUseGoal().length() > Constant.RESERVE_USE_GOAL_MAX_LENGTH) {
				// 使用目的 最长输入500个字符
				ok = false;
			}
		}

		// 验证颜色
		if (deviceReserveInfoDto.getColor() != null) {
			Pattern pattern = Pattern.compile(Constant.RESERVE_COLOR);
			Matcher matcher = pattern.matcher(deviceReserveInfoDto.getColor());
			// 字符串是否与正则表达式相匹配
			boolean rs = matcher.matches();
			if (!rs) {
				// 请选择正确的颜色
				ok = false;
			}
		} else {
			// 请选择颜色
			ok = false;
		}

		//判断期间
		if(deviceReserveInfoDto.getStartDt().compareTo(deviceReserveInfoDto.getEndDt()) > Constant.RESERVE_LENGTH_NUM_ZERO
				|| deviceReserveInfoDto.getEndDt().compareTo(new Date()) < Constant.RESERVE_LENGTH_NUM_ZERO) {
			ok = false;
		}



		return ok;
	}




	//设备新增修改合法性check
	public boolean reserveDevice(DeviceInfoEntity deviceInfoEntity) {
		boolean ok = true;
		//判断设备编号是否为空
		if(deviceInfoEntity.getDeviceNo() == null) {
			ok = false;
		}else {
			//判断设备编号长度是否超过数据库限制
			if(deviceInfoEntity.getDeviceNo().length() > Constant.DEVICE_NO_MAX_LENGTH) {
				ok = false;
			}
		}
		//判断设备名称是否为空
		if(deviceInfoEntity.getDeviceName() == null) {
			ok = false;
		}else {
			//判断设备名称长度是否超过数据库限制
			if(deviceInfoEntity.getDeviceName().length() > Constant.RESERVE_DEVICENAME_MAX_LENGTH) {
				ok = false;
			}
		}
		//判断设备状态是否为空
		if(deviceInfoEntity.getDeviceStatus() == Constant.RESERVE_LENGTH_NUM_ZERO) {
			ok = false;
		}
		//判断设备参数是否为空
		if(deviceInfoEntity.getDeviceParm() == null) {
			ok = false;
		}else {
			//判断设备参数长度是否超过数据库限制
			if(deviceInfoEntity.getDeviceParm().length() > Constant.RESERVE_DEVICEPARM_MAX_LENGTH) {
				ok = false;
			}
		}
		//判断设备位置是否为空
		if(deviceInfoEntity.getLocationId() == null) {
			ok = false;
		}
		//判断设备说明是否为空
		if(deviceInfoEntity.getCommentInfo() == null) {
			ok = false;
		}else {
			//判断设备说明长度是否超过数据库限制
			if(deviceInfoEntity.getCommentInfo().length() > Constant.RESERVE_COMMENTINFO_MAX_LENGTH) {
				ok = false;
			}
		}

		return ok;

	}


	//设备使用工时录入合法性check
	public boolean reserveHour(DeviceUseInfoEntity deviceUseInfoEntity) {
		boolean ok = true;
		//判断实验设备是否为空
		if(deviceUseInfoEntity.getDeviceNo() == null) {
			ok = false;
		}
		//判断设备类型是否为空
		if(deviceUseInfoEntity.getUseType() == Constant.RESERVE_LENGTH_NUM_ZERO) {
			ok = false;
		}
		//判断使用人是否为空
		if(deviceUseInfoEntity.getUserId() == null) {
			ok = false;
		}
		//判断使用开始时间是否为空
		if(deviceUseInfoEntity.getStartDt() == null) {
			ok = false;
		}
		//判断使用结束时间是否为空
		if(deviceUseInfoEntity.getEndDt() == null) {
			ok = false;
		}
		//判断使用备注是否为空
		if(deviceUseInfoEntity.getCommentInfo() == null) {
			ok = false;
		}else {
			//判断使用备注是否超过数据库限制
			if(deviceUseInfoEntity.getCommentInfo().length() > Constant.RESERVE_COMMENTINFO_MAX_LENGTH) {
				ok = false;
			}
		}

		return ok;
	}
	//===================================== 数据合法性check区域结束 ===============================

}
