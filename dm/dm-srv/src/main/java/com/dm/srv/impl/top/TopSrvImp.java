package com.dm.srv.impl.top;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.TopDao;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DocWillHandleDto;
import com.dm.dto.MonthHourListDto;
import com.dm.dto.MonthHourTableDto;
import com.dm.dto.PersonMonthHourInfo;
import com.dm.entity.DeviceReserveInfoEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.top.TopSrv;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：首页Service
 */
@Service
public class TopSrvImp  extends BaseSrvImp implements TopSrv{
	@Autowired
	private TopDao topDao;

	//获取工时
	@Override
	public MonthHourListDto getMonthHourList(String userId) {
		//初始化首页Dto
		MonthHourListDto monethHourTable = new MonthHourListDto();
		//获取当月工时数据
		MonthHourTableDto monethHourResult = topDao.getMonthHour(userId);
		//初始化课题编号
		List<String> projectNos = new ArrayList<String>();
		//初始化工时合计
		BigDecimal timesTotal = new BigDecimal(0);
		//当查询结果不为空的场合
		if(monethHourResult!=null) {
			//当当月工时不为空的场合
			if(monethHourResult.getMonthHour()!=null&&!monethHourResult.getMonthHour().isEmpty()) {
				//循环遍历当月数据
				for(int i=0;i<monethHourResult.getMonthHour().size();i++) {
					PersonMonthHourInfo personMonthHour =monethHourResult.getMonthHour().get(i);
					projectNos.add(personMonthHour.getProjectNo());
					//当工时不为null的场合
					if(personMonthHour.getWorkingtimes()!=null) {
						timesTotal=timesTotal.add(personMonthHour.getWorkingtimes());
					}
				}
				monethHourResult.setTimesTotal(timesTotal);
				monethHourTable.setProjectNos(projectNos);
				monethHourTable.setMonthHour(monethHourResult);
			}
		}
		return monethHourTable;
	}

	//获取待处理文档
	@Override
	public List<DocWillHandleDto> getWillHandleDocList(String userId) {
		//获取待处理文档
		List<DocWillHandleDto> docWillHandle = topDao.getWillHandleDocList(userId);
		return docWillHandle;
	}

	//获取待审核文档
	@Override
	public List<DocWillHandleDto> getWillApproveDocList(List<UserDeptEntity> depts) {
		//获取待审核文档
		List<UserDeptEntity> deptList = new ArrayList<UserDeptEntity>();
		for(int i=0;i<depts.size();i++) {
			if(depts.get(i).getLeaderFlg().trim().equals("1")) {
				deptList.add(depts.get(i));
			}
		}
		List<DocWillHandleDto> docWillHandle = new ArrayList<DocWillHandleDto>();
		if(deptList!=null&&!deptList.isEmpty()) {
			docWillHandle= topDao.getWillApproveDocList(deptList);
		}
		return docWillHandle;
	}

	//获取已预约设备信息
	@Override
	public List<DeviceReserveInfoDto> getDeviceReserveList(String userId) {
		List<DeviceReserveInfoDto> deviceReserveList = topDao.getDeviceReserveList(userId);
		return deviceReserveList;
	}
}
