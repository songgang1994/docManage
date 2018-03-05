package com.dm.srv.top;

import java.util.List;

import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DocWillHandleDto;
import com.dm.dto.MonthHourListDto;
import com.dm.entity.DeviceReserveInfoEntity;
import com.dm.entity.UserDeptEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	首页Srv
 */
public interface TopSrv {
	//获取当月工时
	MonthHourListDto 	getMonthHourList(String userId);
	//获取我的待处理文档
	List<DocWillHandleDto> getWillHandleDocList(String userId);
	//获取待审核文件
	List<DocWillHandleDto> getWillApproveDocList(List<UserDeptEntity> depts);
	//获取已预约设备信息
	List<DeviceReserveInfoDto> getDeviceReserveList(String userId);
}
