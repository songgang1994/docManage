package com.dm.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DepartAnnualStatisticsDto;
import com.dm.dto.DeviceStatisticsDto;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：数据统计
 */
public interface ReportDao {

	/**
	 * 获取指定设备，指定期间内的统计数据
	 * @param deviceNo	设备No
	 * @param startDt   开始日期
	 * @param endDt     结束日期
	 * @return
	 */
	DeviceStatisticsDto getDeviceStatistics(@Param("deviceNo") String deviceNo, @Param("startDt") Date startDt,
			@Param("endDt") Date endDt);

	/**
	 * 获取部署年度文档统计的部门
	 * @param docTypes  文档类型数组
	 * @param startDt	开始日期
	 * @param endDt		结束日期
	 * @return
	 */
	List<String> getDepts(@Param("docTypes") String[] docTypes, @Param("startDt") Date startDt,
			@Param("endDt") Date endDt);

	/**
	 * 获取部署年度文档统计
	 * @param deptIds	含有数据的部门ids
	 * @param docTypes	文档类型数组
	 * @param startDt	开始日期
	 * @param endDt		结束日期
	 * @return
	 */
	List<DepartAnnualStatisticsDto> getDepartAnnualStatistics(@Param("deptIds") List<String> deptIds,
			@Param("docTypes") String[] docTypes, @Param("startDt") Date startDt, @Param("endDt") Date endDt);
}
