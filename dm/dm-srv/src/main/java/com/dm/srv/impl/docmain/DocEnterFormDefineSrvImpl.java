package com.dm.srv.impl.docmain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.DocEnterFormDefineDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocDataSourceDto;
import com.dm.dto.DocDetailItemSettingDto;
import com.dm.dto.DocumentItemSettingDto;
import com.dm.dto.DocumentItemSettingFormDto;
import com.dm.entity.DocDetailCustomInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocDetailItemSettingEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.srv.docitem.DocEnterFormDefineSrv;

import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档数据项管理
 */
@Service
public class DocEnterFormDefineSrvImpl extends BaseSrvImp implements DocEnterFormDefineSrv {

	@Autowired
	private DocEnterFormDefineDao dEFDDao;

	@Override
	public DocumentItemSettingDto docItemSettingInit(String docType) {

		DocumentItemSettingDto disDto = new DocumentItemSettingDto();
		// 创建【未使用数据项】
		List<DocItemsEntity> defineInfo = new ArrayList<>();
		// 【左侧显示项】
		List<DocDetailItemSettingDto> left = new ArrayList<>();
		// 【右侧显示项】
		List<DocDetailItemSettingDto> right = new ArrayList<>();

		// 查询文档类型数据
		DocDataSourceDto ddsDto = dEFDDao.queryDocTypeInfo();

		// 设置下拉框以及label
		disDto.setDocDataSource(ddsDto);

		// 查询件数 = 0时,不进行后续处理。
		if (ddsDto == null || ddsDto.getDataSourceList().size() == 0) {
			disDto.setBizCode(BizCode.BIZ_CODE_RECORD_0);
			return disDto;
		}

		// 获取【处理文档类型】
		String documentType = "";
		if (docType.isEmpty()) {
			documentType = ddsDto.getDataSourceList().get(0).getItemValue();
		} else {
			documentType = docType;
		}

		// 查询【已定义数据项】
		List<DocDetailItemSettingDto> definedInfos = dEFDDao.queryDefinedInfo(documentType);

		// 查询【全部数据项】
		List<DocItemsEntity> defineInfos = dEFDDao.queryAllDefineInfo();

		// 对【全部数据项】进行循环处理，将【已定义数据项】中已使用的数据项进行删除后，放入【未使用数据项】

		// 遍历【全部数据项】
		for (int i = 0; i < defineInfos.size(); i++) {
			boolean flag = false;
			// 获取数据项
			DocItemsEntity docdefine = defineInfos.get(i);
			// 遍历【已定义数据项】， 去除【全部数据项】中的【已定义数据项】数据
			for (int j = 0; j < definedInfos.size(); j++) {
				if (docdefine.getDocumentItemCode().equals(definedInfos.get(j).getDocumentItemCode())) {
					flag = true;
				}
			}

			// 【全部数据项】中不存在 【已定义数据项】,放入【未使用数据项】
			if (!flag) {
				defineInfo.add(docdefine);
			}
		}

		// 过滤 to项目
		List<DocItemsEntity> defineInfo1 = defineInfo.stream()
				.filter(x -> !x.getDocumentItemNo().toString().equals("1")).collect(Collectors.toList());
		List<DocDetailItemSettingDto> definedInfos1 = definedInfos.stream()
				.filter(x -> !x.getDocumentItemNo().toString().equals("1")).collect(Collectors.toList());

		// 设置【未使用数据项】
		disDto.setUnDefineInfo(defineInfo1);

		// 设置空白项
		DocDetailItemSettingDto blankItem = new DocDetailItemSettingDto();
		blankItem.setDocumentItemCode(Constant.DOCUMENT_ITEM_F00000);
		blankItem.setDocumentItemNo(new BigDecimal("0"));

		for(int i = 0;i<definedInfos1.size();i++) {
			if(i == definedInfos1.size() -1) {
				break;
			}
			if(definedInfos1.get(i).getLayoutCol().equals(definedInfos1.get(i+1).getLayoutCol())) {
				if(definedInfos1.get(i).getLayoutCol().equals(new BigDecimal("1"))) {
					blankItem.setLayoutCol(new BigDecimal("2"));
				}else {
					blankItem.setLayoutCol(new BigDecimal("1"));
				}
				definedInfos1.add(i+1, blankItem);
			}

		}


		// 对【已定义数据项】进行循环处理，获取
		for (int i = 0; i < definedInfos1.size(); i++) {
			DocDetailItemSettingDto docdefined = definedInfos1.get(i);
			if ("1".equals(docdefined.getLayoutCol().toString())) {// 【左侧显示项】
				left.add(docdefined);
			} else {// 【右侧显示项】
				right.add(docdefined);
			}
		}

		// 设置【左侧显示项】
		disDto.setLeftDefinedInfo(left);
		// 设置【右侧显示项】
		disDto.setRightDefinedInfo(right);
		// 设置最终审核人
		List<DocumentItemSettingDto> disDto1 = dEFDDao.queryLastApproveUser(documentType);
		if (disDto1 != null) {
			String name = "", id = "";
			for (DocumentItemSettingDto x : disDto1) {
				if (name.length() > 0) {
					name = name.concat(",");
				}
				if (id.length() > 0) {
					id = id.concat(",");
				}
				name = name.concat(x.getUserName());
				id = id.concat(x.getLastApproveUser());
			}

			disDto.setUserName(name);
			disDto.setLastApproveUser(id);
		}
		return disDto;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto docItemSettingDefine(DocumentItemSettingFormDto docForm) {
		BaseDto baseDto = new BaseDto();

		// 查询文档录入表单定义表
		List<DocDetailItemSettingDto> docSetList = dEFDDao.queryDefinedInfo(docForm.getDocumentType());

		if (docSetList != null && docSetList.size() > 0) {

		}
		// 获取【删除文档数据项】
		List<DocDetailItemSettingDto> pageDelList = new ArrayList<>();
		// 【删除文档数据项】存在数据
		if (docSetList != null && docSetList.size() > 0) {
			// 遍历【删除文档数据项】
			for (int i = 0; i < docSetList.size(); i++) {
				DocDetailItemSettingDto docDto = docSetList.get(i);
				Boolean flag = false;
				String[] itemCode = docForm.getDocumentItemCode();
				// 遍历页面数据项编号
				for (int j = 0; j < itemCode.length; j++) {
					// 页面数据项编号 与 【删除文档数据项】相等
					if (itemCode[j].equals(docDto.getDocumentItemCode())) {
						flag = true;
						break;
					}
				}

				// 将页面上删除的文档数据项放入【删除文档数据项】
				if (!flag) {
					pageDelList.add(docDto);
				}
			}

			// 【删除文档数据项】存在数据
			if (pageDelList.size() > 0) {
				// 获取【删除固定数据项】、【删除自定义数据项】
				List<DocItemsEntity> doclist1 = dEFDDao.queryDataItem(pageDelList);
				// 过滤不存在bdName数据项
				List<DocItemsEntity> doclist = doclist1.stream()
						.filter(x -> x.getDbItemName() != null && !x.getDbItemName().isEmpty())
						.collect(Collectors.toList());
				List<DocItemsEntity> delFixList = new ArrayList<>();
				List<DocItemsEntity> delCustomList = new ArrayList<>();
				// 遍历【删除文档数据项】
				for (int j = 0; j < doclist.size(); j++) {
					DocItemsEntity doc = doclist.get(j);
					if ("1".equals(doc.getIsFixItem())) {// 【删除固定数据项】
						delFixList.add(doc);
					} else {// 【删除自定义数据项】
						delCustomList.add(doc);
					}
				}

				// 查询【删除固定数据项】是否有录入
				if (delFixList.size() > 0) {
					List<DocDetailInfoEntity> getDelFixList = dEFDDao.getDelFixList(delFixList);
					// 查询数据件数 > 0，并且查询的数据不为空时
					if (getDelFixList != null && getDelFixList.size() > 0) {
						for (int j = 0; j < getDelFixList.size(); j++) {
							if (getDelFixList.get(j) != null) {
								baseDto.setBizCode(BizCode.BIZ_CODE_DOC_DATA_ITEM_ALREADY_EXISTED);
								return baseDto;
							}
						}
					}
				}

				// 查询【删除自定义数据项】是否有录入
				if (delCustomList.size() > 0) {
					List<DocDetailCustomInfoEntity> getDelCustomList = dEFDDao.getDelCustomList(delCustomList);
					// 查询数据件数 > 0，并且查询的数据不为空时
					if (getDelCustomList != null && getDelCustomList.size() > 0) {
						for (int j = 0; j < getDelCustomList.size(); j++) {
							if (getDelCustomList.get(j) != null) {
								baseDto.setBizCode(BizCode.BIZ_CODE_DOC_DATA_ITEM_ALREADY_EXISTED);
								return baseDto;
							}
						}
					}
				}
			}

		}

		// 删除文档录入表单定义表
		dEFDDao.delDocDetailItemSettingByDoctype(docForm.getDocumentType());

		// 获取文档录入表单信息
		List<DocDetailItemSettingEntity> docSettingList = new ArrayList<>();
		if (docForm.getDocumentItemCode().length > 0) {
			for (int j = 0; j < docForm.getDocumentItemCode().length; j++) {
				// 去除空白项
				if ("DOCUMENT_ITEM_F00000".equals(docForm.getDocumentItemCode()[j])) {
					continue;
				}
				DocDetailItemSettingEntity docSetting = new DocDetailItemSettingEntity();
				docSetting.setDocumentType(docForm.getDocumentType());
				docSetting.setDocumentItemCode((docForm.getDocumentItemCode())[j]);
				docSetting.setDocumentItemNo(new BigDecimal((docForm.getDocumentItemNo())[j]));
				docSetting.setLayoutRow(new BigDecimal((docForm.getLayoutRow())[j]));
				docSetting.setLayoutCol(new BigDecimal((docForm.getLayoutCol())[j]));
				docSetting.setInputRequire(new BigDecimal((docForm.getInputRequire())[j]));
				docSetting.setCreator(docForm.getCreator());
				docSettingList.add(docSetting);

				// fromto 项目添加 to项目
				if ((docForm.getIsFromToItem())[j].equals("1")) {
					DocDetailItemSettingEntity docSetting1 = new DocDetailItemSettingEntity();
					docSetting1.setDocumentType(docForm.getDocumentType());
					docSetting1.setDocumentItemCode((docForm.getDocumentItemCode())[j]);
					docSetting1.setDocumentItemNo(new BigDecimal("1"));
					docSetting1.setLayoutRow(new BigDecimal((docForm.getLayoutRow())[j]));
					docSetting1.setLayoutCol(new BigDecimal((docForm.getLayoutCol())[j]));
					docSetting1.setInputRequire(new BigDecimal((docForm.getInputRequire())[j]));
					docSetting1.setCreator(docForm.getCreator());
					docSettingList.add(docSetting1);
				}
			}

			// 插入文档录入表单定义表
			dEFDDao.addDocDetailItemSettingByDoctype(docSettingList);

		}

		// 删除文档审核管理表
		dEFDDao.delApproveManager(docForm.getDocumentType());

		// 对画面上选择的最终审核人员进行循环处理，插入文档审核管理表
		dEFDDao.addApproveManager(docForm.getLastApprovalUserId(), docForm.getDocumentType(), docForm.getCreator());

		// 返回成功信息

		return baseDto;
	}

}
