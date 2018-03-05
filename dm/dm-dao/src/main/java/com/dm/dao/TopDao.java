package com.dm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DocWillHandleDto;
import com.dm.dto.MonthHourTableDto;
import com.dm.entity.DeviceReserveInfoEntity;
import com.dm.entity.UserDeptEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	首页Dao
 */
public interface TopDao {
	//获取待处理文档
	List<DocWillHandleDto> getWillHandleDocList(@Param("userId")String userId);
	//获取工时
	MonthHourTableDto getMonthHour(@Param("userId")String userId);
	//获取待审核文档
	List<DocWillHandleDto> getWillApproveDocList(@Param("depts")List<UserDeptEntity> depts);
	//获取我的已预约设备
	List<DeviceReserveInfoDto> getDeviceReserveList(@Param("userId")String userId);
}
