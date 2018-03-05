package com.dm.ctrl.docmain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.DocDefaultSettingFormDto;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.dto.SearchItemsDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocSearchItemSettingEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.docitem.DocFormDefaultSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档数据项管理
 */
@Controller
@RequestMapping("/docmain")
public class DocFormDefaultCtrl extends BaseCtrl {

	@Autowired
	private DocFormDefaultSrv dFDSrv;
	/**
	 * 跳转默认设置画面
	 *
	 * @return
	 */
	@RequestMapping("/searchdefaultitemedit")
	private ModelAndView EnterFormDefineInit(@RequestParam(value="locationType",required=false,defaultValue="0")String locationType) {
		ModelAndView mv = new ModelAndView();

		try {
			SearchItemsDto searchItems = null;
			// 查询用户是否自定义
			if(locationType.equals("0")) {// 系统默认
				searchItems = dFDSrv.getSearchItems(Constant.USER_ID);
			}else {
				UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
				List<DocSearchItemSettingEntity> list = dFDSrv.isCustomDefine(user.getUserId());
				if(list != null && list.size()>0) {
					searchItems = dFDSrv.getSearchItems(user.getUserId());
				}else {
					searchItems = dFDSrv.getSearchItems(Constant.USER_ID);
				}
			}

			mv.addObject("items", searchItems.getItems());
			mv.addObject("leftItems", searchItems.getLeftList());
			mv.addObject("rightItems", searchItems.getRightList());
			mv.addObject("params", searchItems.getParams());
			mv.addObject("locationType", locationType);
			mv.setViewName("docmain/DocFormDefault");
			return mv;
		} catch (Exception e) {
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	 @RequestMapping("/getdocsource")
	 @ResponseBody
	private ResultDto<DocItemDataSourceCodeEntity> getDocSource(String docCode,String docNo){
		ResultDto<DocItemDataSourceCodeEntity> resultDto = new ResultDto<>();
		DocItemDataSourceCodeEntity baseDto = new DocItemDataSourceCodeEntity();
		try {
			List<DocItemDataSourceCodeEntity> list = dFDSrv.getDocSource(docCode, docNo);
			resultDto.setListData(list);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	 @RequestMapping("/getdocstatus")
	 @ResponseBody
	private ResultDto<ParmMstEntity> getDocStatus(){
		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		ParmMstEntity baseDto = new ParmMstEntity();
		try {
			List<ParmMstEntity> list = dFDSrv.getDocStatus(Constant.DOC_STATUS);
			resultDto.setListData(list);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	/**
	 * 自定义表单默认设置提交
	 *
	 * @param docForm
	 * @return resultDto
	 */
	 @RequestMapping("/defaultsubmit")
	 @ResponseBody
	private ResultDto<BaseDto> defaultFormSubmit(@RequestBody DocDefaultSettingFormDto doc) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

		try {
			// 获取系统登录人信息
			String insertId = null;
			if(doc.getLocationType().equals("0")) {// 系统默认
				insertId = Constant.USER_ID;
			}else {
				UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
				insertId =	user.getUserId();
			}

			baseDto = dFDSrv.defaultFormSubmit(doc,insertId);

			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

}
