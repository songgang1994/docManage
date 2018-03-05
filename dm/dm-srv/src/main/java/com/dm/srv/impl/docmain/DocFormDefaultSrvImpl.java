package com.dm.srv.impl.docmain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.DocEnterDao;
import com.dm.dao.DocFormDefaultDao;
import com.dm.dto.BaseDto;
import com.dm.dto.DocDefaultSettingFormDto;
import com.dm.dto.SearchItemsDto;
import com.dm.dto.SearchItemsInfoDto;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.DocSearchItemSettingEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.srv.docitem.DocFormDefaultSrv;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;

@Service
public class DocFormDefaultSrvImpl extends BaseSrvImp implements DocFormDefaultSrv {

	@Autowired
	private DocFormDefaultDao dFDDao;
	@Autowired
	private DocEnterDao docEnterDao;

	@Override
	public List<DocItemsEntity> getItems() {

		// 获取数据项
		List<DocItemsEntity> allItems = dFDDao.getAllDocItems();
		// 去除 审核意见，审核部门 数据项
		for (int i = 0; i < allItems.size(); i++) {
			if (allItems.get(i).getDocumentItemName().equals("审核意见")
					|| allItems.get(i).getDocumentItemName().equals("审核部门")) {
				allItems.remove(i);
				i--;
			}
		}
		return allItems;
	}

	@Override
	public List<DocSearchItemSettingEntity> isCustomDefine(String userId) {
		return dFDDao.isCustomDefine(userId);
	}

	@Override
	public SearchItemsDto getSearchItems(String userId) {
		SearchItemsDto serachDto = new SearchItemsDto();
		// 获取数据项
		List<DocItemsEntity> allItems = dFDDao.getAllDocItems();
		// 过滤to项目,审核部门
		List<DocItemsEntity> allItems1 = allItems.stream().filter(x -> x.getDocumentItemNo().toString().equals("0")
				&& !x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F000012)).collect(Collectors.toList());
		serachDto.setItems(allItems1);

		// 获取显示项
		List<SearchItemsInfoDto> searchList = dFDDao.getSearchItems(userId);
		// 获取to项目
		List<SearchItemsInfoDto> searchToList = searchList.stream()
				.filter(x -> x.getDocumentItemNo().toString().equals("1") )
				.collect(Collectors.toList());
		// 过`滤to项目
		List<SearchItemsInfoDto> searchList1 = searchList.stream()
				.filter(x -> x.getDocumentItemNo().toString().equals("0") )
				.collect(Collectors.toList());

		// 将to项目默认值与from项目默认值拼接，便与前台显示,使用#拼接
		for(SearchItemsInfoDto x:searchList1) {
			for(SearchItemsInfoDto y:searchToList) {
				if(x.getDocumentItemCode().equals(y.getDocumentItemCode())) {
					String s = x.getDefaultItemValue().concat("#").concat(y.getDefaultItemValue());
					x.setDefaultItemValue(s);
				}
			}
		}

		List<SearchItemsInfoDto> left = searchList1.stream()
				.filter(x -> "1".equals(x.getLayoutCol().toString()))
				.collect(Collectors.toList());
		// 处理显示项，获取左侧/右侧 显示项
		List<SearchItemsInfoDto> right = searchList1.stream()
				.filter(x -> "2".equals(x.getLayoutCol().toString()))
				.collect(Collectors.toList());

		// 设置返回数据
		serachDto.setLeftList(left);
		serachDto.setRightList(right);

		// 获取匹配方式
		List<ParmMstEntity> params = dFDDao.getParams();
		serachDto.setParams(params);

		return serachDto;
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto defaultFormSubmit(DocDefaultSettingFormDto doc, String userId) {
		BaseDto baseDto = new BaseDto();

		List<DocSearchItemSettingEntity> list = new ArrayList<>();
		DocSearchItemSettingEntity docSet = null;
		DocSearchItemSettingEntity docSetTo = null;

		// 数据处理
		for (int i = 0; i < doc.getDocumentItemCode().length; i++) {
			docSet = new DocSearchItemSettingEntity();

			docSet.setUserId(userId);
			docSet.setDocumentItemCode(doc.getDocumentItemCode()[i]);
			docSet.setIsListItem(new BigDecimal(doc.getIsListItem()[i]));
			docSet.setIsSearchItem(new BigDecimal(doc.getIsSearchItem()[i]));
			docSet.setMatching(doc.getMatching()[i]);
			docSet.setLayoutRow(new BigDecimal(doc.getLayoutRow()[i]));
			docSet.setLayoutCol(new BigDecimal(doc.getLayoutCol()[i]));
			docSet.setCreator(userId);
			docSet.setCreateDt(new Date());
			docSet.setUpdater(userId);
			docSet.setUpdateDt(new Date());
			// 获取系统时间

			// 默认值与数据项编号 区分fromto

			if (doc.getIsFromToItem()[i].equals("1")) {// fromto项目
				docSetTo = new DocSearchItemSettingEntity();
				docSetTo.setUserId(userId);
				docSetTo.setDocumentItemCode(doc.getDocumentItemCode()[i]);
				docSetTo.setIsListItem(new BigDecimal(doc.getIsListItem()[i]));
				docSetTo.setIsSearchItem(new BigDecimal(doc.getIsSearchItem()[i]));
				docSetTo.setMatching(doc.getMatching()[i]);
				docSetTo.setLayoutRow(new BigDecimal(doc.getLayoutRow()[i]));
				docSetTo.setLayoutCol(new BigDecimal(doc.getLayoutCol()[i]));

				// 数据项
				String[] docNo = doc.getDocumentItemNo()[i].split("#");
				docSet.setDocumentItemNo(new BigDecimal(docNo[0]));
				docSetTo.setDocumentItemNo(new BigDecimal(docNo[1]));

				// 默认值
				String[] docDefault = doc.getDefaultVal()[i].split("#");
				if(docDefault != null && docDefault.length > 1) {

					docSet.setDefaultItemValue(docDefault[0]);
					docSetTo.setDefaultItemValue(docDefault[1]);

				}else {
					docSet.setDefaultItemValue("");
					docSetTo.setDefaultItemValue("");
				}

				docSetTo.setCreator(userId);
				docSetTo.setCreateDt(new Date());
				docSetTo.setUpdater(userId);
				docSetTo.setUpdateDt(new Date());

				list.add(docSet);
				list.add(docSetTo);
			} else {
				docSet.setDocumentItemNo(new BigDecimal(doc.getDocumentItemNo()[i]));
				docSet.setDefaultItemValue(doc.getDefaultVal()[i]);
				list.add(docSet);
			}
		}

		dFDDao.delDocSearchItemSetting(userId);

		if (list.size() > 0) {
			dFDDao.addDocSearchItemSetting(list);
		}

		return baseDto;
	}

	@Override
	public List<DocItemDataSourceCodeEntity> getDocSource(String docCode, String docNo) {
		return dFDDao.getDocSource(docCode, docNo);
	}

	@Override
	public List<ParmMstEntity> getDocStatus(String type1) {
		return docEnterDao.paramMst(type1);
	}

}
