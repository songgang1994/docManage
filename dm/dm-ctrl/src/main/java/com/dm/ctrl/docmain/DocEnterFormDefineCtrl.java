package com.dm.ctrl.docmain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.DocumentItemSettingDto;
import com.dm.dto.DocumentItemSettingFormDto;
import com.dm.dto.ErrCode;
import com.dm.dto.LoginDto;
import com.dm.dto.ResultDto;
import com.dm.srv.docitem.DocEnterFormDefineSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
@Controller
@RequestMapping("/docmain")
public class DocEnterFormDefineCtrl extends BaseCtrl {

	@Autowired
	private DocEnterFormDefineSrv dEFDSrv;

	/**
	 * 跳转自定义表单设置页面
	 * @return
	 */
	@RequestMapping("/enterformdefine")
	private ModelAndView EnterFormDefineInit(@RequestParam(value="docType",required=false,defaultValue="")String docType) {
		ModelAndView mv = new ModelAndView();
		try {
			DocumentItemSettingDto disDto = dEFDSrv.docItemSettingInit(docType);
			mv.addObject("info", disDto);
			mv.addObject("selectDocType", docType);
			mv.setViewName("docmain/DocEnterFormDefine");
			return mv;
		} catch (Exception e) {e.printStackTrace();
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	/**
	 * 自定义表单页面设置
	 * @param docForm
	 * @return resultDto
	 */
	@RequestMapping("/enterform")
	@ResponseBody
	private ResultDto<BaseDto> EnterFormDefine(@RequestBody DocumentItemSettingFormDto docForm) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			docForm.setCreator(user.getUserId());
			baseDto = dEFDSrv.docItemSettingDefine(docForm);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

}
