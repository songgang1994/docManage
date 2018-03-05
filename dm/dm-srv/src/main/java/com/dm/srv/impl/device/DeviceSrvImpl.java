package com.dm.srv.impl.device;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.DeviceDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DeviceInfoDto;
import com.dm.dto.DeviceReserveInfoDto;
import com.dm.dto.DeviceUseInfoDto;
import com.dm.entity.DeviceInfoEntity;
import com.dm.entity.DeviceUseInfoEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.device.DeviceSrv;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：邵林军
 * 模块名：实验设备
 */
@Service(value="deviceSrv")
public class DeviceSrvImpl extends BaseSrvImp implements DeviceSrv{

	@Autowired
	private DeviceDao deviceDao;

	/**
	 * 分页搜索设备，返回当前页数据
	 *
	 * @param deviceNo 设备编号
	 * @param deviceName 设备名称
	 * @param deviceLocation 设备位置
	 */
	@Override
	public List<DeviceInfoDto> listByPage(String deviceNo,String deviceName,String deviceLocation,int start,int length) {
		//模糊查询，拼接字符串
		deviceNo = Constant.STR_PERCENT + deviceNo + Constant.STR_PERCENT;
		deviceName =  Constant.STR_PERCENT + deviceName + Constant.STR_PERCENT;
		deviceLocation = Constant.STR_PERCENT + deviceLocation + Constant.STR_PERCENT;
		// 查询的数据
		List<DeviceInfoDto> list = deviceDao.listByPage(deviceNo, deviceName, deviceLocation,start,length);
		return list;
	}

	/**
	 * 删除设备
	 *
	 * @param deviceNo 设备编号
	 * @param userId 当前用户
	 */
	@Override
	public BaseDto delete(String deviceNo, String userId) {
		BaseDto deviceInfo = new BaseDto();
		//判断是否有预约记录
		int countre = deviceDao.countReserve(deviceNo);
		//判断是否有使用记录
		int countus = deviceDao.countUse(deviceNo);
		if (countre > 0) {
			deviceInfo.setBizCode(BizCode.BIZ_CODE_RESERVEDEVICE_DELETE_FAILED);
			return deviceInfo;
		}
		if (countus > 0) {
			deviceInfo.setBizCode(BizCode.BIZ_CODE_USEDEVICE_DELETE_FAILED);
			return deviceInfo;
		}
		//删除设备信息
		int delete =deviceDao.delete(deviceNo, userId);
		//判断是否删除成功
		if(delete>0) {
			//删除成功
			deviceInfo.setBizCode(BizCode.BIZ_CODE_DEVICE_DELETE_SUCCESS);
		}else {
			//参数错误，删除失败
			deviceInfo.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return deviceInfo;
	}

//=================================设备新增修改===========================//
	//查询设备编号是否存在
	@Override
	public DeviceInfoEntity searchNumber(String eqnumber) {
		//调用dao查询设备编号的方法
		DeviceInfoEntity  device = deviceDao.searchNumber(eqnumber);
		return device;
	}

	//添加设备信息
	@Override
	public BaseDto addDevice(DeviceInfoEntity deviceInfoEntity,String userId) {
		BaseDto deviceInfo = new BaseDto();
		//新增设备信息
        int devic = deviceDao.addDevice(deviceInfoEntity,userId);
        if(devic>0) {
        	//新增成功
        	deviceInfo.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
        }else {
        	//新增失败
        	deviceInfo.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
        }
		return deviceInfo;
	}

	//通过设备编号，查询设备信息
	@Override
	public DeviceInfoEntity editorDevice(String deviceNo) {
		//通过设备编号查询设备信息
        DeviceInfoEntity device = deviceDao.editorDevice(deviceNo);
		return device;
	}

   //通过设备编号修改设备信息
	@Override
	public BaseDto updateDevice(DeviceInfoEntity deviceInfoEntity,String userId) {
		BaseDto baseDto = new BaseDto();
		//调用dao层修改设备信息
		int deviceInfo = deviceDao.updateDevice(deviceInfoEntity,userId);
		if (deviceInfo>0) {
			//修改成功
			baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
		}else {
			//参数问题修改失败
			baseDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return baseDto;
	}

//=================================设备新增修改结束===========================//
	// 新增、修改、删除 设备预约
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDto reserve(DeviceReserveInfoDto deviceReserveInfoDto) {
		//定义返回对象
		BaseDto baseDto = new BaseDto();
		//定义期间查重变量
		Integer periodCheck;
		//定义数据库操作结果
		Integer result;

		//判断操作模式
		switch(deviceReserveInfoDto.getPattern()) {
			case Constant.RESERVE_PATTERN_ADD://新增预约
				//期间查重
				periodCheck = deviceDao.periodCheck(deviceReserveInfoDto);
				if(periodCheck != null && periodCheck >0){
					//期间重复
					baseDto.setBizCode(BizCode.BIZ_CODE_DURATION_ERR);
					break;
				}
				//获取最后一条reserveNo
				BigDecimal lastReserveNo = deviceDao.getLastReserveNo();
				if(lastReserveNo == null) {
					lastReserveNo = new BigDecimal("1");
				}
				deviceReserveInfoDto.setReserveNo(lastReserveNo.add(new BigDecimal("1")));
				//执行新增
				result = deviceDao.addReserve(deviceReserveInfoDto);
				if(result == null  || result <= 0 ) {
					baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_ADD_FAILED);//设备预约信息插入失败
				}else {
					baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_ADD_SUCCESS);//设备预约信息插入成功
				}
				break;

			case Constant.RESERVE_PATTERN_UPDATE://修改预约
				//期间查重
				periodCheck = deviceDao.periodCheck(deviceReserveInfoDto);
				if(periodCheck != null && periodCheck >1){
					//期间重复
					baseDto.setBizCode(BizCode.BIZ_CODE_DURATION_ERR);
					break;
				}
				//执行更新
				result = deviceDao.updateReserve(deviceReserveInfoDto);
				if(result == null  || result <= 0 ) {
					baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_UPDATE_FAILED);//设备预约信息修改失败
				}else {
					baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_UPDATE_SUCCESS);//设备预约信息修改成功
				}
				break;

			case Constant.RESERVE_PATTERN_DELETE://删除预约
				result = deviceDao.deleteReserve(deviceReserveInfoDto);
				if(result == null  || result <= 0 ) {
					baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_DELETE_FAILED);//设备预约信息删除失败
				}else {
					baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_DELETE_SUCCESS);//设备预约信息删除成功
				}
				break;

			default://其他
				baseDto.setBizCode(BizCode.BIZ_CODE_RESERVE_PATTERN_ERR);//设备预约操作模式异常
				break;
		}

		return baseDto;
	}

	// 获取单条设备预约记录
	@Override
	public DeviceReserveInfoDto getReserve(String deviceNo, String reserveNo) {
		return deviceDao.getReserve(deviceNo, reserveNo);
	}

	// 获取  指定期间，指定设备的预约状况
	@Override
	public List<DeviceReserveInfoDto> reserveInfo(String[] deviceNos, Date startDt, Date endDt) {
		return  deviceDao.reserveInfo(deviceNos,startDt,endDt);
	}
//====================================设备使用一览==============================//
	//设备使用数据查询
	public List<DeviceInfoDto> getUseList(DeviceInfoDto deviceInfoDto,int start,int length){
		//设备使用数据查询
		List<DeviceInfoDto> list = deviceDao.getUseList(deviceInfoDto, start, length);
		//日期格式转换
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//定义日期变量
		String startDt = null;
		String endDt = null;
		String useDt =null;
		//循环设置使用期间
		for(int i=0;i<list.size();i++) {
			//获取起始日期
			startDt =df.format(list.get(i).getStartDt());
			//获取结束日期
			endDt = df.format(list.get(i).getEndDt());
			//拼接使用日期
			useDt = startDt+"~"+endDt;
			//设置使用期间
			list.get(i).setUseTime(useDt);
		}
		return list;
	}
	//查询设备使用总条数
	public int getUseTatal(DeviceInfoDto deviceInfoDto) {
		return deviceDao.getUseTatal(deviceInfoDto);
	}
//====================================设备使用一览结束===========================//
//====================================使用工时录入==============================//
	//查询所有设备
	public List<DeviceInfoEntity> getDevice(){
		List<DeviceInfoEntity> list = deviceDao.getDevice();
		return list;
	}

	//使用设备工时录入
	@Override
	public BaseDto addHour(DeviceUseInfoEntity deviceUserInfoEntity, String userId) {
		BaseDto deviceHour = new BaseDto();
		// 查询数据库最后一条数据
		DeviceUseInfoEntity device = getFinal();
		//获取编号
		BigDecimal useN = device.getUseNo();
		// 使用编号自增长
		BigDecimal useNo = useN.add(new BigDecimal("1"));
		//录入工时信息
		int hour = deviceDao.addHour(deviceUserInfoEntity, userId,useNo);
		if(hour>0) {
			//录入成功
			deviceHour.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
		}else {
			//录入失败
			deviceHour.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return deviceHour;
	}

	//查询数据库最后一条数据
	@Override
	public DeviceUseInfoEntity getFinal() {
		return deviceDao.getFinal();
	}

	//设备工时信息删除
	@Override
	public BaseDto deleteHour(BigDecimal useNo) {
		BaseDto deleteHour = new BaseDto();
		//删除工时信息
		int delete = deviceDao.deleteHour(useNo);
		if(delete>0) {
			//信息删除成功
			deleteHour.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
		}else {
			//信息删除失败
			deleteHour.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return deleteHour;
	}

	//判断时间是否冲突
	@Override
	public DeviceUseInfoEntity gettime(DeviceUseInfoEntity deviceUserInfoEntity) {
		//查询时间是否满足要求
		DeviceUseInfoEntity time = deviceDao.gettime(deviceUserInfoEntity);
		return time;
	}

	//修改设备工时信息
	@Override
	public BaseDto updateHour(DeviceUseInfoEntity deviceUserInfoEntity,String userId) {
		BaseDto updateHour = new BaseDto();
		//修改设备信息
		int update = deviceDao.updateHour(deviceUserInfoEntity,userId);
		if (update>0) {
			//信息修改成功
			updateHour.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
		} else {
			//参数错误，信息修改失败
            updateHour.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return updateHour;
	}

	//通过使用编号查询设备信息
	@Override
	public DeviceUseInfoDto getInfo(BigDecimal useNo) {
		DeviceUseInfoDto useInfo = deviceDao.getInfo(useNo);
		//日期分隔符处理
		String startDt = useInfo.getStartDt();
		String s11 = startDt.replace("-","/");
		//截取字符串
		String s1 = s11.substring(0,s11.length()-5);
		useInfo.setStartDt(s1);
		String endDt = useInfo.getEndDt();
		String s22 = endDt.replace("-","/");
		//截取字符串
		String s2 = s22.substring(0,s22.length()-5);
		useInfo.setEndDt(s2);
		return useInfo;
	}
//====================================使用工时录入结束==============================//

	@Override
	public int getlistCount(String deviceNo, String deviceName, String deviceLocation) {
		//模糊查询，拼接字符串
		deviceNo = Constant.STR_PERCENT + deviceNo + Constant.STR_PERCENT;
		deviceName =  Constant.STR_PERCENT + deviceName + Constant.STR_PERCENT;
		deviceLocation = Constant.STR_PERCENT + deviceLocation + Constant.STR_PERCENT;
		// 查询的数据
		int result = deviceDao.count(deviceNo, deviceName, deviceLocation);
		return result;
	}


}
