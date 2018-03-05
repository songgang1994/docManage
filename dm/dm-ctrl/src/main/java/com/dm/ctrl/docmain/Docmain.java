package com.dm.ctrl.docmain;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.ctrl.util.LoginCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocItemDataSourceCodeDto;
import com.dm.dto.DocItemsDto;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.UserEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.docitem.DocItemsSrv;
import com.dm.tool.Constant;
import com.dm.srv.util.FindParmSrv;
import com.dm.tool.StringUtil;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档数据项管理ctrl
 */

@Controller
@RequestMapping("/docmain")
public class Docmain extends BaseCtrl {
	// 出力LOG用
   private static final Logger log = LoggerFactory.getLogger(LoginCtrl.class);
	@Autowired
	private DocItemsSrv docItemSrv;

	@Autowired
	private FindParmSrv findParmSrv;

	//初始化画面
	@RequestMapping("/itemsearch")
	public ModelAndView search(
			@RequestParam(value = "docItemsName", defaultValue = "") String docItemsName,
			@RequestParam(value = "docItemsType", defaultValue = "") String docItemsType,
			@RequestParam(value = "docItemName", defaultValue = "") String docItemName,
			@RequestParam(value = "itemId", defaultValue = "") String itemId,
			@RequestParam(value = "itemName", defaultValue = "") String itemName
			) {
		ModelAndView mv = new ModelAndView();
		try {
			docItemsName = java.net.URLDecoder.decode(docItemsName, "UTF-8");
			docItemName = java.net.URLDecoder.decode(docItemName, "UTF-8");
			itemName = java.net.URLDecoder.decode(itemName, "UTF-8");
			docItemsName = !StringUtil.isEmpty(docItemsName) ? docItemsName.trim() : "";
			docItemsType = !StringUtil.isEmpty(docItemsType) ? docItemsType.trim() : "";
			mv.setViewName("docmain/DocItemSearch");
			mv.addObject("docItemsName", docItemsName);
			mv.addObject("docItemsType", docItemsType);
			mv.addObject("docItemName", docItemName);
			mv.addObject("itemId", itemId);
			mv.addObject("itemName", itemName);
			List<ParmMstEntity> itemTypes = findParmSrv.listItemTypes();
			mv.addObject("itemTypes", itemTypes);
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}
	//查询事件
	@RequestMapping("/searchList")
	@ResponseBody
	public ResultDto<DocItemsEntity> searchList(
			@RequestParam(value = "docItemsName", defaultValue = "") String docItemsName,
			@RequestParam(value = "docItemsType", defaultValue = "") String docItemsType) {
		ResultDto<DocItemsEntity> resultDto = new ResultDto<DocItemsEntity>();
		try {
			//开始的数据行数
			String start = request.getParameter("start");
			//每页的数据数
			String length = request.getParameter("length");
			int len=Integer.parseInt(length);
			if(len==-1) {
				len = Integer.MAX_VALUE;
			}
			//DT传递的draw:
			Integer draw = Integer.parseInt(request.getParameter("draw"));

			docItemsName = !StringUtil.isEmpty(docItemsName) ? docItemsName.trim() : "";
			docItemsType = !StringUtil.isEmpty(docItemsType) ? docItemsType.trim() : "";
			List<DocItemsEntity> list = docItemSrv.search(docItemsName, docItemsType,Integer.parseInt(start),len);
			//分页总数据
			int count = docItemSrv.getSearchCount(docItemsName, docItemsType);
			//设置cookie页数值
			Cookie cookie = new Cookie(Constant.ITEM_PAGE_LENGTH,length);
			cookie.setPath("/");
			response.addCookie(cookie);
			resultDto.setDraw(draw);
			resultDto.setRecordsTotal(count);
			resultDto.setRecordsFiltered(count);
			resultDto.setListData(list);
			resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
			return resultDto;
		}catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public ResultDto<BaseDto> delete(@RequestParam("docItemsCode") String docItemsCode) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		if (StringUtil.isEmpty(docItemsCode)) {
			resultDto.setRtnCode(ErrCode.RTN_CODE_ILLEGAL_REQUEST);
			return resultDto;
		}
		resultDto.setData(docItemSrv.delete(docItemsCode));
		return resultDto;
	}

//===============================新建修改文档数据项===============================//
	//画面跳转新建修改文档（新增模式）
	@RequestMapping(value = "/itemedit")
	public ModelAndView itemedit(String documentItemCode,
													@RequestParam(value = "docItemsName", defaultValue = "") String docItemsName,
													@RequestParam(value = "itemId", defaultValue = "") String itemId,
													@RequestParam(value = "itemName", defaultValue = "") String itemName) {
		ModelAndView mv = new ModelAndView();
		try {
			docItemsName = java.net.URLDecoder.decode(docItemsName, "UTF-8");
			itemName = java.net.URLDecoder.decode(itemName, "UTF-8");
			if (documentItemCode!=null) {
				//当传来的参数数据项编号不为空时，将反应到画面
				DocItemsEntity docItemsEntity = docItemSrv.getDocItem(documentItemCode);
				request.setAttribute("docitem", docItemsEntity);
			}
			//设置打开页面后属于什么模式
			request.setAttribute("flag", 2);
			//获取传来的参数数据项编号
			request.setAttribute("documentItemCode", documentItemCode);
			//查询数据源选择
			List<DocItemDataSourceCodeEntity> list = docItemSrv.getDateList();
			request.setAttribute("list", list);
			mv.addObject("docItemsName", docItemsName);
			mv.addObject("itemId", itemId);
			mv.addObject("itemName",itemName );
			mv.setViewName("docmain/DocItemEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	//画面跳转新建修改文档(详细模式)
	@RequestMapping(value = "/itemlook")
	public ModelAndView itemlook(HttpServletRequest request,String documentItemCode) {
		ModelAndView mv = new ModelAndView();
		if (documentItemCode!=null) {
			//当传来的参数数据项编号不为空时，将反应到画面
			DocItemsEntity docItemsEntity = docItemSrv.getDocItem(documentItemCode);
			request.setAttribute("doc", docItemsEntity);
		}
		//查询数据源选择
		List<DocItemDataSourceCodeEntity> list = docItemSrv.getDateList();
		request.setAttribute("list", list);
		//设置打开页面后属于什么模式
		request.setAttribute("flag", Constant.RESERVE_LENGTH_NUM_ZERO);
		//获取传来的参数数据项编号
		request.setAttribute("documentItemCode", documentItemCode);
		mv.setViewName("docmain/DocItemEdit");
		return mv;
	}

	//画面跳转新建修改文档(修改模式)
	@RequestMapping(value = "/itemupdate")
	public ModelAndView itemupdate(String documentItemCode,
														@RequestParam(value = "docItemsName", defaultValue = "") String docItemsName,
														@RequestParam(value = "itemId", defaultValue = "") String itemId,
														@RequestParam(value = "itemName", defaultValue = "") String itemName
														) {
		ModelAndView mv = new ModelAndView();
		try {
			docItemsName = java.net.URLDecoder.decode(docItemsName, "UTF-8");
			itemName = java.net.URLDecoder.decode(itemName, "UTF-8");
			if (documentItemCode!=null) {
				//当传来的参数数据项编号不为空时，将反应到画面
				DocItemsEntity docItemsEntity = docItemSrv.getDocItem(documentItemCode);
				//判断数据库是否公共与isfromto 是否为空，为空时赋值0
				if(docItemsEntity.getIsFromToItem()==null) {
					docItemsEntity.setIsFromToItem(new BigDecimal(Constant.RESERVE_LENGTH_NUM_ZERO));
				}else if(docItemsEntity.getIsPublicItem()==null) {
					docItemsEntity.setIsPublicItem("0");
				}else if(docItemsEntity.getIsFromToItem()==null && docItemsEntity.getIsPublicItem()==null){
					docItemsEntity.setIsFromToItem(new BigDecimal(Constant.RESERVE_LENGTH_NUM_ZERO));
					docItemsEntity.setIsPublicItem("0");
				}
				request.setAttribute("doc", docItemsEntity);
			}
			//查询数据源选择
			List<DocItemDataSourceCodeEntity> list = docItemSrv.getDateList();
			request.setAttribute("list", list);
			//设置打开页面后属于什么模式
			request.setAttribute("flag", 1);
			//获取传来的参数数据项编号
			request.setAttribute("documentItemCode", documentItemCode);
			mv.addObject("docItemsName", docItemsName);
			mv.addObject("itemId", itemId);
			mv.addObject("itemName",itemName );
			mv.setViewName("docmain/DocItemEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}

	}

	//数据项新增，修改
	@RequestMapping(value="/docItem")
	@ResponseBody
	public ResultDto<BaseDto> docItem(@RequestBody DocItemsDto DocItemsDto,String numformat){
		ResultDto<BaseDto>  resultDto = new ResultDto();
		resevedocItem(DocItemsDto);
		// 合法性check结束
		try {
			java.net.URLDecoder urlDecoder=new java.net.URLDecoder();
		    numformat = urlDecoder.decode(request.getParameter("numformat"),"utf-8");
			//获取登录人Id
			UserEntity user = (UserEntity)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			String userId = user.getUserId();
			//数据项新增修改
			BaseDto baseDto = docItemSrv.docItem(DocItemsDto,userId,numformat);
			resultDto.setData(baseDto);
			return resultDto;
		}catch(Exception e) {
			// 出力异常信息到LOG中
		    log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
//===============================新建修改文档数据项===============================//

//=====================================新建修改查询数据源区域===============================================
	//文档项目数据源画面显示
	@RequestMapping(value = "/itemdatasourcesearch")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("docmain/DocItemDataSrcList");
		return mv;
	}
	//修改成功返回详细画面
		@RequestMapping(value = "/itemdatasourcesearchbacklist")
		public ModelAndView backList(String documentItemSourceName) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("docmain/DocItemDataSrcList");
			mv.addObject("documentItemSourceName",documentItemSourceName);
			return mv;
		}
	//列表数据显示
	@RequestMapping(value = "/dataSrcList")
	@ResponseBody
	public ResultDto<DocItemDataSourceCodeDto> dataSrcList(@RequestParam(value = "documentItemSourceName", defaultValue = "") String documentItemSourceName) {
		ResultDto<DocItemDataSourceCodeDto> resultDto = new ResultDto<DocItemDataSourceCodeDto>();
		try {
			//开始的数据行数
			String start = request.getParameter("start");
			//每页的数据数
			String length = request.getParameter("length");
			int len=Integer.parseInt(length);
			if(len==-1) {
				len = Integer.MAX_VALUE;
			}
			//DT传递的draw:
			Integer draw = Integer.parseInt(request.getParameter("draw"));
			//查询列表信息
			List<DocItemDataSourceCodeDto> resultList = docItemSrv.getDataSrcList(documentItemSourceName,Integer.parseInt(start),len);
			int count = docItemSrv.getDataSrcListCount(documentItemSourceName);
			//设置cookie页数值
			Cookie cookie = new Cookie(Constant.ITEMSRC_PAGE_LENGTH,length);
			cookie.setPath("/");
			response.addCookie(cookie);
			resultDto.setListData(resultList);
			resultDto.setDraw(draw);
			resultDto.setRecordsTotal(count);
			resultDto.setRecordsFiltered(count);
			resultDto.setRtnCode(BizCode.BIZ_CODE_SUCCESS);
			return resultDto;
		} catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	//列表数据删除
	@RequestMapping(value = "/dataSrcListDelete")
	@ResponseBody
	public ResultDto<BaseDto> DataSrcListDelete(String documentItemSourceCode){
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			baseDto = docItemSrv.DataSrcListDelete(documentItemSourceCode);
			// 设置业务处理结果
			resultDto.setData(baseDto);
			return resultDto;
		}catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//数据源详细画面显示
	@RequestMapping(value = "/itemdatasourceedit",method = RequestMethod.GET)
	public ModelAndView detailInit() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("dataSrcFlag", "detail");
		mv.setViewName("docmain/DocItemDataSrcEdit");
		return mv;
	}

	//数据源添加画面显示
	@RequestMapping(value = "/addDataSrc",method = RequestMethod.GET)
	public ModelAndView addInit(String documentItemSourceName) {
		ModelAndView mv = new ModelAndView();
		try {
			documentItemSourceName = java.net.URLDecoder.decode(documentItemSourceName, "UTF-8");
			mv.addObject("dataSrcFlag", "add");
			mv.addObject("documentItemSourceName",documentItemSourceName);
			mv.setViewName("docmain/DocItemDataSrcEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	//数据源编辑画面显示
	@RequestMapping(value = "/editDataSrc/{documentItemSourceCode}",method = RequestMethod.GET)
	public ModelAndView editInit(
			@PathVariable("documentItemSourceCode")String documentItemSourceCode,Model model,
			@RequestParam(value = "documentItemSourceName", defaultValue = "") String documentItemSourceName
			) {

		ModelAndView mv = new ModelAndView();
		try {
			documentItemSourceName = java.net.URLDecoder.decode(documentItemSourceName, "UTF-8");
			//查询相应数据源编号得数据源信息
			if(!documentItemSourceCode.isEmpty()) {
				DocItemDataSourceCodeDto list = docItemSrv.getDetailDataSrc(documentItemSourceCode);
				String[] detailDataItem = list.getDetail().split(",");
				//获取数据源条目名称数组
				List detailList = java.util.Arrays.asList(detailDataItem);
				String[] detailDataItemOther = list.getOtherDetail().split(",");
				//获取数据源附加值数组
				List detailOtherList = java.util.Arrays.asList(detailDataItemOther);
				String[] detailDataItemVal = list.getValDetail().split(",");
				//获取数据源条目值数组
				List detailValList = java.util.Arrays.asList(detailDataItemVal);
				//将获取到的值赋值给画面
				mv.addObject("detailDataSrcCode", documentItemSourceCode);
				mv.addObject("detailDataSrc", list);
				mv.addObject("detailList", detailList);
				mv.addObject("detailOtherList", detailOtherList);
				mv.addObject("detailValList", detailValList);
				mv.addObject("dataSrcFlag", "edit");
				mv.addObject("documentItemSourceName", documentItemSourceName);
			}
			mv.setViewName("docmain/DocItemDataSrcEdit");
			return mv;
		} catch (UnsupportedEncodingException e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
	}

	//数据源详细画面显示
		@RequestMapping(value = "/editDataSrca/{documentItemSourceCode}",method = RequestMethod.GET)
		public ModelAndView editInita(@PathVariable("documentItemSourceCode")String documentItemSourceCode,Model model,
				@RequestParam(value = "documentItemSourceName", defaultValue = "")String documentItemSourceName) {
			ModelAndView mv = new ModelAndView();
			try {
				documentItemSourceName = java.net.URLDecoder.decode(documentItemSourceName, "UTF-8");
				//查询相应数据源编号得数据源信息
				if(!documentItemSourceCode.isEmpty()) {
					DocItemDataSourceCodeDto list = docItemSrv.getDetailDataSrc(documentItemSourceCode);
					String[] detailDataItem = list.getDetail().split(",");
					//获取数据源条目名称数组
					List detailList = java.util.Arrays.asList(detailDataItem);
					String[] detailDataItemOther = list.getOtherDetail().split(",");
					//获取数据源附加值数组
					List detailOtherList = java.util.Arrays.asList(detailDataItemOther);
					String[] detailDataItemVal = list.getValDetail().split(",");
					//获取数据源条目值数组
					List detailValList = java.util.Arrays.asList(detailDataItemVal);
					//将获取到的值赋值给画面
					mv.addObject("detailDataSrcCode", documentItemSourceCode);
					mv.addObject("documentItemSourceName", documentItemSourceName);
					mv.addObject("detailDataSrc", list);
					mv.addObject("detailList", detailList);
					mv.addObject("detailOtherList", detailOtherList);
					mv.addObject("detailValList", detailValList);
					mv.addObject("dataSrcFlag", "deta");
				}
				mv.setViewName("docmain/DocItemDataSrcEdit");
				return mv;
			} catch (UnsupportedEncodingException e) {
				// 出力异常信息到LOG中
				log.error(e.getMessage());
				mv.addObject("exMsg", Constant.MS_ERROR);
				mv.setViewName("util/error");
				return mv;
			}
		}

	//数据源修改/新建
	@RequestMapping(value = "/dataSrcAddOrEdit")
	@ResponseBody
	public ResultDto<BaseDto> DataSrcAddOrEdit(String documentItemSourceName,String detail,String otherDetail,String itemVal,
			String documentItemSourceCode){
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			baseDto = docItemSrv.DataSrcAddOrEdit(documentItemSourceName, detail, otherDetail, itemVal,documentItemSourceCode);
			// 设置业务处理结果
			resultDto.setData(baseDto);
			return resultDto;
		}catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	//数据源条目删除
	@RequestMapping(value = "/dataSrcDelete")
	@ResponseBody
	public ResultDto<BaseDto> DataSrcDelete(String documentItemSourceCode,String itemVal){
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();
		try {
			baseDto = docItemSrv.DataSrcDelete(documentItemSourceCode,itemVal);
			// 设置业务处理结果
			resultDto.setData(baseDto);
			return resultDto;
		}catch (Exception e) {
			// 出力异常信息到LOG中
			log.error(e.getMessage());
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}
//=====================================新建修改查询数据源区域结束===============================================
//=====================================数据合法性check区域===============================================
	// 新建修改文档信息合法性check
	public boolean resevedocItem(DocItemsDto docItemsDto) {
		boolean ok = true;
		//判断数据项类型是否为空
		if(docItemsDto.getDocumentItemType() == null) {
			ok = false;
		}
		//判断数据源选择是否为空
		if(docItemsDto.getDocumentItemSourceCode() == null) {
			ok = false;
		}
		//判断数据项显示名称是否为空
		if(docItemsDto.getDocumentItemName() == null) {
			ok = false;
		}else {
			//判断数据项显示名称是否超过数据库限制
			if(docItemsDto.getDocumentItemName().length() > Constant.RESERVE_DOCUMENTITEMNAME_MAX_LENGTH) {
				ok = false;
			}
		}
		//判断最大长度是否为空
		if(docItemsDto.getMaxLength() == null) {
			ok = false;
		}

		return ok;
	}




//=====================================数据合法性check区域结束===============================================

}
