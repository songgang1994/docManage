package com.dm.srv.impl.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.UserDao;
import com.dm.dto.LoginDto;
import com.dm.entity.MenuEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.user.UserSrv;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：登录Service
 */
@Service
public class UserSrvImp extends BaseSrvImp implements UserSrv{
	@Autowired
    private UserDao userDao;

	@Override
	public List<MenuEntity> getMenu(List<Integer> roleIds) {
		//获取菜单目录
		//角色不为空的场合
		if(roleIds!=null&&roleIds.size()!=0) {
			List<MenuEntity> menuList = userDao.getMenuItemList(roleIds);
			return menuList;
		}else {
			return null;
		}
	}

	@Override
	public LoginDto getUser(LoginDto logonDto) {
		List<LoginDto> list = userDao.getUser(logonDto.getUserCode());
		//获取登录用户信息
		LoginDto result = new LoginDto();
		if(list!=null&&list.size()!=0) {
			 result = list.get(0);
		} else {
			return null;
		}
		//获取所有按钮action
		List<String> allActions =  userDao.getAllActions();
		//当获取结果不为空且角色不为空的场合
		if(result!=null) {
			if(result.getRoleIds()!=null&&!result.getRoleIds().isEmpty()) {
				//获取用户权限action
				List<String> sysActions =  userDao.getSysActions(result.getRoleIds());
				result.setSysActions(sysActions);
				result.setAllActions(allActions);
			}

		}
		return result;
	}

}
