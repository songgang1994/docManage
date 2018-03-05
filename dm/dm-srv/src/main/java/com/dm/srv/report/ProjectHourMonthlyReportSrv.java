package com.dm.srv.report;

import java.util.List;
import javax.servlet.ServletOutputStream;

import com.dm.dto.BaseDto;
import com.dm.dto.ProjectInfoDto;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	课题人工统计月度报表Server
 */
public interface ProjectHourMonthlyReportSrv {
	//获取课题人工统计月度报表数据
	List<ProjectInfoDto> getProjectHourMonthlyList(ProjectInfoDto projectInfo);
	//导出excel
	BaseDto projectHourMonthlyExport(String filePath,String[] deptIdList,String[] deptNumber,  ServletOutputStream out,List<ProjectInfoDto> list,String dateInfo);
}
