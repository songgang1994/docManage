package com.dm.ctrl.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.annotation.JSONField;
import com.dm.ctrl.BaseCtrl;
import com.dm.ctrl.util.LoginCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.ErrCode;
import com.dm.dto.FileExportDto;
import com.dm.dto.MonthHourTableDto;
import com.dm.dto.ResultDto;
import com.dm.dto.StaffDeptInfoDto;
import com.dm.dto.SysCfgDto;
import com.dm.dto.TableDto;
import com.dm.entity.DeviceInfoEntity;
import com.dm.entity.DeviceUseInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.device.DeviceSrv;
import com.dm.srv.report.ReportSrv;
import com.dm.tool.Constant;
import com.dm.tool.RWExcelUtil;
import com.dm.tool.StringUtil;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
@Controller
@RequestMapping("/report")
public class ReportCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(LoginCtrl.class);

	@Autowired
	private ReportSrv reportSrv;

	//实验设备统计页面
	@RequestMapping(value = "/devicestatistics")
	public ModelAndView DeviceStatistics() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/DeviceStatistics");
		return mv;
	}

	//获取指定设备，指定期间内的统计数据
	@RequestMapping("/getDeviceStatistics")
	@ResponseBody
	private ResultDto<TableDto> getDeviceStatistics(
			@RequestParam("deviceNos") String[] deviceNos,
			@RequestParam("startDt") @JSONField(format = "yyyy/MM/dd HH:mm:ss") Date startDt,
			@RequestParam("endDt") @JSONField(format = "yyyy/MM/dd HH:mm:ss") Date endDt) {
		ResultDto<TableDto> resultDto = new ResultDto<TableDto>();

		try {
			//业务处理
			TableDto table = reportSrv.getDeviceStatistics(deviceNos, startDt, endDt);
			//保存实验设备统计数据，以便导出
			session.setAttribute(Constant.SAVE_DEVICE_STATISTICS_KEY, table);
			resultDto.setData(table);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	//导出实验设备统计
	@RequestMapping("/exportDeviceStatistics")
	@ResponseBody
	private ResultDto<BaseDto> exportDeviceStatistics() {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			//从session中获取已保存的实验设备统计
			TableDto table  =(TableDto)session.getAttribute(Constant.SAVE_DEVICE_STATISTICS_KEY);
			// 文档导出
			FileExportDto baseDto = reportSrv.exportDeviceStatistics(table);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//部署年度文档统计页面
	@RequestMapping(value = "/departannualstatistics")
	public ModelAndView DepartAnnualStatistics() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/DepartAnnualStatistics");
		return mv;
	}

	//获取部署年度文档统计数据
	@RequestMapping("/getDepartAnnualStatistics")
	@ResponseBody
	private ResultDto<TableDto> getDepartAnnualStatistics(
			@RequestParam("fyYear") @JSONField(format = "yyyy/MM/dd HH:mm:ss") Date fyYear,
			@RequestParam("docTypes[]") String[] docTypes) {
		ResultDto<TableDto> resultDto = new ResultDto<TableDto>();

		try {
			//业务处理
			TableDto table = reportSrv.getDepartAnnualStatistics(fyYear, docTypes);
			//保存实验设备统计数据，以便导出
			session.setAttribute(Constant.SAVE_DEVICE_ANNUAL_STATISTICS_KEY, table);
			resultDto.setData(table);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	//导出部署年度文档统计数据
	@RequestMapping("/exportDepartAnnualStatistics")
	@ResponseBody
	private ResultDto<BaseDto> exportDepartAnnualStatistics(String deptName,String fyYear) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		FileExportDto baseDto = new FileExportDto();

		try {
			//从session中获取已保存的实验设备统计
			TableDto table  =(TableDto)session.getAttribute(Constant.SAVE_DEVICE_ANNUAL_STATISTICS_KEY);
			// 文档导出
			baseDto = reportSrv.exportDepartAnnualStatistics(table);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

}
