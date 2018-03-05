package com.dm.srv.impl.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.ReportDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DepartAnnualStatisticsDto;
import com.dm.dto.DeviceStatisticsDto;
import com.dm.dto.FileExportDto;
import com.dm.dto.TableDto;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.report.ReportSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
@Service(value="reportSrv")
public class ReportSrvImpl extends BaseSrvImp implements ReportSrv{

	@Autowired
	private ReportDao reportDao;

	//获取指定设备，指定期间内的统计数据
	@Override
	public TableDto getDeviceStatistics(String[] deviceNos, Date startDt, Date endDt) {

		TableDto tableDto = new TableDto();

		//获取开始和结束日期
		Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDt);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDt);

        //计算开始和结束日期相差几个月
        int months = 0;
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        months = year*12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH) +1;


        //准备表格对象
        //table二位数组
		String[][] table = new String[deviceNos.length+2][months*3 + 2];

		//第一行的第一列和第二列的表头
		table[0][0] = "设备编号";
		table[0][1] = "设备名称";


		Calendar c2 = Calendar.getInstance();
		for (int i = 2; i < months*3 + 2 ; i++) {
			c2.setTime(startDt);
			c2.add(Calendar.MONTH,(i-2)/3);// 从开始月起，增加几个月
			//第一行剩下的列
			table[0][i] = String.valueOf(c2.get(Calendar.MONTH) + 1)+"月";//月（必须要+1）

			//第二行
			table[1][i] = "工作时间";
			table[1][i+1] = "维护时间";
			table[1][i+2] = "使用率";

			i += 2;//每个月有三个子列
		}

		//获取每行数据（一个设备号一行）
		for(int i=0; i<deviceNos.length;i++) {
			DeviceStatisticsDto d = new DeviceStatisticsDto();
			//获取行里的每一列
			for(int j = 0; j < months; j++) {
				Calendar c = Calendar.getInstance();
				c.setTime(startDt);
				c.add(Calendar.MONTH,j);// 从开始月起，增加几个月
				c.set(Calendar.HOUR_OF_DAY, 0);// 0点
		        c.set(Calendar.MINUTE, 0);// 0分
		        c.set(Calendar.SECOND, 0);// 0秒
		        c.add(Calendar.DAY_OF_MONTH, 0);// 当月1号
		        Date first = new Date();
		        first = c.getTime();
		        c.add(Calendar.MONTH,1);// 再加一个月
		        c.add(Calendar.SECOND, -1);//再减1秒 ，即本月最后一天23:59:59
		        Date last = new Date();
		        last = c.getTime();
		        d = reportDao.getDeviceStatistics(deviceNos[i], first, last);
		        table[i+2][j*3+2] = String.valueOf(d.getWorkingTime());//每个设备每月使用时间
		        table[i+2][j*3+3] = String.valueOf(d.getMaintenanceTime());//每个设备每月维护时间
		        table[i+2][j*3+4] = String.valueOf(d.getUsageRate());//每个设备每月使用率
			}
			 table[i+2][0] = d.getDeviceNo();//每个设备No
			 table[i+2][1] = d.getDeviceName();//每个设备名称
		}

		tableDto.setTable(table);
		return tableDto;
	}

	//导出指定设备，指定期间内的统计数据
	@Override
	public FileExportDto exportDeviceStatistics(TableDto tableDto) throws IOException {
		FileExportDto baseDto = new FileExportDto();
		List<Row> rows = new ArrayList<>();
		String[][] table = tableDto.getTable();
		// 获取总列数
	    int CountColumnNum = table[0].length;
	    // 创建Excel文档
	    HSSFWorkbook hwb = new HSSFWorkbook();
	    // sheet 对应一个工作页
	    HSSFSheet sheet = hwb.createSheet();

	    //查询结果
	    for (int i = 0; i < table.length; i++) {
	        // 创建一行
	        HSSFRow row = sheet.createRow(i);
	        for (int j = 0; j < CountColumnNum; j++) {
		        // 在一行内循环
		        HSSFCell x = row.createCell(j);
		        x.setCellValue(new HSSFRichTextString(table[i][j]));
	        }
	        //行添加
	        rows.add(row);
	    }
	    //合并单元格
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
	    for(int i=2; i<CountColumnNum; i++) {
		    sheet.addMergedRegion(new CellRangeAddress(0, 0, i, i+2));
		    i += 2;
	    }
		//文件导出路径
		 String fileName= Constant.DEVICE_EXPORT_NAME.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDDHHmmSS")));
		 String file_Path = Constant.FILE_EXPORT_PATH +fileName+Constant.FILE_EXPORT_TYPE;
	    // 重新将数据写入Excel中
		FileOutputStream outputStream = new FileOutputStream(file_Path);
		hwb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		baseDto.setFileName(fileName);
	    //导出有问题 不能合并单元格
//	    RWExcelUtil u = new RWExcelUtil();
//		u.writeExcel(rows);

		return baseDto;
	}

	// 获取部署年度文档统计
	@Override
	public TableDto getDepartAnnualStatistics(Date fyYear, String[] docTypes) {
		TableDto tableDto = new TableDto();

		//获取查询年度
		Calendar fyYearCalendar = Calendar.getInstance();
		fyYearCalendar.setTime(fyYear);

		//将查询年度转为日本年度 4月-次年3月
		//开始时间   4月1日00:00:00
		fyYearCalendar.add(Calendar.MONTH,3);// 从开始月起，增加几个月
		fyYearCalendar.set(Calendar.HOUR_OF_DAY, 0);// 0点
		fyYearCalendar.set(Calendar.MINUTE, 0);// 0分
		fyYearCalendar.set(Calendar.SECOND, 0);// 0秒
		fyYearCalendar.add(Calendar.DAY_OF_MONTH, 0);// 当月1号
        Date first = new Date();
        first = fyYearCalendar.getTime();

        //结束时间   次年3月31日23:59:59
        fyYearCalendar.add(Calendar.MONTH,12);// 再加12个月
        fyYearCalendar.add(Calendar.SECOND, -1);//再减1秒 ，即上月最后一天23:59:59
        Date last = new Date();
        last = fyYearCalendar.getTime();


        //获取含有数据的部门
        List<String> deptList = new ArrayList<>();
        deptList = reportDao.getDepts(docTypes, first, last);

        //当没有部门时
        if(deptList==null || deptList.size()<1) {
        	tableDto.setBizCode(BizCode.BIZ_CODE_RECORD_0);
        	return tableDto;
        }

        //获取数据
        List<DepartAnnualStatisticsDto> list= new ArrayList<>();
        list = reportDao.getDepartAnnualStatistics(deptList, docTypes, first, last);

        //准备表格对象
        //table二位数组
		String[][] table = new String[list.size()+1][docTypes.length+3];

		//准备获得年
		Calendar c = Calendar.getInstance();
		c.setTime(first);
		String tableYear = String.valueOf(c.get(Calendar.YEAR));//表格中显示的年份

		//获取每行数据（一个设备号一行）
		for(int i=0; i<list.size()+1; i++) {

			//获取行里的每一列
			for(int j = 0; j < docTypes.length; j++) {
				//右侧数据区域
				if(i == 0) {
					table[i][j+3] = list.get(i).getAnnualStatisticsDtoList().get(j).getItemName();//数据区 表头
				}else {
					c.setTime(list.get(i-1).getYears());
					table[i][2] = tableYear;//年度
					int data = list.get(i-1).getAnnualStatisticsDtoList().get(j).getCnt();
					table[i][j+3] = String.valueOf(data == 0 ? "" : data);//数据(将0变为""显示)
				}
			}

			//左侧行头区域
			if(i == 0) {
				//第一行的第一列和第二列的表头
				table[0][0] = "序号";
				table[0][1] = "部门";
				table[0][2] = "年度";
			}else {
				table[i][0] = String.valueOf(i);//序号
				table[i][1] = list.get(i-1).getDeptName();//每个部门名称
			}
		}

		tableDto.setTable(table);
		return tableDto;
	}

	// 导出部署年度文档统计
	@Override
	public FileExportDto exportDepartAnnualStatistics(TableDto tableDto) throws IOException {
		FileExportDto baseDto = new FileExportDto();
		List<Row> rows = new ArrayList<>();
		String[][] table = tableDto.getTable();
		// 获取总列数
	    int CountColumnNum = table[0].length;
	    // 创建Excel文档
	    HSSFWorkbook hwb = new HSSFWorkbook();
	    // sheet 对应一个工作页
	    HSSFSheet sheet = hwb.createSheet();

	    //查询结果
	    for (int i = 0; i < table.length; i++) {
	        // 创建一行
	        HSSFRow row = sheet.createRow(i);
	        for (int j = 0; j < CountColumnNum; j++) {
		        // 在一行内循环
		        HSSFCell x = row.createCell(j);
		        x.setCellValue(new HSSFRichTextString(table[i][j]));
	        }
	        //行添加
	        rows.add(row);
	    }

	    // 重新将数据写入Excel中
	    //文件导出名称
		String fileName= Constant.DEPARTANNUAL_EXPORT_NAME.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDDHHmmSS")));
		 String file_Path =Constant.FILE_EXPORT_PATH + fileName+Constant.FILE_EXPORT_TYPE;
		FileOutputStream outputStream = new FileOutputStream(file_Path);
		hwb.write(outputStream);
		outputStream.flush();
		outputStream.close();
		baseDto.setFileName(fileName);
		return baseDto;
	}
}
