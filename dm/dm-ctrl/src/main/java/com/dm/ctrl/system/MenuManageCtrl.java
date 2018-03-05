package com.dm.ctrl.system;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.entity.SysMenuEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.system.MenuManageSrv;
import com.dm.tool.Constant;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 * 系统菜单管理
 */
@Controller
@RequestMapping("/system")
public class MenuManageCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(MenuManageCtrl.class);
	// 系统菜单管理Service
	@Autowired
	private MenuManageSrv menuManageSrv;
	// 系统菜单管理画面显示
	@RequestMapping(value = "/menumanage")
	public ModelAndView init() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("system/MenuManage");
		return mv;
	}

		// 系统菜单树目录显示
		@RequestMapping(value = "/sysmenuTree")
		@ResponseBody
		public ResultDto<SysMenuEntity> initTree() {
			ResultDto<SysMenuEntity> resultDto = new ResultDto<SysMenuEntity>();
			try {
				// 获取系统菜单树数据
				List<SysMenuEntity> menuTree = menuManageSrv.getmenuManageTree();
				resultDto.setListData(menuTree);
				resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
				return resultDto;
			} catch (Exception e) {
				// 出力异常信息到LOG中
				log.error(e.getMessage());
				resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
				return resultDto;
			}
		}

		//系统菜单修改
		@RequestMapping(value = "/menusubmit", method = {RequestMethod.POST})
		@ResponseBody
		public ResultDto<BaseDto> treeUpd(String[] menuIds,String[] texts,String[] sortNos ){
			ResultDto<BaseDto> resultDto = new ResultDto<BaseDto>();
			try {
				// 获取登录用户信息
				UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
				String updater = user.getUserId();
				BaseDto baseDto= menuManageSrv.menuUpd(menuIds, texts, sortNos,updater);
				resultDto.setData(baseDto);
				return resultDto;
			}catch (Exception e) {
				// 出力异常信息到LOG中
				log.error(e.getMessage());
				resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
				return resultDto;
			}
		}
}
