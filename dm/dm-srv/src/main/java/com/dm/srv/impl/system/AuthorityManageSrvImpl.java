package com.dm.srv.impl.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.dm.dao.AuthorityManageDao;
import com.dm.dto.AuthoritymanageTreeDto;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.RoleActionDto;
import com.dm.dto.SysMenuDto;
import com.dm.entity.SysRoleEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.system.AuthorityManageSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	权限维护Service
 */
@Service
public class AuthorityManageSrvImpl extends BaseSrvImp implements AuthorityManageSrv{

	//权限维护Dao
	@Autowired
	private AuthorityManageDao authoritymanageDao;

	//获取权限维护目录树数据
	@Override
	public AuthoritymanageTreeDto getTree(int roleId) {
		AuthoritymanageTreeDto authoritymanageTree = new AuthoritymanageTreeDto();
		//获取菜单数据
		List<SysMenuDto> menuList = authoritymanageDao.getMenuList(roleId);
		for(int i=0;i<menuList.size();i++) {
				if(menuList.get(i).getRoleId()!=null){
					menuList.get(i).setSelected(true);
				}
		}
		//获取画面标签数据
		List<RoleActionDto> roleActionList = authoritymanageDao.getRoleActionList(roleId);
		for(int i=0;i<roleActionList.size();i++) {
			if(roleActionList.get(i).getRoleId()!=null) {
				roleActionList.get(i).setSelected(true);
			}
		}
		//设置权限维护目录数据
		authoritymanageTree.setRoleActionList(roleActionList);
		authoritymanageTree.setSysMenuList(menuList);
		authoritymanageTree.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return authoritymanageTree;
	}

	//权限项保存
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDto authorityUpd(String[] actionSelected, int roleId,String userId) {
		BaseDto baseDto = new BaseDto();
		//删除角色权限
		authoritymanageDao.actionRelationDelete(roleId);
		//新增角色权限
		authoritymanageDao.actionrelationAdd(actionSelected, roleId, userId);
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}

	//获取角色一览数据
	@Override
	public List<SysRoleEntity> getRoleList(SysRoleEntity roleInfo) {
		return authoritymanageDao.getRoleList(roleInfo.getRoleName(), roleInfo.getDescription());
	}

	//角色新增
	@Override
	public BaseDto roleAdd(SysRoleEntity roleInfo) {
		BaseDto baseDto = new BaseDto();
		authoritymanageDao.roleAdd(roleInfo);
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}

	//角色修改
	@Override
	public BaseDto roleUpd(SysRoleEntity roleInfo) {
		BaseDto baseDto = new BaseDto();
		authoritymanageDao.roleUpd(roleInfo);
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}
	//角色删除
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public BaseDto roleDelete(@RequestParam("roleIds")String[] roleIds) {
		BaseDto baseDto = new BaseDto();
		int resultCount = authoritymanageDao.userCountByRoleId(roleIds);
		//当存在员工的场合
		if(resultCount>0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_SRCCANT_DELETE);
			return baseDto;
		}
		authoritymanageDao.roleActionDelete(roleIds);
		authoritymanageDao.roleDelete(roleIds);
		baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		return baseDto;
	}
}
