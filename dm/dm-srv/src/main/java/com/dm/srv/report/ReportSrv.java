package com.dm.srv.report;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.dm.dto.BaseDto;
import com.dm.dto.FileExportDto;
import com.dm.dto.TableDto;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
public interface ReportSrv {


	/**
	 * 获取指定设备，指定期间内的统计数据
	 * @param deviceNos 设备Nos
	 * @param startDt	开始日期
	 * @param endDt		结束日期
	 * @return
	 */
	TableDto getDeviceStatistics(String[] deviceNos, Date startDt, Date endDt);

	/**
	 * 导出指定设备，指定期间内的统计数据
	 * @param table
	 * @return
	 * @throws IOException
	 */
	FileExportDto exportDeviceStatistics(TableDto tableDto) throws IOException;


	/**
	 * 获取部署年度文档统计
	 * @param fyYear
	 * @param docTypes
	 * @return
	 */
	TableDto getDepartAnnualStatistics(Date fyYear, String[] docTypes);


	/**
	 * 导出部署年度文档统计
	 * @param table
	 * @return
	 * @throws IOException
	 */
	FileExportDto exportDepartAnnualStatistics(TableDto tableDto) throws IOException;

}
