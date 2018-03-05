package com.dm.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dm.dto.DeviceInfoDto;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DeviceUseInfoDto;
import com.dm.entity.DeviceInfoEntity;
import com.dm.entity.DeviceUseInfoEntity;
import com.dm.entity.ParmMstEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：实验设备
 */


public interface DeviceDao {

	// 计算搜索总条数
	int count(
			@Param("deviceNo") String deviceNo,
			@Param("deviceName") String deviceName,
			@Param("deviceLocation") String deviceLocation);

	// 获取当前页设备列表
	List<DeviceInfoDto> listByPage(
			@Param("deviceNo") String deviceNo,
			@Param("deviceName") String deviceName,
			@Param("deviceLocation") String deviceLocation,
			@Param("start")int start,@Param("length")int length);

	// 检查设备是否被预约
	int countReserve(String deviceNo);

	// 检查设备是否被使用登记过
	int countUse(String deviceNo);

	// 设备删除
	int delete(
			@Param("deviceNo") String deviceNo,
			@Param("userId") String userId);
//================================实验设备使用一览==============================//
	/**
	 * 查询设备使用数据
	 * @param deviceUseInfoDto
	 * @param start
	 * @param length
	 * @return list
	 */
	List<DeviceInfoDto> getUseList(@Param("device")DeviceInfoDto deviceInfoDto,@Param("start")int start,@Param("length") int length);

	/**
	 * 查询设备使用数据总条数
	 * @param deviceInfoDto
	 * @return int
	 */
	int getUseTatal(@Param("device")DeviceInfoDto deviceInfoDto);
//================================实验设备使用一览结束===========================//
//================================实验设备新增修改==============================//
	/**
	 * 查询设备编号是否已存在
	 * @param deviceNo  设备编号
	 * @return DeviceInfoEntity
	 */
	DeviceInfoEntity searchNumber(@Param("deviceNo") String eqnumber);

	/**
	 * 添加设备信息
	 * @param deviceInfoEntity
	 * @param userId  登录人id
	 * @return
	 */
	Integer addDevice(@Param("device") DeviceInfoEntity deviceInfoEntity,@Param("userId") String userId);

	/**
	 * 通过设备编号查询设备信息
	 * @param deviceNo  设备编号
	 * @return DeviceInfoEntity
	 */
	DeviceInfoEntity editorDevice(@Param("deviceNo") String deviceNo);

	/**
	 * 通过设备编号修改设备信息
	 * @param deviceInfoEntity
	 * @param userId  登录人id
	 * @return
	 */
	int updateDevice(@Param("device") DeviceInfoEntity deviceInfoEntity,@Param("userId") String userId);
//================================实验设备新增修改结束==============================//

	/**
	 * 获取最后插入的预约信息的ReserveNo
	 * @return 最后插入的预约信息的ReserveNo
	 */
	BigDecimal getLastReserveNo();

	/**
	 * 设备预约期间查重
	 * @param deviceReserveInfoDto
	 * @return 指定期间内 指定设备的预约数
	 */
	Integer periodCheck(DeviceReserveInfoDto deviceReserveInfoDto);

	/**
	 * 新增设备预约
	 * @param deviceReserveInfoDto
	 * @return 受影响行数
	 */
	Integer addReserve(DeviceReserveInfoDto deviceReserveInfoDto);

	/**
	 *  修改设备预约
	 * @param deviceReserveInfoDto
	 * @return 受影响的行数
	 */
	Integer updateReserve(DeviceReserveInfoDto deviceReserveInfoDto);

	/**
	 * 删除设备预约
	 * @param deviceReserveInfoDto
	 * @return 受影响的行数
	 */
	Integer deleteReserve(DeviceReserveInfoDto deviceReserveInfoDto);

	/**
	 * 获取单条设备预约信息
	 * @param deviceNo 设备号
	 * @param reserveNo	预约号
	 * @return 指定设备，指定预约的 预约信息
	 */
	DeviceReserveInfoDto getReserve(@Param("deviceNo")String deviceNo, @Param("reserveNo")String reserveNo);

	/**
	 * 获得 设备预约记录
	 * @param deviceNos 设备号（数组）
	 * @param startDt	开始日期
	 * @param endDt		结束日期
	 * @return	指定期间内 指定设备的预约记录
	 */
	List<DeviceReserveInfoDto> reserveInfo(@Param("deviceNos")String[] deviceNos, @Param("startDt")Date startDt, @Param("endDt")Date endDt );

//================================使用工时录入==============================//
	//查询所有设备
	List<DeviceInfoEntity> getDevice();

	//查询设备信息中的设备状态
	List<ParmMstEntity> getstate();

	//查询设备信息中的位置
	List<ParmMstEntity> getplace();

	//使用设备工时录入
	int addHour(@Param("hour") DeviceUseInfoEntity deviceUserInfoEntity,@Param("userId") String userId,@Param("useNo") BigDecimal useNo);

	//查询数据库最后一条数据
	DeviceUseInfoEntity getFinal();

    //使用设备信息删除
	int deleteHour(@Param("useNo") BigDecimal useNo);

	//判断时间是否冲突
	DeviceUseInfoEntity gettime(@Param("hour") DeviceUseInfoEntity deviceUserInfoEntity);

	//修改设备信息
	int updateHour(@Param("hour") DeviceUseInfoEntity deviceUserInfoEntity,@Param("userId") String userId);

	//通过使用设备查询设备信息
	DeviceUseInfoDto getInfo(@Param("useNo") BigDecimal useNo);

//================================使用工时录入结束==============================//
}
