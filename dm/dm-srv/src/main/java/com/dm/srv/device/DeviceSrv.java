package com.dm.srv.device;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.dm.dto.BaseDto;
import com.dm.dto.DeviceInfoDto;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DeviceUseInfoDto;
import com.dm.entity.DeviceInfoEntity;
import com.dm.entity.DeviceReserveInfoEntity;
import com.dm.entity.DeviceUseInfoEntity;
import com.dm.entity.ParmMstEntity;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：实验设备
 */
public interface DeviceSrv {

	List<DeviceInfoDto> listByPage(
			String deviceNo,
			String deviceName,
			String deviceLocation,
			int start,int length);

	int getlistCount(String deviceNo,
							String deviceName,
							String deviceLocation);

	BaseDto delete(String deviceNo, String userId);

//=================================设备新增修改==========================//
    //查找是否存在设备编号
	DeviceInfoEntity searchNumber(String eqnumber);

	//添加设备信息
	BaseDto addDevice(DeviceInfoEntity deviceInfoEntity,String userId);

	//通过设备编号查信息
	DeviceInfoEntity editorDevice(String deviceNo);

	//通过设备编号修改设备信息
	BaseDto updateDevice(DeviceInfoEntity deviceInfoEntity,String userId);

//=================================设备新增修改结束==========================//
	/**
	 * 新增、修改、删除 设备预约
	 * @param deviceReserveInfoDto 设备预约信息
	 * @return 新增 修改 删除的返回code
	 */
	BaseDto reserve(DeviceReserveInfoDto deviceReserveInfoDto);

	/**
	 * 获取单条设备预约记录
	 * @param deviceNo 设备编号
	 * @param reserveNo 预约号
	 * @return 单条设备预约记录
	 */
	DeviceReserveInfoDto getReserve(String deviceNo, String reserveNo);

	/**
	 * 获取  指定期间，指定设备的预约状况
	 * @param deviceNos 设备编号（数组）
	 * @param startDt	开始日期
	 * @param endDt		结束日期
	 * @return 指定期间，指定设备的预约状况
	 */
	List<DeviceReserveInfoDto> reserveInfo(String[] deviceNos, Date startDt, Date endDt);
//=============================设备使用一览==============================//
	/**
	 * 设备使用数据查询
	 * @param deviceUseInfoDto
	 * @param start
	 * @param length
	 * @return list
	 */
	List<DeviceInfoDto> getUseList(DeviceInfoDto deviceInfoDto,int start,int length);

	/**
	 * 查询设备使用总条数
	 * @param deviceInfoDto
	 * @return
	 */
	int getUseTatal(DeviceInfoDto deviceInfoDto);
//=============================设备使用一览结束===========================//
//=============================使用工时录入==============================//
   //查询所有设备
	List<DeviceInfoEntity> getDevice();

	//使用设备工时录入
	BaseDto addHour(DeviceUseInfoEntity deviceUserInfoEntity,String userId);

	//查询数据库最后一条数据
	DeviceUseInfoEntity getFinal();

	//设备工时信息删除
	BaseDto deleteHour(BigDecimal useNo);

	//判断时间是否冲突
	DeviceUseInfoEntity gettime(DeviceUseInfoEntity deviceUserInfoEntity);

	//修改设备信息
    BaseDto updateHour(DeviceUseInfoEntity deviceUserInfoEntity,String userId);

    //通过使用编号查询设备信息
    DeviceUseInfoDto getInfo(BigDecimal useNo);
 //=============================使用工时录入结束==============================//
}
