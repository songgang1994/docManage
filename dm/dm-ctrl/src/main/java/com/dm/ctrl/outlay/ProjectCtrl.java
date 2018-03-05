package com.dm.ctrl.outlay;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ErrCode;
import com.dm.dto.FileDto;
import com.dm.dto.FileExportDto;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.ResultDto;
import com.dm.dto.SysCfgDto;
import com.dm.entity.UserEntity;
import com.dm.srv.outlay.ProjectSrv;
import com.dm.tool.Constant;
import com.dm.tool.RWExcelUtil;
import com.dm.tool.StringUtil;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 * 课题Controller
 */
@Controller
@RequestMapping("/outlay")
public class ProjectCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(ProjectCtrl.class);
	// 课题用Service
	@Autowired
	private ProjectSrv projectSrv;

	// 课题一览画面显示
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "fyYear", defaultValue = "") String fyYear,
											@RequestParam(value = "deptNames", defaultValue = "") String deptNames,
											@RequestParam(value = "deptId", defaultValue = "") String deptId,
											@RequestParam(value = "projectName", defaultValue = "") String projectName,
											@RequestParam(value = "projectType", defaultValue = "") String projectType) {
		ModelAndView mv = new ModelAndView();
		try {
			deptNames = java.net.URLDecoder.decode(deptNames, "UTF-8");
			projectName = java.net.URLDecoder.decode(projectName, "UTF-8");
			mv.addObject("fyYear", fyYear);
			mv.addObject("deptNames", deptNames);
			mv.addObject("deptId", deptId);
			mv.addObject("projectName", projectName);
			mv.addObject("projectType", projectType);
			mv.setViewName("outlay/ProjectList");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	// 列表数据显示
	@RequestMapping(value = "/subjectList", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<ProjectInfoDto> subjectList( ProjectInfoDto projectInfo,HttpServletResponse res) {
		ResultDto<ProjectInfoDto> resultDto = new ResultDto<ProjectInfoDto>();
		// 获取课题一览列表数据
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
			//获取每页数据
			List<ProjectInfoDto> resultList = projectSrv.getSubjectList(projectInfo,Integer.parseInt(start),len);
			//获取总条数
			int count = projectSrv.getSubjectListCount(projectInfo);
			//新建cookie
			Cookie cookie = new Cookie(Constant.PROJECT_PAGE_LENGTH,length);
			//设置cookie存储路径
			cookie.setPath("/");
			//将cookie传递到页面
			res.addCookie(cookie);
			//设置每页数据
			resultDto.setListData(resultList);
			//将draw传回页面
			resultDto.setDraw(draw);
			//设置原始数据总数
			resultDto.setRecordsTotal(count);
			//设置过滤后数据总数
			resultDto.setRecordsFiltered(count);
			//设置bizeCode
			resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 课题新增画面显示
	@RequestMapping(value = "/addInit", method = RequestMethod.GET)
	public ModelAndView addInit(@RequestParam(value = "fyYear", defaultValue = "") String fyYear,
			@RequestParam(value = "deptNames", defaultValue = "") String deptNames,
			@RequestParam(value = "deptId", defaultValue = "") String deptId,
			@RequestParam(value = "projectName", defaultValue = "") String projectName,
			@RequestParam(value = "projectType", defaultValue = "") String projectType) {
		ModelAndView mv = new ModelAndView();
		try {
			deptNames = java.net.URLDecoder.decode(deptNames, "UTF-8");
			projectName = java.net.URLDecoder.decode(projectName, "UTF-8");
			// 课题信息画面传递flag
			mv.addObject("detailFlag", "add");
			mv.addObject("fyYear", fyYear);
			mv.addObject("deptNames", deptNames);
			mv.addObject("deptId", deptId);
			mv.addObject("projectName", projectName);
			mv.addObject("projectType", projectType);
			mv.setViewName("outlay/ProjectEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	// 课题新增
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> projectAdd(@RequestBody ProjectInfoDto projectForm) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		// 验证数据合法性
		if (!projectCheck(projectForm)) {
			BaseDto base = new BaseDto();
			base.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			resultDto.setData(base);
			return resultDto;
		}
		try {
			// 获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			// 设置课题新增者
			projectForm.setCreator(user.getUserId());
			// 新增课题
			BaseDto baseDto = projectSrv.projectAdd(projectForm);
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

	// 课题修改画面显示
	@RequestMapping(value = "/updInit/{fyYear}/{projectNo}/{deptNames}/{deptId}/{projectName}/{projectType}")
	public ModelAndView updInit(@PathVariable("fyYear") String fyYear, @PathVariable("projectNo") String projectNo
			, @PathVariable("deptNames") String deptNames, @PathVariable("deptId") String deptId, @PathVariable("projectName") String projectName
			, @PathVariable("projectType") String projectType) {
		ModelAndView mv = new ModelAndView();
		try {
			// 获取课题详细信息
			ProjectInfoDto projectInfo = projectSrv.getSubject(fyYear, projectNo);
			// 将详细信息传递会画面
			mv.addObject("projectForm", projectInfo);
			// 设置画面flag状态
			mv.addObject("detailFlag", "upd");
			//将一览画面检索参数传到前台
			mv.addObject("fyYear", fyYear);
			mv.addObject("deptNames", deptNames);
			mv.addObject("deptId", deptId);
			mv.addObject("projectName", projectName);
			mv.addObject("projectType", projectType);
			mv.setViewName("outlay/ProjectEdit");
			return mv;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	// 课题修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> projectUpdate(@RequestBody ProjectInfoDto projectForm) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		// 验证数据合法性
		if (!projectCheck(projectForm)) {
			BaseDto base = new BaseDto();
			base.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			resultDto.setData(base);
			return resultDto;
		}
		try {
			// 获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			projectForm.setUpdater(user.getUserId());
			// 修改课题
			BaseDto baseDto = projectSrv.projectUpdate(projectForm);
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

	// 课题删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> projectDelete(String fyYear, String projectNo) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			// 删除课题
			BaseDto baseDto = projectSrv.projectdelete(fyYear, projectNo);
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

	// 批量导入
	@RequestMapping(value = "/UpdFile", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<BaseDto> projectUpload(@RequestParam(value = "file_input") MultipartFile file,
			@RequestParam(value = "fyyear") String fyyear) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			// 获取登录用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			// 获取上传excel路径
			String path = request.getSession().getServletContext().getRealPath("excel/");
			// 获取excel名称
			String fileName = file.getOriginalFilename();
			// 生成文件流
			File tempFile = new File(path, new Date().getTime() + String.valueOf(fileName));
			// 获取路径
			String filePath = path + tempFile.getName();
			// 如果路径不存在，创建目录
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdir();
			}
			// 当文件不存在时,创建文件
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
			// 文件上传
			file.transferTo(tempFile);
			// 读取文件
			RWExcelUtil rWExcelUtil = new RWExcelUtil();
			List<Row> fileList = rWExcelUtil.readExcel(filePath);
			// 批量导入
			FileDto result = projectSrv.projectUpload(fileList, fyyear, user.getUserId());
			resultDto.setData(result);
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
	public ResultDto<BaseDto> projectExport(@RequestBody ProjectInfoDto projectInfo) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		try {
			ServletOutputStream out = response.getOutputStream();
			//获取根目录
			String filePath1 = request.getSession().getServletContext().getRealPath("/");
			//获取sysconfig数据
			WebApplicationContext webApplicationContext = ContextLoader
					.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext
					.getServletContext();

			SysCfgDto sysCfgInfo = (SysCfgDto) servletContext
					.getAttribute("SysCfgInfo");
			//获取模板路径
			String filePath =filePath1+sysCfgInfo.getProjectPath();
			//导出文件
			FileExportDto baseDto = projectSrv.projectExport(filePath, out, projectInfo);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	// 课题信息合法性check
	public boolean projectCheck(ProjectInfoDto projectInfo) {
		boolean ok = true;

		// 验证课题编号
		if (StringUtil.isEmpty(projectInfo.getProjectNo())
				|| projectInfo.getProjectNo().length() > Constant.PROJECTNO_LENGTH_MAX) {
			ok = false;
		}

		// 验证主题
		if (StringUtil.isEmpty(projectInfo.getProjectName())
				|| projectInfo.getProjectName().length() > Constant.PROJECTNAME_LENGTH_MAX) {
			ok = false;
		}

		// 验证目标
		if (StringUtil.isEmpty(projectInfo.getProjectGoal())
				|| projectInfo.getProjectGoal().length() > Constant.PROJECTGOAL_LENGTH_MAX) {
			ok = false;
		}
		// 验证所属部署
		if (StringUtil.isEmpty(projectInfo.getDeptIds())) {
			ok = false;
		}
		// 验证计划人工
		if (StringUtil.isEmpty(projectInfo.getPlanTimes())
				|| projectInfo.getPlanTimes().doubleValue() > Constant.PORJECT_PLAN_TIMES) {
			ok = false;
		}
		// 验证课题分类
		if (StringUtil.isEmpty(projectInfo.getProjectType())) {
			ok = false;
		}
		// 验证内容
		if (StringUtil.isEmpty(projectInfo.getContents())
				|| projectInfo.getContents().length() > Constant.CONTENTS_LENGTH_MAX) {
			ok = false;
		}
		// 验证法人
		if (StringUtil.isEmpty(projectInfo.getLegalIds())) {
			ok = false;
		}
		// 验证法人比例
		if (StringUtil.isEmpty(projectInfo.getPercentages())) {
			ok = false;
		} else {
			// 获取法人比例集合
			String[] percentageList = projectInfo.getPercentages().split(",");
			double count = Constant.RESERVE_LENGTH_NUM_ZEROPOINT;
			// 遍历法人比例集合
			for (int i = Constant.RESERVE_LENGTH_NUM_ZERO; i < percentageList.length; i++) {
				count = count + Double.valueOf(percentageList[i]);
			}
			// 判断比例和是否为100%
			if (count != Constant.PROJECT_PERCENTAGE_COUNT) {
				ok = false;
			}
		}
		return ok;
	}
}
