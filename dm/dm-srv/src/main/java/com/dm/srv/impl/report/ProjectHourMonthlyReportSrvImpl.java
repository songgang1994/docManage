package com.dm.srv.impl.report;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.ProjectDao;
import com.dm.dao.ProjectHourMonthlyReportDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ProjectInfoDto;
import com.dm.dto.SubjectLegalDto;
import com.dm.dto.DepartManageInfoDto;
import com.dm.dto.FileExportDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.report.ProjectHourMonthlyReportSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题人工统计月度报表Service
 */
@Service
public class ProjectHourMonthlyReportSrvImpl extends BaseSrvImp implements ProjectHourMonthlyReportSrv {
	// 课题人工统计月度报表Dao
	@Autowired
	private ProjectHourMonthlyReportDao projectHourMonthlyReportDao;
	//课题Dao
	@Autowired
	private ProjectDao projectDao;
	@Override
	public List<ProjectInfoDto> getProjectHourMonthlyList(ProjectInfoDto projectInfo) {
		// 申明查询变量，默认值设置为“”
		String projectName = "";
		String[] deptIdList = null;
		String projectDate=null;
		//获取年月
		if (projectInfo.getDateInfo() != null && !projectInfo.getDateInfo().isEmpty()) {
			String year = projectInfo.getDateInfo().substring(0,4);
			String month = projectInfo.getDateInfo().substring(5,7);
		   projectDate = year+"-"+month;
		}
		// 当部门Id不为空的场合
		if (projectInfo.getDeptId() != null && !projectInfo.getDeptId().isEmpty()) {
			deptIdList = projectInfo.getDeptId().split(",");
		}
		//当课题主题不为空的场合
		if (projectInfo.getProjectName() != null && !projectInfo.getProjectName().isEmpty()) {
			projectName = projectInfo.getProjectName();
		}
		//设置查询年月
		projectInfo.setProjectDate(projectDate);
		//设置查询Id集合
		projectInfo.setDeptIdList(deptIdList);
		//设置查询课题主题
		projectInfo.setProjectName(projectName);
		//设置课题分类静态常量
		String type1 = Constant.PROJECT_TYPE;
		//设置法人静态常量
		String type2 = Constant.LEGAL;
		// 获取课题人工统计月度报表数据
		List<ProjectInfoDto> projectHourMonthlyList = projectHourMonthlyReportDao.getProjectHourMonthlyList(projectInfo,
				type1,type2);
		List<String[]> deptList = getDepts(projectHourMonthlyList);
		projectHourMonthlyList.get(0).setDeptNameList(deptList.get(0));
		projectHourMonthlyList.get(0).setDeptIdList(deptList.get(1));
		return projectHourMonthlyList;
	}

	//导出文件
	@Override
	public FileExportDto projectHourMonthlyExport(String filePath,String[] deptIdList,String[] deptNumber, ServletOutputStream out, List<ProjectInfoDto> list,String dateInfo) {
		FileExportDto baseDto = new FileExportDto();
		try {
			//获取样本文件
			FileInputStream is = new FileInputStream(filePath);
			//获取excel
			Workbook workbook = null;
			try {
				workbook = new HSSFWorkbook(is);
			} catch (Exception ex) {
			    // 解决read error异常
			    is = new FileInputStream(filePath);
			    workbook = new XSSFWorkbook(is);
			}
			// 获取第一个sheet
			Sheet  hssfSheet = workbook.getSheetAt(0);
		//设置第1行
			//获取第2行第1列样式
			CellStyle column0 = hssfSheet.getRow(1).getCell(1).getCellStyle();
			//获取年度
			int year = Integer.parseInt(dateInfo.substring(0, 4));
			int month = Integer.parseInt(dateInfo.substring(5, 7));
			//当月小于4月的场合
			if(month < 4) {
				//年度-1
				year=year-1;
			}
			String FyYear = String.valueOf(year);
			//获取第一行FY年度
			String Fy = "FY"+FyYear.substring(2,4)+"年度";
			//设置第一行，第1列
			Row hssfRow = hssfSheet.getRow(0);
			//创建单元格
			Cell a0 = hssfRow.createCell(1);
			//设置单元格样式
			a0.setCellStyle(column0);
			//设置单元格值
			a0.setCellValue(Fy);
		//设置第2行
			//获取第2行
			 Row hssfRow1 = hssfSheet.getRow(1);
			//创建单元格
			 Cell b1 = hssfRow1.createCell(2);
			//设置单元格样式
			 b1.setCellStyle(column0);
			//设置单元格值
			 b1.setCellValue(dateInfo);
			//获取法人名称集合
			String type1 = Constant.LEGAL;
			List<String> legalList = projectDao.getLegalList(type1);
		//设置各法人人工负担额和法人
			//获取第3行
			Row hssfRow2 = hssfSheet.getRow(2);
			//获取第4行
			Row hssfRow3 = hssfSheet.getRow(3);
			//设置默认值
			Cell hssfCell = null;
			Cell cellLegal = null;
			//获取第3行和第4行对应excel样本式样
			CellStyle columnlegal = hssfSheet.getRow(2).getCell(9).getCellStyle();
			CellStyle columnlegal1 = hssfSheet.getRow(2).getCell(10).getCellStyle();
			CellStyle columnNum = hssfSheet.getRow(3).getCell(9).getCellStyle();
			CellStyle columnNum1 = hssfSheet.getRow(3).getCell(10).getCellStyle();
			//循环遍历法人
			for (int i = 0; i < legalList.size(); i++) {
				hssfCell = hssfRow3.createCell(9+i);
				//当i不为0的场合
				if(i!=0) {
					cellLegal = hssfRow2.createCell(9+i);
				}
				if(i==legalList.size()-1) {
				//当i为最大值的场合
					hssfCell.setCellStyle(columnNum1);
					//当i不为0的场合
					if(i!=0) {
						cellLegal.setCellStyle(columnlegal1);
					}
				}else {
				//当i不为最大值的场合
					hssfCell.setCellStyle(columnNum);
					//当i不为0的场合
					if(i!=0) {
						cellLegal.setCellStyle(columnlegal);
					}
				}
				//设置法人名称
				hssfCell.setCellValue(legalList.get(i));
			}
			//总列数
			int countColumn = 9+legalList.size();
			//各法人人工负担额单元格合并
			CellRangeAddress cra=new CellRangeAddress(2, 2, 9, countColumn-1);
	        //在sheet里增加合并单元格
			hssfSheet.addMergedRegion(cra);
			//获取合并单元格
			cellLegal = hssfSheet.getRow(2).getCell(9);
			//设置单元格样式
			cellLegal.setCellStyle(columnlegal1);
			//获取excel样本中间行样式(第1列，中间，最后1列)
			CellStyle columnOne = hssfSheet.getRow(4).getCell(1).getCellStyle();
			CellStyle columnOne1 = hssfSheet.getRow(4).getCell(2).getCellStyle();
			CellStyle columnOne2 = hssfSheet.getRow(4).getCell(10).getCellStyle();
			//获取excel样本最后行样式(第1列，中间，最后1列)
			CellStyle columnLast = hssfSheet.getRow(5).getCell(1).getCellStyle();
			CellStyle columnLast1 = hssfSheet.getRow(5).getCell(2).getCellStyle();
			CellStyle columnLast2 = hssfSheet.getRow(5).getCell(10).getCellStyle();
			CellStyle columnLast3 = hssfSheet.getRow(6).getCell(6).getCellStyle();
		//  写入实体数据
			if (list != null && !list.isEmpty()) {
				//循环遍历数据
				for (int i = 0; i < list.size(); i++) {
					//第5行到最后行下移1行
					hssfSheet.shiftRows(4, hssfSheet.getLastRowNum(), 1,true,false);
					//创建第5行
					hssfRow = hssfSheet.createRow(4);
					//获取数据
					ProjectInfoDto projectInfoDto = list.get(i);
					//部门集合
					String deptId = "";
					hssfCell = hssfRow.createCell(1);
					if (projectInfoDto.getDeptId() != null) {
						deptId = projectInfoDto.getDeptId();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast);
					}else {
						hssfCell.setCellStyle(columnOne);
					}
					hssfCell.setCellValue(deptId);
					//课题分类
					String dispName = "";
					hssfCell = hssfRow.createCell(2);
					if (projectInfoDto.getDispName() != null) {
						dispName = projectInfoDto.getDispName();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					hssfCell.setCellValue(dispName);
					//课题主题
					String projectName = "";
					hssfCell = hssfRow.createCell(3);
					if (projectInfoDto.getProjectName() != null) {
						projectName = projectInfoDto.getProjectName();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					hssfCell.setCellValue(projectName);
					//课题内容
					String contents = "";
					hssfCell = hssfRow.createCell(4);
					if (projectInfoDto.getContents() != null) {
						contents = projectInfoDto.getContents();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					hssfCell.setCellValue(contents);
					//课题编号
					String projectNo = "";
					hssfCell = hssfRow.createCell(5);
					if (projectInfoDto.getProjectNo() != null) {
						projectNo = projectInfoDto.getProjectNo();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					hssfCell.setCellValue(projectNo);
					//计划人工
					BigDecimal planTimes = new BigDecimal(0);
					hssfCell = hssfRow.createCell(6);
					if (!projectInfoDto.getPlanTimes().equals(BigDecimal.ZERO)) {
						planTimes = projectInfoDto.getPlanTimes();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					hssfCell.setCellValue(planTimes.doubleValue());
					//工作时间
					BigDecimal timesTotal = new BigDecimal(0);
					hssfCell = hssfRow.createCell(7);
					if (projectInfoDto.getTimesTotal()!=null&&!projectInfoDto.getTimesTotal().equals(BigDecimal.ZERO)) {
						timesTotal = projectInfoDto.getTimesTotal();
					}
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					hssfCell.setCellValue(timesTotal.doubleValue());
					//人工
					hssfCell = hssfRow.createCell(8);
					if(i==0) {
						hssfCell.setCellStyle(columnLast1);
					}else {
						hssfCell.setCellStyle(columnOne1);
					}
					//获取部门总时间
					BigDecimal timesTotals = list.get(i).getTimesTotal();
					for(int j=0;j<list.size();j++) {
						if(list.get(j).getDeptId().equals(deptId)&&i!=j) {
							timesTotals = timesTotals.add(list.get(j).getTimesTotal());
						}
					}
					String departId =  "\""+deptId+ "\"";
					String lookUps = "LOOKUP("+departId+",人数!A:A,人数!B:B)";
					String formula = "H5/"+timesTotals+"*("+lookUps+")";
					hssfCell.setCellFormula(formula);
					//各法人人工负担额
					List<SubjectLegalDto> legals =projectInfoDto.getSubjectLegal();
					//循环遍历法人集合
						for(int j=0;j<legalList.size();j++) {
							hssfCell = hssfRow.createCell(9+j);
							BigDecimal percentage = new BigDecimal(0);
							//判断对应法人是否为空
							if (legals!= null&&!legals.isEmpty()) {
								//循环遍历课题对应法人集合
								for(int m=0;m<legals.size();m++) {
									//判断是否含有此法人
									if(legalList.get(j).trim().equals(legals.get(m).getDispName().trim())) {
										//获取法人比例
										percentage = legals.get(m).getPercentage();
										break;
									}
								}
							}
							//当i为1的场合
							if(i==0) {
								//当是最后一列的场合
								if(j==legalList.size()-1) {
									//设置单元格样式
									hssfCell.setCellStyle(columnLast2);
								}else {
									//当不是最后一列的场合
									hssfCell.setCellStyle(columnLast1);
								}
							}else {
							//当i不为1的场合
								//当是最后一列的场合
								if(j==legalList.size()-1) {
									hssfCell.setCellStyle(columnOne2);
								}else {
								//当不是最后一列的场合
									hssfCell.setCellStyle(columnOne1);
								}
							}
							//获取比例
							percentage = percentage.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
							//设置法人对应比例
							hssfCell.setCellFormula("I5*"+percentage);
						}
				}
			}
			 //删除倒数第2行和倒数第3行
			 Row row = hssfSheet.getRow(list.size()+4);
			 hssfSheet.removeRow(row);
			 Row row1 = hssfSheet.getRow(list.size()+5);
			 hssfSheet.removeRow(row1);
			 //最后一行上移2行
			 hssfSheet.shiftRows(list.size()+6, list.size()+6, -2);
			//合计
			 //最后行号
			 int lineLast = list.size()+4;
			 Row row3 = hssfSheet.getRow(lineLast);
			 //计划人工
			 hssfCell = row3.createCell(6);
			 hssfCell.setCellStyle(columnLast3);
			 hssfCell.setCellFormula("sum(G5:G"+lineLast+")");
			//工作时间
			 hssfCell = row3.createCell(7);
			 hssfCell.setCellStyle(columnLast3);
			 hssfCell.setCellFormula("sum(H5:H"+lineLast+")");
			 //人工
			 hssfCell = row3.createCell(8);
			 hssfCell.setCellStyle(columnLast3);
			 hssfCell.setCellFormula("sum(I5:I"+lineLast+")");
			 //法人
			for(int i=0;i<legalList.size();i++) {
				hssfCell = row3.createCell(9+i);
				hssfCell.setCellStyle(columnLast3);
				String a =CellReference.convertNumToColString(9+i);
				hssfCell.setCellFormula("sum("+a+"5:"+a+""+lineLast+")");
			}
		//获取sheet2
			Sheet  hssfSheet1 = workbook.getSheetAt(1);
			//获取第2行的1，2列样式
			CellStyle column = hssfSheet1.getRow(2).getCell(0).getCellStyle();
			CellStyle column1 = hssfSheet1.getRow(2).getCell(1).getCellStyle();
			//循环遍历部门Id
			if (deptIdList != null && deptIdList.length!=0) {
				for (int i = 0; i < deptIdList.length; i++) {
					//第3行下移1行
					hssfSheet1.shiftRows(2, hssfSheet1.getLastRowNum(), 1,true,false);
					//创建第3行
					hssfRow = hssfSheet1.createRow(2);
					//部门Id
					String deptId = "";
					hssfCell = hssfRow.createCell(0);
					if(deptIdList[i]!=null) {
						deptId = deptIdList[i];
					}
					hssfCell.setCellStyle(column);
					hssfCell.setCellValue(deptId);
					//部门对应人数
					String deptNum = "";
					hssfCell = hssfRow.createCell(1);
					if(deptNumber[i]!=null) {
						deptNum = deptNumber[i];
					}
					hssfCell.setCellStyle(column1);
					hssfCell.setCellValue(deptNum);
				}
			}
			 Row row2 = hssfSheet1.getRow(deptIdList.length+2);
			 hssfSheet1.removeRow(row2);
			 hssfSheet1.shiftRows(deptIdList.length+3, deptIdList.length+3, -1);
			 //最后一行
			 int lineLast1 = deptIdList.length+2;
			 Row row4 = hssfSheet1.getRow(lineLast1);
			 hssfCell = row4.createCell(1);
			 hssfCell.setCellStyle(column1);
			 hssfCell.setCellFormula("SUMPRODUCT(B3:B"+lineLast1+"*1)");
			// 第七步，将文件输出到客户端浏览器
			try {
				//文件导出路径
				String fileName= Constant.PROJECT_HOUR_MONTHLY_REPORT_NAME.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDDHHmmSS")));
				String file_Path = Constant.FILE_EXPORT_PATH+fileName+Constant.FILE_EXPORT_TYPE;
				FileOutputStream fileOutputStream = new FileOutputStream(file_Path);// 指定路径与名字和格式
				workbook.write(fileOutputStream);// 将数据写出去
				fileOutputStream.close();// 关闭输出流
				baseDto.setFileName(fileName);
			} catch (Exception e) {
				baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
				return baseDto;
			}
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
			return baseDto;
		} catch (Exception e) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
	}

	//获取所有部门名称
	public List<String[]> getDepts(List<ProjectInfoDto> projectInfo) {
		List<String[]> deptList = new ArrayList<String[]>();
		List<String> deptIds = new ArrayList<String>();
		List<String> deptNames = new ArrayList<String>();
		 //当projectInfo集合不为空的场合
		 if(projectInfo!=null&&projectInfo.size()!=0) {
			 //循环遍历projectInfo集合
			 for(int i=0;i<projectInfo.size();i++) {
				//判断集合中是否已存在
				if(!deptIds.contains(projectInfo.get(i).getDeptId())) {
					deptIds.add(projectInfo.get(i).getDeptId());
					deptNames.add(projectInfo.get(i).getDeptName());
				}

			 }
		 }
		 //获取部门名称集合
		 String[] deptNameList = deptNames.toArray(new String[deptNames.size()]);
		 String[] deptIdList = deptIds.toArray(new String[deptIds.size()]);
		 deptList.add(deptNameList);
		 deptList.add(deptIdList);
		return deptList;
	}
}
