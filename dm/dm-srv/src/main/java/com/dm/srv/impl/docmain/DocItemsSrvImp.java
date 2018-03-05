package com.dm.srv.impl.docmain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.DocItemsDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocItemDataSourceCodeDto;
import com.dm.dto.DocItemsDto;
import com.dm.dto.ItemSettingUsageDto;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.srv.docitem.DocItemsSrv;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档数据项管理service实现
 */

@Service
public class DocItemsSrvImp extends BaseSrvImp implements DocItemsSrv {

	@Autowired
	private DocItemsDao docItemsDao;

	@Override
	public List<DocItemsEntity> search(String docItemsName, String docItemsType,int start,int length) {

		docItemsName = "%" + docItemsName + "%";

		List<DocItemsEntity> list = docItemsDao.search(
				docItemsName, docItemsType,start,length);
		return list;
	}

	@Override
	public BaseDto delete(String docItemCode) {

		BaseDto baseDto = new BaseDto();

		List<ItemSettingUsageDto> itemSettingUsage = docItemsDao.itemSettingUsages(docItemCode);
		if (itemSettingUsage.size() > 0) {
			String message = String.format(
					"该数据项已在文档录入表单定义表中使用，不能进行删除处理(文档类型:[%s])。",
					String.join(",", itemSettingUsage.stream()
							.map(x -> x.getDocumentItemSourceName())
							.collect(Collectors.toList())));

			baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_DOCUMENT_ALREADY);
			baseDto.setBizExeMsgs(Arrays.asList(message));

			return baseDto;
		}

		int count = docItemsDao.searchItemSettingUsage(docItemCode);
		if (count != 0) {

			baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_DOCUMENT_USE);
			baseDto.setBizExeMsgs(Arrays.asList("该数据项已在文档一览检索数据项定义表中使用，不能进行删除处理。"));

			return baseDto;
		}

		int delete = docItemsDao.delete(docItemCode);
		if(delete > 0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_DOCUMENT_SUCCESS);
		}else {
			baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_DOCUMENT_FAILED);
		}
		return baseDto;
	}

//====================================新建修改文档数据源==============================//
	//数据源选择遍历
	@Override
	public List<DocItemDataSourceCodeEntity> getDateList() {
		//查询数据源
		List<DocItemDataSourceCodeEntity> list = docItemsDao.getDateList();
		return list;
	}

	//当数据项编号不为空时，查询所有数据
	@Override
	public DocItemsEntity getDocItem(String documentItemCode) {
		//查询所有数据项信息
		DocItemsEntity docItems = docItemsDao.getDocItem(documentItemCode);
		return docItems;
	}

	//通过数据项编号修改信息（combox未改变的情况下）
	@Override
	public BaseDto updateDocItem(DocItemsDto DocItemsDto, String userId,String numformat) {
		BaseDto DocItem = new BaseDto();
		//判断公共项是否为空，为空的赋值为默认值0
		if(DocItemsDto.getIsPublicItem()==null) {
			DocItemsDto.setIsPublicItem(Constant.ZERO);
		}
		//判断isfromto是否为空，为空赋值默认值为1
		if (DocItemsDto.getIsFromToItem() == null) {
			DocItemsDto.setIsFromToItem(new BigDecimal(Constant.ZERO));
		}
		//修改数据项信息
		int doc = docItemsDao.updateDocItem(DocItemsDto, userId,numformat);
		if (doc>0) {
			//修改成功
			DocItem.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
		}else {
			//参数错误，修改失败
			DocItem.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return DocItem;
	}

	//删除文档数据项（修改的情况下【选中--不选中】）
	@Override
	public int deleteDoc(String documentItemCode) {
		return docItemsDao.deleteDoc(documentItemCode);
	}

	//查询文档数据项（修改的情况下【不选中--选中】）
	@Override
	public List<DocItemsDto> getDbName() {
		List<DocItemsDto> listDB = docItemsDao.getDbName();
		return listDB;
	}

	//修改信息时插入数据
	@Override
	public BaseDto insertDb(DocItemsDto DocItemsDto,String userId) {
		BaseDto DocItem = new BaseDto();
		//判断公共项是否为空，为空的赋值为默认值0
		if(DocItemsDto.getIsPublicItem()==null) {
			DocItemsDto.setIsPublicItem(Constant.ZERO);
		}
		//获取自定义项目字段名
		List<DocItemsDto> listDB = getDbName();
		List<String>  list = new ArrayList<>();
		List<String>  list1 = new ArrayList<>();
		for(int j = 1;j<=50;j++) {
			list.add("CUSTOME_ITEM"+j);
		}
		for(int j=0;j<listDB.size();j++) {
			list1.add(listDB.get(j).getDbItemName());
		}
		//除去相同元素
        list.removeAll(list1);
        //取元素中的第一个元素
	    String DBName =  list.get(0).toString();
	    //插入数据项信息
		int insert = docItemsDao.insertDb(DocItemsDto, DBName,userId);
		if (insert>0) {
			//插入成功
			DocItem.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
		}else {
			//参数错误，插入失败
			DocItem.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
		}
		return DocItem;
	}

	//查询数据库最大数据项编号
	@Override
	public DocItemsDto maxDocNo() {
		DocItemsDto sel = docItemsDao.maxDocNo();
		return sel;
	}

	//新增数据项信息（当fromto项目未选中时,与选中时）
	@Override
	public BaseDto insertAll(DocItemsDto DocItemsDto, String userId, String numformat) {
		BaseDto DocItem = new BaseDto();
		//获取是否公共项
		String isPublicItem = DocItemsDto.getIsPublicItem();
		//当是否为公共项未选中时，赋值为0
		if(isPublicItem==null) {
			DocItemsDto.setIsPublicItem(Constant.ZERO);
		}
		//获取自定义项目字段名
		List<DocItemsDto> listDB =getDbName();
		List<String>  listall = new ArrayList<>();
		List<String> DBlist = new ArrayList<>();
		for(int j = 1;j<=50;j++) {
			listall.add("CUSTOME_ITEM"+j);
		}
		for(int j=0;j<listDB.size();j++) {
			DBlist.add(listDB.get(j).getDbItemName());
		}
		//除去相同元素
        listall.removeAll(DBlist);
        //取元素中的第一个元素
		String first =  listall.get(0).toString();
		//取元素中的第二个元素
		String second =  listall.get(1).toString();
		//获取查询结果（最大数据项编号）
		DocItemsDto maxNo =maxDocNo();
		String Code = null;
		if(maxNo==null) {
			Code="DOCUMENT_ITEM_C00001";
		}else {
			//Code字符串最后数据自增长
			Code = maxNo.getDocumentItemCode();
			 String version = Code.substring(Code.indexOf("_C")+2);
			 String result = ""+(Integer.parseInt(version)+1);
			 int size = 5-result.length();
			 for(int j=0;j<size;j++){
			 result="0"+result;
			 }
			 Code = Code.substring(0,Code.indexOf("_C")+2)+result;
		}
		//获取isfrom项目 值
		BigDecimal isFromToItem = DocItemsDto.getIsFromToItem();
		//判断isfromto是否选中
		if(isFromToItem==null || isFromToItem.toString() == "") {
			DocItemsDto.setIsFromToItem(new BigDecimal(Constant.ZERO));
			//新增文档项信息新增(isfromto未选中)
			int all = docItemsDao.insertAll(DocItemsDto, userId, Code, first,numformat);
			if(all>0) {
				//新增成功
				DocItem.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
			}else {
				//参数错误，新增失败
				DocItem.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}else {
			//选中时设置默认值为0
			DocItemsDto.setIsFromToItem(new BigDecimal(Constant.ONE));
			int all = docItemsDao.insertAll(DocItemsDto, userId, Code, first,numformat);
			if(all>0) {
				//新增成功
				DocItem.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
			}else {
				//参数错误，新增失败
				DocItem.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
			//新增文档项信息新增(isfromto选中)
			int alls = docItemsDao.insertAlls(DocItemsDto, userId, Code, second,numformat);
			if(alls>0) {
				//新增成功
				DocItem.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
			}else {
				//新增成功
				DocItem.setBizCode(BizCode.BIZ_CODE_INVALID_PARAMS);
			}
		}
		return DocItem;
	}

	//数据项新增修改
	@Override
	public BaseDto docItem(DocItemsDto DocItemsDto, String userId,String numformat) {
		BaseDto DocItem = new BaseDto();
		//判断当公共项为空值时，给他赋值0
		if (DocItemsDto.getIsPublicItem() == null) {
			DocItemsDto.setIsPublicItem(Constant.ZERO);
		}
		if(numformat.equals("undefined")) {
			numformat = "";
		}
		//获取模式
		int flag = DocItemsDto.getFlag();
		//获取数据项编号
		String documentItemCode = DocItemsDto.getDocumentItemCode();
		//获取combox选中状态  选中--不选中(ytn)  不选中--选中(nty)  不作修改(nothing)
		String combox = DocItemsDto.getState();
		//修改模式
		if (flag ==Constant.ITEM_UPDATE) {
			//combox没有任何修改时
			if (combox.equals(Constant.ITEM_CHANGE_U)) {
				//更新文档数据项
				 DocItem = updateDocItem(DocItemsDto,userId,numformat);
			//combox由选中到不选中
			}else if(combox.equals(Constant.ITEM_CHANGE_N)){
				//删除文档数据项
				 int del=deleteDoc(documentItemCode);
				 DocItem.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
				//更新文档数据项
				 DocItem = updateDocItem(DocItemsDto,userId,numformat);
			//combox由不选中到选中
			}else if (combox.equals(Constant.ITEM_CHANGE_Y)) {
				//更新文档数据项
				DocItem = updateDocItem(DocItemsDto,userId,numformat);
				//插入文档数据项
				DocItem = insertDb(DocItemsDto,userId);
			}
		//新增模式
		}else{
			DocItem = insertAll(DocItemsDto,userId,numformat);
		}
		return DocItem;
	}

//======================================新建修改文档数据源==============================//

//=====================================新建修改查询数据源区域===============================================
	//获取文档项目数据源列表
	@Override
	public List<DocItemDataSourceCodeDto> getInitDataSrcList(){
		List<DocItemDataSourceCodeDto> list = docItemsDao.getInitDataSrcList();
		return list;
	}

	//获取文档项目数据源列表
	@Override
	public List<DocItemDataSourceCodeDto> getDataSrcList(String documentItemSourceName,int start,int length){
		//画面查询条件不为空的场合
		if(documentItemSourceName == null) {
			documentItemSourceName ="";
		}
		//模糊查询
		List<DocItemDataSourceCodeDto> list = docItemsDao.getDataSrcList("%"+documentItemSourceName+"%",start,length);
		return list;
	}

	//列表数据删除
	@Override
	public BaseDto DataSrcListDelete(String documentItemSourceCode) {
		BaseDto baseDto = new BaseDto();
		//根据数据源编号查询数据项目
		List<DocItemsEntity> list = docItemsDao.getItems(documentItemSourceCode);
		//数据源被使用的场合
		if(list.size() > 0) {
			//不允许删除
			baseDto.setBizCode(BizCode.BIZ_CODE_SRCCANT_DELETE);
		//数据源未被使用的场合
		}else {
			//删除数据源
			int deleteListId = docItemsDao.DataSrcListDelete(documentItemSourceCode);
			//删除成功
			if(deleteListId > 0) {
				baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
			}else {
				baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			}
		}
		return baseDto;
	}

	//获取单个数据源信息
	@Override
	public DocItemDataSourceCodeDto getDetailDataSrc(String documentItemSourceCode) {
		DocItemDataSourceCodeDto docItemDataSourceCodeDto = docItemsDao.getDetailDataSrc(documentItemSourceCode);
		return docItemDataSourceCodeDto;
	}

	//数据源新建/修改
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto DataSrcAddOrEdit(String documentItemSourceName,String detail,String otherDetail,String itemVal,
			String documentItemSourceCode) {
		BaseDto baseDto = new BaseDto();
		//数据源条目名称表单数组
		String[] detailArray = detail.split(",");
		//数据源附加值表单数组
		String[] otherDetailArray = otherDetail.split(",");
		//数据源条目值表单数组
		String[] itemValue = itemVal.split(",");

		//获取数据源所有数据源编号
		List<DocItemDataSourceCodeDto> codeList = docItemsDao.getDocumentItemSourceCode();
		//声明编号最大数字
		int maxCodeNum = 0;
		//循环比较数据源编号，查出数字部分最大的编号数字
		for(int i = 0; i < codeList.size(); i++) {
			DocItemDataSourceCodeDto docItemDataSourceCodeDto = codeList.get(i);
			//从dto中取出数据源编号
			String DataSrcCode = docItemDataSourceCodeDto.getDocumentItemSourceCode();
			//获取数据源编号数字部分
			int codeNum = Integer.valueOf(DataSrcCode.substring(DataSrcCode.length()-5)).intValue();
			if(maxCodeNum < codeNum) {
				//取最大值
				maxCodeNum = codeNum;
			}
		}
		//新建数据源编号数字部分
		int newCodeNum = maxCodeNum + 1;
		//数字部分补零
		String num = new DecimalFormat("00000").format(newCodeNum);
		String str = "DATA_SOURCE_C";
		//新建数据源编号
		String newDocumentItemSourceCode = str.concat(num);

		//查询同一数据源编号下所有条目值
		List<DocItemDataSourceCodeDto> DocumentItemSourceNoList = docItemsDao.getDocumentItemSourceNo(documentItemSourceCode);
		//声明最大条目序号
		int maxNum = 0;
		if(DocumentItemSourceNoList.size() > 0) {
			for(int i = 0; i < DocumentItemSourceNoList.size(); i++) {
				DocItemDataSourceCodeDto docItemDataSourceCodeDto = DocumentItemSourceNoList.get(i);
				//从dto中取出数据源条目序号
				int DataSrcNo = docItemDataSourceCodeDto.getDocumentItemSourceNo();
				if(maxNum < DataSrcNo) {
					//取最大值
					maxNum = DataSrcNo;
				}
			}
		}

		List<DocItemDataSourceCodeDto> addList = new ArrayList<>();
		//循环前台传过来的表单数组
		for(int i = 0; i < detailArray.length; i++) {
			//设置新建或修改标识位
			//添加条目的场合
			if((itemValue[i].equals("clickAdd"))||(itemValue[i].equals("initVal"))) {
				if((documentItemSourceCode == null)||(documentItemSourceCode.equals(""))) {
					documentItemSourceCode = newDocumentItemSourceCode;
				}
				//设置数据源条目序号
				maxNum = maxNum + 1;
				//取数据源编号数字部分并补零
				String numAdd = new DecimalFormat("00000").format(Integer.valueOf(
						documentItemSourceCode.substring(documentItemSourceCode.length()-5)).intValue());
				//取数据源条目序号并补零
				String itemValNumAdd = new DecimalFormat("00").format(maxNum);
				//设置数据源条目值
				String itemValAdd = "C".concat(numAdd + "_" + itemValNumAdd);
				DocItemDataSourceCodeDto ddsc = new DocItemDataSourceCodeDto();
				//将需要插入的值插入dto
				ddsc.setDocumentItemSourceCode(documentItemSourceCode);
				ddsc.setDocumentItemSourceName(documentItemSourceName);
				ddsc.setDocumentItemSourceNo(maxNum);
				ddsc.setItemName(detailArray[i]);
				ddsc.setItemValue(itemValAdd);
				//排序号按照前台数组的顺序
				ddsc.setItemSortNo(i+1);
				ddsc.setItemOtherValue(otherDetailArray[i]);
				//将对象赋给list
				addList.add(ddsc);
			//修改条目的场合
			}else {
				DocItemDataSourceCodeDto ddsc = new DocItemDataSourceCodeDto();
				//将需要插入的值插入dto
				ddsc.setDocumentItemSourceCode(documentItemSourceCode);
				ddsc.setItemValue(itemValue[i]);
				ddsc.setItemName(detailArray[i]);
				//排序号按照前台数组的顺序
				ddsc.setItemSortNo(i+1);
				ddsc.setItemOtherValue(otherDetailArray[i]);
				//执行dao层修改方法
				int updateId = docItemsDao.updateDataSrc(ddsc);
				//更新成功
				if (updateId > 0) {
					baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
				//操作失败
				}else {
					baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
				}
			}
		}
		//添加条目的场合
		if(addList.size() > 0) {
			//执行dao层添加方法
			int insertId = docItemsDao.insertDataSrc(addList);
			//插入成功
			if (insertId > 0) {
				if(Arrays.asList(itemValue).contains("initVal")) {
					baseDto.setBizCode(BizCode.BIZ_CODE_INSERT_SUCCESS);
				}else {
					baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
				}
			//操作失败
			}else {
				baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			}
		}
		return baseDto;
	}

	//数据源删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto DataSrcDelete(String documentItemSourceCode,String itemVal) {
		BaseDto baseDto = new BaseDto();
		//查询数据项是否使用此条数据源
		List<DocItemsEntity> itemList = docItemsDao.getItems(documentItemSourceCode);
		//数据项未使用此条数据源的场合
		if(itemList.size() == 0) {
			//删除条目
			int deleteId = docItemsDao.deleteItem(documentItemSourceCode,itemVal);
			//删除成功
			if (deleteId > 0) {
				baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
			}else {
				baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
			}
		//数据项使用此条数据源的场合
		}else if(itemList.size() > 0) {
			List<DocItemsEntity> docItemList = new ArrayList<>();
			List<DocItemsEntity> docItemCustomList = new ArrayList<>();
			//循环查询到的数据项
			for(int i = 0; i < itemList.size(); i++) {
				DocItemsEntity docItemsEntity = itemList.get(i);
				//获取是否固定项目
				String fixId = docItemsEntity.getIsFixItem();
				//获取DB项目字段名
//					String DBItemName = docItemsEntity.getDbItemName();
				//固定项目的场合
				if(fixId.equals("0") ) {
					docItemList.add(docItemsEntity);
				//自定义数据项的场合
				}else if(fixId.equals("0")) {
					docItemCustomList.add(docItemsEntity);
				}
			}

			//数据管理表的场合
			if(docItemList.size() > 0) {
				//根据DB项目字段名查询数据项目
				List<DocDetailInfoEntity> detailInfoList = docItemsDao.getDetailInfo(docItemList);
				//字段内存在项目的场合
				if((detailInfoList.size() > 0)&&(detailInfoList != null)) {
					for(int i = 0; i < detailInfoList.size(); i++) {
						DocDetailInfoEntity columnVal = detailInfoList.get(i);
						if(columnVal != null) {
							//不允许删除
							baseDto.setBizCode(BizCode.BIZ_CODE_ITEMCANT_DELETE);
							return baseDto;
						}
					}
					//删除条目
					int deleteId = docItemsDao.deleteItem(documentItemSourceCode,itemVal);
					//删除成功
					if (deleteId > 0) {
						baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
					}else {
						baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
					}
				//字段内不存在项目的场合
				}else {
					//删除条目
					int deleteId = docItemsDao.deleteItem(documentItemSourceCode,itemVal);
					//删除成功
					if (deleteId > 0) {
						baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
					}else {
						baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
					}
				}
			}

			//文档自定义数据项表的场合
			if(docItemCustomList.size() > 0) {
				//根据DB项目字段名查询数据项目
				List<DocDetailCustomInfoEntity> detailCustomInfoList = docItemsDao.getDetailCustomInfo(docItemCustomList);
				//字段内存在项目的场合
				if((detailCustomInfoList.size() > 0)&&(detailCustomInfoList != null)) {
					for(int i = 0; i < detailCustomInfoList.size(); i++) {
						DocDetailCustomInfoEntity columnVal = detailCustomInfoList.get(i);
						if(columnVal != null) {
							//不允许删除
							baseDto.setBizCode(BizCode.BIZ_CODE_ITEMCANT_DELETE);
							return baseDto;
						}
					}
					//删除条目
					int deleteId = docItemsDao.deleteItem(documentItemSourceCode,itemVal);
					//删除成功
					if (deleteId > 0) {
						baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
					}else {
						baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
					}
				//字段内不存在项目的场合
				}else {
					//删除条目
					int deleteId = docItemsDao.deleteItem(documentItemSourceCode,itemVal);
					//删除成功
					if (deleteId > 0) {
						baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_SUCCESS);
					}else {
						baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
					}
				}
			}
		}
		return baseDto;
	}
//=====================================新建修改查询数据源区域结束===============================================

	@Override
	public int getSearchCount(String docItemsName, String docItemsType) {
		docItemsName = "%" + docItemsName + "%";

		int result = docItemsDao.getSearchCount(
				docItemsName, docItemsType);
		return result;
	}

	@Override
	public int getDataSrcListCount(String documentItemSourceName) {
		//画面查询条件不为空的场合
		if(documentItemSourceName == null) {
			documentItemSourceName ="";
		}
		//模糊查询
		int result = docItemsDao.getDataSrcListCount("%"+documentItemSourceName+"%");
		return result;
	}



}
