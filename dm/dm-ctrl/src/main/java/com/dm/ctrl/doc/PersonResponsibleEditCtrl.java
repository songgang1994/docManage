package com.dm.ctrl.doc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.PersonResDto;
import com.dm.dto.ResultDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.doc.PersonResponsibleEditSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档数据项管理
 */
@Controller
@RequestMapping("/doc")
public class PersonResponsibleEditCtrl extends BaseCtrl {

	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(PersonResponsibleEditCtrl.class);

	@Autowired
	private PersonResponsibleEditSrv pRESrv;

	/**
	 * 跳转默认设置画面
	 *
	 * @return
	 */
	@RequestMapping("/personresponsibleedit")
	private ModelAndView itemSearchInit() {
		ModelAndView mv = new ModelAndView();

		try {
			// 获取系统登录人信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			// 根据登录者id获取登录者所属部门
			List<DepartmentInfoEntity> deptlist = pRESrv.getDeptByUserId(user.getUserId());
			// 获取文档类型
			List<DocItemDataSourceCodeEntity> docType = pRESrv.getDocSource();
			//获取文档状态
			List<ParmMstEntity> docStatus = pRESrv.getDocStatus();

			mv.addObject("depts", deptlist);
			mv.addObject("docTypes", docType);
			mv.addObject("docStatus", docStatus);
			mv.setViewName("doc/PersonResponsibleEdit");
			return mv;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			return mv;
		}
	}

	@RequestMapping("/query")
	@ResponseBody
	private ResultDto<PersonResDto> query(String personId, String docType, String docStatus) {
		ResultDto<PersonResDto> resultDto = new ResultDto<>();
		try {
			// 获取系统登录人信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			List<PersonResDto> list = pRESrv.query(user.getUserId(), personId, docType, docStatus);
			resultDto.setListData(list);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			return resultDto;
		}
	}

	@RequestMapping("/reseditsubmit")
	@ResponseBody
	private ResultDto<BaseDto> resEditSubmit(String[] docCode, String appointPersonId) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

			// 获取系统登录人信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			baseDto = pRESrv.resEditSubmit(docCode, appointPersonId, user.getUserId());
			try {return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			return resultDto;
		}
	}
}
