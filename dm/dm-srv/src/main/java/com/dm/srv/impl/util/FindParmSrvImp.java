package com.dm.srv.impl.util;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	共通实现Server
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.FindParmDao;
import com.dm.dto.ParmMstDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.util.FindParmSrv;
@Service
public class FindParmSrvImp extends BaseSrvImp implements FindParmSrv{
	@Autowired
	private FindParmDao findParmDao;
	//查询课题分类
	@Override
	public List<ParmMstEntity> getProjectTypeList() {
		return findParmDao.getProjectTypeList();
	}

	//查询使用类别
	@Override
	public List<ParmMstEntity> getUserType() {
		return findParmDao.getUserType();
	}

	//查询设备信息中设备状态
	public List<ParmMstEntity> getstate() {
		List<ParmMstEntity> list = findParmDao.getstate();
		return list;
	}

    //查询设备信息中位置
	@Override
	public List<ParmMstEntity> getplace() {
		List<ParmMstEntity> listplace = findParmDao.getplace();
		return listplace;
	}
    //查询数据项
	@Override
	public List<ParmMstEntity> listItemTypes() {

		return findParmDao.getItemTypes();
	}
   //查询法人类型
	@Override
	public List<ParmMstDto> getLegal() {
		return findParmDao.getLegal();
	}

	//查询操作内容
	@Override
	public List<ParmMstDto> getOperate() {
		return findParmDao.getOperate();
	}

	//获取文档类型
	@Override
	public List<DocItemDataSourceCodeEntity> getDocType() {
		return findParmDao.getDocType();
	}

}
