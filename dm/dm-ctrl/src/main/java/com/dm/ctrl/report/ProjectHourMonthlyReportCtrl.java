package com.dm.ctrl.report;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ErrCode;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.ResultDto;
import com.dm.dto.SysCfgDto;
import com.dm.srv.report.ProjectHourMonthlyReportSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *  课题人工统计月度报表Controller
 */
@Controller
@RequestMapping("/report")
public class ProjectHourMonthlyReportCtrl extends BaseCtrl{
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(ProjectHourMonthlyReportCtrl.class);
	//课题人工统计月度报表Service
	@Autowired
	private ProjectHourMonthlyReportSrv projectHourMonthlyReportSrv;

	//课题人工统计月度报表初始化
	@RequestMapping(value ="/projecthourmonthlyreport")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("report/ProjectHourMonthlyReport");
		return mv;
	}

	//课题人工统计月度报表列表画面显示
	@RequestMapping(value = "/projecthourmonthlyList", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<ProjectInfoDto> projecthourmonthlyList(@ModelAttribute("projectInfo") ProjectInfoDto projectInfo) {
		ResultDto<ProjectInfoDto> resultDto = new ResultDto<ProjectInfoDto>();
		//获取课题人工统计月度报表数据
		try {
			List<ProjectInfoDto> resultList = projectHourMonthlyReportSrv.getProjectHourMonthlyList(projectInfo);
			//存储年月
			resultList.get(0).setDateInfo(projectInfo.getDateInfo());
			//存储课题人工统计月度报表数据，便于导出使用
			session.setAttribute(Constant.SAVE_PROJECT_HOUR_MONTH_LIST_KEY, resultList);
			//将数据传递到画面
			resultDto.setListData(resultList);
			resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

		// 课题导出
		@RequestMapping(value = "/export", method = RequestMethod.POST)
		@ResponseBody
		public ResultDto<BaseDto> projectExport(String[] deptIdList,String[] deptNumber,String dateInfo) {
			ResultDto<BaseDto> resultDto = new ResultDto<>();
			try {
				//输出文件初始化
				ServletOutputStream out = response.getOutputStream();
				//获取根目录
				String filePath1 = request.getSession().getServletContext().getRealPath("/");
				//获取课题人工统计月度报表数据
				List<ProjectInfoDto> list = (List<ProjectInfoDto>) session.getAttribute(Constant.SAVE_PROJECT_HOUR_MONTH_LIST_KEY);
				//获取sysconfig数据
				WebApplicationContext webApplicationContext = ContextLoader
						.getCurrentWebApplicationContext();
				ServletContext servletContext = webApplicationContext
						.getServletContext();

				SysCfgDto sysCfgInfo = (SysCfgDto) servletContext
						.getAttribute("SysCfgInfo");
				//获取模板路径
				String filePath = filePath1+sysCfgInfo.getProjectHourMonthlyReportPath();
				//导出文件
				BaseDto baseDto = projectHourMonthlyReportSrv.projectHourMonthlyExport(filePath,deptIdList,deptNumber, out, list,dateInfo);
				//移除session
				session.removeAttribute(Constant.SAVE_PROJECT_LIST_KEY);
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
