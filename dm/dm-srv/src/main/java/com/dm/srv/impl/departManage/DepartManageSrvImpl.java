package com.dm.srv.impl.departManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.DepartManageDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.UserDeptDto;
import com.dm.dto.DepartManageInfoDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.srv.departManage.DepartManageSrv;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;
/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	组织结构维护实现server
 */
@Service
public class DepartManageSrvImpl extends BaseSrvImp implements DepartManageSrv{
	@Autowired
	private DepartManageDao departManageDao;

	//组织结构树目录获取
	@Override
	public List<DepartmentInfoEntity> getdepartManageTree() {
		//获取组织结构目录树数据
		List<DepartmentInfoEntity> departManage = departManageDao.getdepartManageTree();
		return departManage;
	}
	//组织新增
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DepartmentInfoEntity departAdd(DepartManageInfoDto departInfo) {
		//根据Id获取部门数量
		int departCount = departManageDao.departCount(departInfo.getDeptId());
		//当部门Id已经存在的场合
		if(departCount > 0) {
			departInfo.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return departInfo;
		}
		//获取上级部门
		String parentDepartId =departInfo.getParentDeptId();
		//当上级部门不存在的场合，设置为null
		if(parentDepartId.length()<=0||parentDepartId=="") {
			departInfo.setParentDeptId(null);
		}
		//获取部门负责人的Id集合
		String[] userIds =  departInfo.getUserId().split(",");
		List<UserDeptDto> userdeptDtos = new ArrayList<UserDeptDto>();
		for(int i=0;i<userIds.length;i++) {
			UserDeptDto userdeptDto= new UserDeptDto();
			userdeptDto.setUserId(userIds[i]);
			userdeptDto.setDeptId(departInfo.getDeptId());
			userdeptDto.setCreator(departInfo.getCreator());
			userdeptDtos.add(userdeptDto);
		}
		//新增部门
		departManageDao.departAdd(departInfo);
		//新增部门负责人
		departManageDao.userDeptAdd(userdeptDtos);
		departInfo.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return departInfo;
	}
	//根据deptId获取部门信息
	@Override
	public DepartManageInfoDto departDetail(String departId) {
		DepartManageInfoDto departInfo = new DepartManageInfoDto();
		//根据Id获取部门数量
		int departCount = departManageDao.departCount(departId);
		//当部门不存在的场合
		if(departCount <= 0) {
			departInfo.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			departInfo.setDeptId(departId);
			return departInfo;
		}
		//获取部门详细信息
		departInfo = departManageDao.getDepartDetail(departId);
		//拼接部门负责人Id和部门负责人名称
		String userId="";
		String userName="";
		for(int i=0;i<departInfo.getUserdeptDtos().size();i++) {
			//当i为0的场合
			if(i==0) {
				userId = departInfo.getUserdeptDtos().get(i).getUserId();
				userName=departInfo.getUserdeptDtos().get(i).getUserName();
			}else {
				//当i不为0的场合
				userId = userId+","+departInfo.getUserdeptDtos().get(i).getUserId();
				userName=userName+","+departInfo.getUserdeptDtos().get(i).getUserName();
			}
		}
		departInfo.setUserId(userId);
		departInfo.setUserName(userName);
		departInfo.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return departInfo;
	}
	//修改部门信息
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DepartManageInfoDto departUpd(DepartManageInfoDto departInfo) {
		//根据部门Id获取部门数量
		int departCount = departManageDao.departCount(departInfo.getDeptId());
		//当部门不存在的场合
		if(departCount <= 0) {
			departInfo.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return departInfo;
		}
		//删除部门负责人
		departManageDao.userDeptDelete(departInfo.getDeptId());
		//获取部门负责人
		String[] userIds =  departInfo.getUserId().split(",");
		List<UserDeptDto> userdeptDtos = new ArrayList<UserDeptDto>();
		//遍历部门负责人
		for(int i=0;i<userIds.length;i++) {
			UserDeptDto userdeptDto= new UserDeptDto();
			userdeptDto.setUserId(userIds[i]);
			userdeptDto.setDeptId(departInfo.getDeptId());
			userdeptDto.setCreator(departInfo.getUpdater());
			userdeptDtos.add(userdeptDto);
		}
		//部门负责人新增
		departManageDao.userDeptAdd(userdeptDtos);
		//修改部门信息
		departManageDao.departUpd(departInfo);
		departInfo.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return departInfo;
	}
	//删除部门
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public DepartManageInfoDto departDelete(String deptId) {
		DepartManageInfoDto baseDto = new DepartManageInfoDto();
		//获取所有部门信息
		List<DepartmentInfoEntity> departManage = departManageDao.getdepartManageTree();
		//获取下属部门
		List<DepartmentInfoEntity> deptList = departHander(departManage,deptId);
		//获取Id集合
		List<String> departIdList =  new ArrayList<String>();
		for(int i=0;i<deptList.size();i++) {
			 String departId=deptList.get(i).getDeptId();
			 departIdList.add(departId);
		}
		departIdList.add(deptId);
		//获取部门员工数量
		int userCount = departManageDao.userCount(departIdList);
		//当部门存在员工的场合
		if(userCount>0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			return baseDto;
		}
		//删除部门
		departManageDao.departDelete(departIdList);
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		baseDto.setDeptId(deptId);
		return baseDto;
	}

	//根据传递departId，获取下属部门详细信息
		public List<DepartmentInfoEntity> departHander(List<DepartmentInfoEntity> departList,String deptId){
			//根据parentDepartId和下属部门，生成map
			Map<String,List<DepartmentInfoEntity>> departMap=new HashMap<String,List<DepartmentInfoEntity>>();
			for(int i=0;i<departList.size();i++){
				if(departMap.get(departList.get(i).getParentDeptId())==null) {
					//根据parentDepartId新增map
					departMap.put(departList.get(i).getParentDeptId(),new ArrayList<DepartmentInfoEntity>());
				}
				//添加下属部门数据
				departMap.get(departList.get(i).getParentDeptId()).add(departList.get(i));
			}
			List<DepartmentInfoEntity> departsList = new ArrayList<DepartmentInfoEntity>();
			//根据当前departId获取下一级部门信息
			List<DepartmentInfoEntity> depart = departMap.get(deptId);
			//下级部门存在的场合
			if(depart!=null && !depart.isEmpty()) {
				departsList.addAll(depart);
			}else {
				return departsList;
			}
			//循环遍历，下属部门的所有下级部门 注：depart.size()会随着depart变化
			for(int i=0;i<depart.size();i++) {
				List<DepartmentInfoEntity> departNode=new ArrayList<DepartmentInfoEntity>();
				departNode = departMap.get(depart.get(i).getDeptId());
				if(departNode!=null && !departNode.isEmpty()) {
					departsList.addAll(departNode);
				}
			}
			return departsList;
		}
}
