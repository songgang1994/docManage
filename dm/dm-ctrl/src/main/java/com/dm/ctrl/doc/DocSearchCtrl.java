package com.dm.ctrl.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.DocSearchDto;
import com.dm.dto.ErrCode;
import com.dm.dto.LoginDto;
import com.dm.dto.ResultDto;
import com.dm.dto.TableDto;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocSearchItemSettingEntity;
import com.dm.srv.doc.DocSearchSrv;
import com.dm.srv.docitem.DocFormDefaultSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档数据项管理
 */
@Controller
@RequestMapping("/doc")
public class DocSearchCtrl extends BaseCtrl {

	@Autowired
	private DocSearchSrv docSearchSrv;
	@Autowired
	private DocFormDefaultSrv dFDSrv;

	/**
	 * 跳转默认设置画面
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/search")
	private ModelAndView itemSearchInit() {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map = new HashMap<>();

		try {
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			List<DocSearchItemSettingEntity> list = dFDSrv.isCustomDefine(user.getUserId());

			if (list != null && list.size() > 0) {
				map = docSearchSrv.getQueryDataItemByUserId(user.getUserId());
			} else {
				map = docSearchSrv.getQueryDataItemByUserId(Constant.USER_ID);
			}

			session.setAttribute(Constant.LIST_SHOW_DATA_ITEM, (List<DocSearchDto>) map.get("listItems1"));
			session.setAttribute(Constant.USER_CUSTOM_QUERY_DATA_ITEM, (List<DocSearchDto>) map.get("userCustomItems"));

			mv.addAllObjects(map);
			mv.setViewName("doc/DocSearch");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/searchCustomTable")
	@ResponseBody
	private ResultDto<TableDto> getDataTable() {
		ResultDto<TableDto> resultDto = new ResultDto<>();
		TableDto table = new TableDto();
		try {
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			List<DocSearchDto> listItems = (List<DocSearchDto>) session.getAttribute(Constant.LIST_SHOW_DATA_ITEM);
			List<DocSearchDto> userCustomItems = (List<DocSearchDto>) session
					.getAttribute(Constant.USER_CUSTOM_QUERY_DATA_ITEM);

			table = docSearchSrv.getTableData(listItems, userCustomItems, user.getUserId());

			session.setAttribute(Constant.KEEP_DOC_SEARCH_LIST_DATA, table);
			resultDto.setData(table);
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	};

	@RequestMapping("/handledoccode")
	@ResponseBody
	private ResultDto<BaseDto> handleDocCode(String line) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			TableDto table = (TableDto) session.getAttribute(Constant.KEEP_DOC_SEARCH_LIST_DATA);
			baseDto = docSearchSrv.handleDocCode(table, line);
			resultDto.setData(baseDto);
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
		return resultDto;

	}

	@RequestMapping("/handlefile")
	@ResponseBody
	private ResultDto<BaseDto> handleFile(String line, String fileNo) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			TableDto table = (TableDto) session.getAttribute(Constant.KEEP_DOC_SEARCH_LIST_DATA);
			baseDto = docSearchSrv.handleFile(table, line, fileNo);
			resultDto.setData(baseDto);
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
		return resultDto;

	}

	@RequestMapping("/handleedit")
	@ResponseBody
	private ResultDto<BaseDto> handleEdit(String line) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			TableDto table = (TableDto) session.getAttribute(Constant.KEEP_DOC_SEARCH_LIST_DATA);
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			baseDto = docSearchSrv.handleEdit(table, line, user.getUserId());
			resultDto.setData(baseDto);
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
		return resultDto;

	}

	@RequestMapping("/handledel")
	@ResponseBody
	private ResultDto<BaseDto> handleDel(String line) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			TableDto table = (TableDto) session.getAttribute(Constant.KEEP_DOC_SEARCH_LIST_DATA);
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			baseDto = docSearchSrv.handleDelete(table, line, user.getUserId());
			resultDto.setData(baseDto);
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
		return resultDto;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/handleexport")
	@ResponseBody
	private ResultDto<DocFileEntity> handleExport(String line) {
		ResultDto<DocFileEntity> resultDto = new ResultDto<>();
		DocFileEntity baseDto = new DocFileEntity();
		try {
			TableDto table = (TableDto) session.getAttribute(Constant.KEEP_DOC_SEARCH_LIST_DATA);
			List<DocSearchDto> listItems = (List<DocSearchDto>) session.getAttribute(Constant.LIST_SHOW_DATA_ITEM);
			LoginDto user = (LoginDto) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);

			baseDto = docSearchSrv.handleExport(table, listItems, user);
			resultDto.setData(baseDto);
		} catch (Exception e) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
		return resultDto;

	}

	@RequestMapping("/downloadcsv/{fileName}")
	private void downloadCsv(@PathVariable("fileName") String fileName) {
		FileInputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			response.setContentType("application/x-download;charset=UTF-8");
			String file_name = new String(fileName.getBytes(), "ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;fileName=" + file_name.concat(".xlsx"));// 设置文件名
			File file = new File("D:/" + fileName + ".xlsx");
			inputStream = new FileInputStream(file);
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				inputStream.close();
				delCsv(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	private void delCsv(String fileName) {
		File file = new File("D:/" + fileName + ".xlsx");
		if (file.exists()) {
			file.delete();
			return;
		}
	};

}
