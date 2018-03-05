package com.dm.srv.outlay;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Row;

import com.dm.dto.BaseDto;
import com.dm.dto.FileDto;
import com.dm.dto.FileExportDto;
import com.dm.dto.ProjectInfoDto;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	课题一览Server
 */
public interface ProjectSrv {
	//获取课题列表数据
	List<ProjectInfoDto> getSubjectList(ProjectInfoDto projectInfo,int start,int length);
	// 获取课题总共记录数
	int getSubjectListCount(ProjectInfoDto projectInfo);
	//新增课题
	BaseDto projectAdd(ProjectInfoDto projectForm);
	//课题信息获取
	ProjectInfoDto getSubject(String fyYear,String projectNo);
	//删除课题
	BaseDto projectdelete(String fyYear,String projectNo);
	//课题编辑
	BaseDto projectUpdate(ProjectInfoDto projectForm);
	//课题导出
	FileExportDto projectExport(String filePath, ServletOutputStream out,ProjectInfoDto projectInfo);
	//课题导入
	FileDto projectUpload(List<Row> fileList,String fyyear,String creator);
}
