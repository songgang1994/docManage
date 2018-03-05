package com.dm.srv.impl.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.SourceMainDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ParmMstDto;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.main.SourceMainSrv;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：字典数据维护
 */

@Service
public class SourceMainSrvImpl extends BaseSrvImp implements SourceMainSrv{

	@Autowired
	private SourceMainDao sourceMainDao;

	//查询类型
	@Override
	public BaseDto getType(String type1Name) {
		BaseDto ParmMstDto = new BaseDto();
		List<ParmMstDto> list =sourceMainDao.getType(type1Name);
		if(list==null) {
			ParmMstDto.setBizCode(BizCode.BIZ_CODE_RECORD_0);
		}else {
			ParmMstDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		}
		return ParmMstDto;
	}

	//索引，查询类型名
	@Override
	public List<ParmMstDto> getDisName(String type1) {
		List<ParmMstDto> list = sourceMainDao.getDisName(type1);
		return list;
	}

	//新追加行数据插入
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto addNew(String type1,String nowNum,String addType,String userId,String type) {
		BaseDto ParmMstDto = new BaseDto();
        //下拉框查出的类型数组
		String[] type1Value=   type1.split(",");
		//当前行的数组
		String[] nowNumValue = nowNum.split(",");
		ParmMstDto pmd = new ParmMstDto();
		List<ParmMstDto> addlist =  new ArrayList<>();
		//设置type1
		pmd.setType1(type);
		for(int i = 0;i<type1Value.length;i++) {
			pmd.setDispName(type1Value[i]);
			pmd.setMaxNum(nowNumValue[i]);
			//执行dao层修改方法
			int updateSource = sourceMainDao.updateSource(pmd, userId);
			if (updateSource>0) {
				//修改成功
				ParmMstDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
			}else {
				//参数错误，修改失败
				ParmMstDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}
		//判断新增还是修改
		if (addType!="" && addType!=null) {
			//新增行的类型数组
			String[] addTypeValue=  addType.split(",");
			//遍历数组，将值一次写入对象
			for(int i = 0;i<addTypeValue.length;i++) {
				//实例化对象，用来存数据
				ParmMstDto pMstDto = new ParmMstDto();
				//写入数据
				pMstDto.setType1(type);
				pMstDto.setSmallType(addTypeValue[i]);
		        pMstDto.setMaxNum(nowNumValue[type1Value.length+i]);
		        //将对象写入集合
				addlist.add(pMstDto);
			}
			if (addlist.size()>0) {
				//执行dao层添加方法
				int addSource = sourceMainDao.addNew(addlist,userId);
				if (addSource>0) {
					ParmMstDto.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
				}else {
					ParmMstDto.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
				}
			}

		}

		return ParmMstDto;
	}
}
