package com.dm.srv.impl.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.MenuManageDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.entity.SysMenuEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.system.MenuManageSrv;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	系统菜单管理Service
 */
@Service
public class MenuManageSrvImpl extends BaseSrvImp implements MenuManageSrv{
	//系统菜单管理Dao
	@Autowired
	private MenuManageDao manuManageDao;

	//获取系统菜单树目录
	@Override
	public List<SysMenuEntity> getmenuManageTree() {
		// 获取系统菜单树目录数据
		List<SysMenuEntity> sysMenu = manuManageDao.getMenuList();
		return sysMenu;
	}

	//系统菜单修改保存
	@Override
	public BaseDto menuUpd(String[] menuIds,String[] texts,String[] sortNos,String updater) {
		BaseDto baseDto = new BaseDto();
		List<SysMenuEntity> menuList = new ArrayList<SysMenuEntity>();
		boolean result = false;
		if(menuIds!=null&&menuIds.length>0&&texts!=null&&texts.length>0&&sortNos!=null&&sortNos.length>0) {
			for(int i=0;i<menuIds.length;i++) {
				SysMenuEntity menu =new SysMenuEntity();
				menu.setMenuId(menuIds[i]);
				menu.setText(texts[i]);
				menu.setSortNo(Integer.parseInt(sortNos[i]));
				menu.setUpdater(updater);
				menuList.add(menu);
			}
			 result = manuManageDao.menuUpd(menuList);
		}
		if(result) {
			baseDto.setBizCode(BizCode.BIZ_CODE_SUCCESS);
		} else {
			baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
		}
		return baseDto;
	}

}
