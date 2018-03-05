package com.dm.ctrl.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.ErrCode;
import com.dm.dto.ParmMstDto;
import com.dm.dto.ResultDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.util.FindParmSrv;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：共通函数
 */
@Controller
@RequestMapping("/parm")
public class FindParmCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(FindParmCtrl.class);

	//区分值获取的Service
	@Autowired
	private FindParmSrv findParmSrv;

	@RequestMapping(value = "/projectType")
	@ResponseBody
	public ResultDto<ParmMstEntity> getProjectTypeList() {
		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		try {
			List<ParmMstEntity> list = findParmSrv.getProjectTypeList();
			resultDto.setListData(list);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//查询使用类别
	@RequestMapping(value = "/userType")
	@ResponseBody
	public ResultDto<ParmMstEntity> getUserType(){
		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		List<ParmMstEntity> list = findParmSrv.getUserType();
		resultDto.setListData(list);
		return resultDto;
	}

	//查询设备状态
	@RequestMapping(value = "/useState")
	@ResponseBody
	public ResultDto<ParmMstEntity> getState(){
		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		List<ParmMstEntity> list = findParmSrv.getstate();
		resultDto.setListData(list);
		return resultDto;
	}

	//查询设备位置
	@RequestMapping(value = "/usePlace")
	@ResponseBody
	public ResultDto<ParmMstEntity> getPlace(){
		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		List<ParmMstEntity> list = findParmSrv.getplace();
		resultDto.setListData(list);
		return resultDto;
	}


	// 文档数据类型
	@RequestMapping(value = "/docItemType")
	@ResponseBody
	public ResultDto<ParmMstEntity> listItemType() {
		ResultDto<ParmMstEntity> resultDto = new ResultDto<>();
		List<ParmMstEntity> list = findParmSrv.listItemTypes();
		resultDto.setListData(list);
		return resultDto;
	}

	//查询法人类型
	@RequestMapping(value = "/legalType")
	@ResponseBody
	public ResultDto<ParmMstDto> getLegal(){
		ResultDto<ParmMstDto> resultDto = new ResultDto<>();
		List<ParmMstDto> list = findParmSrv.getLegal();
		resultDto.setListData(list);
		return resultDto;
	}

	//查询操作内容
	@RequestMapping(value = "/oprContent")
	@ResponseBody
	public ResultDto<ParmMstDto> getOperate(){
		ResultDto<ParmMstDto> resultDto = new ResultDto<>();
		List<ParmMstDto> list = findParmSrv.getOperate();
		resultDto.setListData(list);
		return resultDto;
	}

	//获取文档类型
	@RequestMapping(value = "/docType")
	@ResponseBody
	public ResultDto<DocItemDataSourceCodeEntity> getDocType(){
		ResultDto<DocItemDataSourceCodeEntity> resultDto = new ResultDto<>();
		List<DocItemDataSourceCodeEntity> list = findParmSrv.getDocType();
		resultDto.setListData(list);
		return resultDto;
	}
}
