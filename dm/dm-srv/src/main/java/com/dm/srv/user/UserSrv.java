package com.dm.srv.user;

import java.util.List;

import com.dm.dto.LoginDto;
import com.dm.entity.MenuEntity;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：登录Server
 */
public interface UserSrv {
	//获取左侧连接菜单
	List<MenuEntity> getMenu(List<Integer> roleIds);

	//获取登录用户信息
	LoginDto getUser(LoginDto logonDto);
}
