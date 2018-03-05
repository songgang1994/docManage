package com.dm.srv.impl.doc;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.DocEnterDao;
import com.dm.dao.DocSearchDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocCodeLinkDto;
import com.dm.dto.DocSearchDto;
import com.dm.dto.LoginDto;
import com.dm.dto.TableDto;
import com.dm.dto.TableSearchDto;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.srv.doc.DocSearchSrv;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;
import com.dm.tool.StringUtil;

@Service
public class DocSearchSrvImpl extends BaseSrvImp implements DocSearchSrv {

	@Autowired
	private DocSearchDao docSearchDao;
	@Autowired
	private DocEnterDao docDao;

	@Override
	public Map<String, Object> getQueryDataItemByUserId(String UserId) {

		List<DocSearchDto> userDefaultliList = new ArrayList<>();
		Map<String, Object> ret = new HashMap<>();

		// 【用户自定义查询数据项】
		userDefaultliList = docSearchDao.getQueryDataItemByUserId(UserId);
		// 【用户自定义查询数据项】为空时，【用户自定义查询数据项】 = 【系统默认查询数据项】
		if (userDefaultliList.size() == 0) {
			// 【系统默认查询数据项】
			userDefaultliList = docSearchDao.getQueryDataItemByUserId(Constant.USER_ID);
		}

		List<DocSearchDto> queryList = new ArrayList<>();
		List<DocSearchDto> itemList = new ArrayList<>();
		userDefaultliList.forEach(x -> {
			// 获取查询项目
			if ("1".equals(x.getIsSearchItem().toString())) {
				queryList.add(x);
			}
			// 获取一览项目
			if ("1".equals(x.getIsListItem().toString())) {
				itemList.add(x);
			}
		});

		// 将上述查询结果中数据源编号不为空的数据源编号放入【数据源编号】，查询数据源信息
		List<String> ds = queryList.stream()
				.filter(x -> x.getDocumentItemSourceCode() != null && x.getDocumentItemSourceCode().trim().length() > 0)
				.map(x -> x.getDocumentItemSourceCode()).collect(Collectors.toList());
		if (ds.isEmpty()) {
			ret.put("DSList", null);
		} else {

			List<DocItemDataSourceCodeEntity> dsList = docSearchDao.getDataSourceByCode(queryList);
			Map<String, List<DocItemDataSourceCodeEntity>> groups = dsList.stream()
					.collect(Collectors.groupingBy(DocItemDataSourceCodeEntity::getDocumentItemSourceCode));
			ret.put("DSList", dsList);

			queryList.forEach(x -> {
				if (!StringUtil.isEmpty(x.getDocumentItemSourceCode())) {
					x.setDataSourceList(groups.get(x.getDocumentItemSourceCode()));
				}
			});
		}

		// 设置无数据项项目
		queryList.forEach(x -> {
			if (x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00008)) {// 文档状态
				x.setParamMstList(docDao.paramMst(Constant.DOC_STATUS));
			}

		});

		queryList.forEach(x -> {
			switch (x.getDocumentItemType()) {
			// case Constant.ITEM_TYPE_SELECT:
			// break;
			// case Constant.ITEM_TYPE_CHECKBOX:
			//
			// break;
			// case Constant.ITEM_TYPE_RADIO:
			// break;
			case Constant.ITEM_TYPE_POP_USER:
				if (x.getDocumentItemType() != null && !x.getDocumentItemType().isEmpty()) {
					x.setValue(x.getDefaultItemValue().substring(x.getDefaultItemValue().indexOf("#") + 1));
				}
				break;
			case Constant.ITEM_TYPE_POP_DEPT:
				if (x.getDocumentItemType() != null && !x.getDocumentItemType().isEmpty()) {
					x.setValue(x.getDefaultItemValue().substring(x.getDefaultItemValue().indexOf("#") + 1));
				}
				break;
			case Constant.ITEM_TYPE_POP_USER_MULT:
				if (x.getDefaultItemValue() != null && !x.getDefaultItemValue().isEmpty()) {
					String[] sArray = x.getDefaultItemValue().split(",");
					String s = "";
					for (String str : Arrays.asList(sArray)) {
						s = s.concat(str.substring(str.indexOf("#") + 1)).concat(",");
					}
					if (s.lastIndexOf(",") == s.length() - 1) {
						x.setValue(s.substring(0, s.length() - 1));
					} else {
						x.setValue(s);
					}

				}
				break;
			case Constant.ITEM_TYPE_POP_DEPT_MULT:
				if (x.getDefaultItemValue() != null && !x.getDefaultItemValue().isEmpty()) {
					String[] sArray = x.getDefaultItemValue().split(",");
					String s = "";
					for (String str : Arrays.asList(sArray)) {
						s = s.concat(str.substring(str.indexOf("#") + 1)).concat(",");
					}
					if (s.lastIndexOf(",") == s.length() - 1) {
						x.setValue(s.substring(0, s.length() - 1));
					} else {
						x.setValue(s);
					}

				}
				break;
			default:
				// 默认值设置
				x.setValue(x.getDefaultItemValue());
				break;
			}

		});

		// 非 From To 项目 和 From 项目
		List<DocSearchDto> normalItems = queryList.stream()
				.filter(x -> x.getIsFromToItem() == null || x.getDocumentItemNo().intValue() == 0)
				.collect(Collectors.toList());

		// To 项目过滤
		Map<String, List<DocSearchDto>> extraItems = queryList.stream()
				.filter(x -> x.getIsFromToItem() != null && x.getDocumentItemNo().intValue() >= 1)
				.collect(Collectors.groupingBy(x -> x.getDocumentItemCode()));

		ret.put("formItems", normalItems);
		ret.put("extraItems", extraItems);

		// 查询和一览项目
		ret.put("listItems1", itemList);
		ret.put("userCustomItems", userDefaultliList);

		// 去除重复FromTo项目、范围查询数据项、数据项
		for (int i = 0; i < queryList.size() - 1; i++) {
			for (int j = queryList.size() - 1; j > i; j--) {
				if (queryList.get(j).getDocumentItemCode().equals(queryList.get(i).getDocumentItemCode())) {
					queryList.remove(j);
				}
			}
		}

		for (int i = 0; i < itemList.size() - 1; i++) {
			for (int j = itemList.size() - 1; j > i; j--) {
				if (itemList.get(j).getDocumentItemCode().equals(itemList.get(i).getDocumentItemCode())) {
					String newDbItemName = itemList.get(i).getDbItemName().concat("+ '~' + ")
							.concat(itemList.get(j).getDbItemName());
					itemList.remove(j);
					itemList.get(i).setDbItemName(newDbItemName);
				}
			}
		}

		ret.put("listItems", itemList);
		return ret;
	}

	@Override
	public TableDto getTableData(List<DocSearchDto> listItems, List<DocSearchDto> userCustomItems, String userId)
			throws Exception {
		StringBuilder selectSql = new StringBuilder("");
		StringBuilder querySql = null;
		List<Integer> indexList = new ArrayList<>();// 用于人员检索Popup(多选）
		// 获取固定检索字段SQL
		selectSql.append("'DOCUMENT_CODE' AS ITEM_KEY1,")// 文档编号
				.append("DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AS ITEM_VAL1,").append("APPROVAL_STATUS AS ITEM_KEY2,")// 文档状态
				.append("(SELECT DISP_NAME FROM PARM_MST WHERE PARM_MST.TYPE1 = '").append(Constant.DOC_STATUS)
				.append("' AND PARM_MST.VALUE = DOCUMENT_DETAIL_INFO.APPROVAL_STATUS) AS ITEM_VAL2,")
				.append("DIRECTOR AS ITEM_KEY3,")// 责任人
				.append("(SELECT USER_NAME FROM SYS_USER WHERE SYS_USER.USER_ID = DOCUMENT_DETAIL_INFO.DIRECTOR) AS ITEM_VAL3,")
				.append("(SELECT '['+DI.DEPT_ID +','+[DI].DEPT_NAME+']' FROM DOCUMENT_VIEW_DEPT DVD,M_DEPARTMENT_INFO DI WHERE DVD.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND DI.DEPT_ID = DVD.DEPT_ID FOR XML PATH('')) AS ITEM_KEY4,")// 文档可查看部门
				.append("(SELECT '['+DI.DEPT_ID +','+[DI].DEPT_NAME+']' FROM DOCUMENT_VIEW_DEPT DVD,M_DEPARTMENT_INFO DI WHERE DVD.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND DI.DEPT_ID = DVD.DEPT_ID FOR XML PATH('')) AS ITEM_VAL4,");

		int index = 5;// 一览数据开始
		for (DocSearchDto x : listItems) {

			if (Constant.DOCUMENT_ITEM_F00001.equals(x.getDocumentItemCode())) {// 文档编号
				selectSql.append("'DOCUMENT_CODE' AS ITEM_KEY").append(String.valueOf(index)).append(",")// 文档编号
						.append("DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AS ITEM_VAL").append(String.valueOf(index))
						.append(",");
			} else if (Constant.DOCUMENT_ITEM_F00008.equals(x.getDocumentItemCode())) {// 文档状态
				selectSql.append("APPROVAL_STATUS AS ITEM_KEY").append(String.valueOf(index)).append(",")// 文档状态
						.append("(SELECT DISP_NAME FROM PARM_MST WHERE PARM_MST.TYPE1 = '").append(Constant.DOC_STATUS)
						.append("' AND PARM_MST.VALUE = DOCUMENT_DETAIL_INFO.APPROVAL_STATUS) AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else if (Constant.DOCUMENT_ITEM_F00005.equals(x.getDocumentItemCode())) { // 责任人
				selectSql.append("DIRECTOR AS ITEM_KEY").append(String.valueOf(index)).append(",")// 责任人
						.append("(SELECT USER_NAME FROM SYS_USER WHERE SYS_USER.USER_ID = DOCUMENT_DETAIL_INFO.DIRECTOR) AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else if (Constant.DOCUMENT_ITEM_F00009.equals(x.getDocumentItemCode())) {// 文档可查看部门
				selectSql.append(
						"(SELECT '['+DI.DEPT_ID +','+[DI].DEPT_NAME+']' FROM DOCUMENT_VIEW_DEPT DVD,M_DEPARTMENT_INFO DI WHERE DVD.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND DI.DEPT_ID = DVD.DEPT_ID FOR XML PATH('')) AS ITEM_KEY")
						.append(String.valueOf(index)).append(",")// 文档可查看部门
						.append("(SELECT '['+DI.DEPT_ID +','+[DI].DEPT_NAME+']' FROM DOCUMENT_VIEW_DEPT DVD,M_DEPARTMENT_INFO DI WHERE DVD.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND DI.DEPT_ID = DVD.DEPT_ID FOR XML PATH('')) AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else if (Constant.ITEM_TYPE_FILE_SINGLE.equals(x.getDocumentItemType())) {
				// 主文件
				selectSql.append(" 'PARENT_FILE' AS ITEM_KEY").append(String.valueOf(index)).append(",").append(
						" ( SELECT CAST(MF.FILE_NO AS VARCHAR)+','+MF.FILE_NAME FROM DOCUMENT_FILE MF WHERE MF.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND MF.FILE_TYPE='")
						.append(Constant.FILE_TYPE_PARENT).append("') AS ITEM_VAL").append(String.valueOf(index))
						.append(",");
			} else if (Constant.ITEM_TYPE_FILE_MULT.equals(x.getDocumentItemType())) {
				// 子文件
				selectSql.append(" 'CHILD_FILE' AS ITEM_KEY").append(String.valueOf(index)).append(",").append(
						"( SELECT '['+CAST(SF.FILE_NO AS VARCHAR) +','+SF.FILE_NAME+']' FROM DOCUMENT_FILE SF WHERE SF.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND SF.FILE_TYPE='")
						.append(Constant.FILE_TYPE_CHILD).append("' FOR XML PATH('')) AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else if ("1".equals(x.getIsFixItem()) && (Constant.DOCUMENT_ITEM_F00004.equals(x.getDocumentItemCode())
					|| Constant.DOCUMENT_ITEM_F000011.equals(x.getDocumentItemCode()))) {// 固定项目
				// 担当部门
				if (Constant.DOCUMENT_ITEM_F00004.equals(x.getDocumentItemCode())) {
					selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY")
							.append(String.valueOf(index)).append(",")
							.append("(SELECT DEPT_NAME FROM M_DEPARTMENT_INFO WHERE M_DEPARTMENT_INFO.DEPT_ID = DOCUMENT_DETAIL_INFO.DEPT_ID) AS ITEM_VAL")
							.append(String.valueOf(index)).append(",");
				} else {
					selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY")
							.append(String.valueOf(index)).append(",")
							.append("(SELECT DEPT_NAME FROM M_DEPARTMENT_INFO WHERE M_DEPARTMENT_INFO.DEPT_ID = DOCUMENT_DETAIL_INFO.APPROVAL_DEPT_ID) AS ITEM_VAL")
							.append(String.valueOf(index)).append(",");
				}

				// }
			} else if (x.getDocumentItemSourceCode() != null) {
				if (x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00003)) {// 文档类型
					selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY")
							.append(String.valueOf(index)).append(",")
							.append("(SELECT ITEM_NAME FROM DOCUMENT_ITEM_DATA_SOURCE_CODE WHERE DOCUMENT_ITEM_DATA_SOURCE_CODE.DOCUMENT_ITEM_SOURCE_CODE = '")
							.append(x.getDocumentItemSourceCode())
							.append("' AND DOCUMENT_ITEM_DATA_SOURCE_CODE.ITEM_VALUE = DOCUMENT_DETAIL_INFO.DOCUMENT_TYPE")
							.append(") AS ITEM_VAL").append(String.valueOf(index)).append(",");
				} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_RADIO)
						|| x.getDocumentItemType().equals(Constant.ITEM_TYPE_SELECT)) {// 文档数据项．数据源编号不为空
					// 单选框 下拉列表
					selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY")
							.append(String.valueOf(index)).append(",")
							.append("(SELECT ITEM_NAME FROM DOCUMENT_ITEM_DATA_SOURCE_CODE WHERE DOCUMENT_ITEM_DATA_SOURCE_CODE.DOCUMENT_ITEM_SOURCE_CODE = '")
							.append(x.getDocumentItemSourceCode())
							.append("' AND DOCUMENT_ITEM_DATA_SOURCE_CODE.ITEM_VALUE = DOCUMENT_DETAIL_CUSTOME_INFO.")
							.append(x.getDbItemName()).append(") AS ITEM_VAL").append(String.valueOf(index))
							.append(",");
				} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_CHECKBOX)) {// 复选框
					selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY")
							.append(String.valueOf(index)).append(",")
							.append("(SELECT ITEM_NAME+',' FROM DOCUMENT_ITEM_DATA_SOURCE_CODE WHERE DOCUMENT_ITEM_DATA_SOURCE_CODE.DOCUMENT_ITEM_SOURCE_CODE = '")
							.append(x.getDocumentItemSourceCode())
							.append("' AND CHARINDEX(DOCUMENT_ITEM_DATA_SOURCE_CODE.ITEM_VALUE,DOCUMENT_DETAIL_CUSTOME_INFO.")
							.append(x.getDbItemName()).append(") != 0 FOR XML PATH('')").append(") AS ITEM_VAL")
							.append(String.valueOf(index)).append(",");
				}

			} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_POP_USER)) {// 人员检索
				selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY").append(String.valueOf(index))
						.append(",")
						.append("(SELECT USER_NAME FROM SYS_USER WHERE SYS_USER.USER_ID = DOCUMENT_DETAIL_CUSTOME_INFO.")
						.append(x.getDbItemName()).append(") AS ITEM_VAL").append(String.valueOf(index)).append(",");
			} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_POP_DEPT)) {// 部门检索
				selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY").append(String.valueOf(index))
						.append(",")
						.append("(SELECT DEPT_NAME FROM M_DEPARTMENT_INFO WHERE M_DEPARTMENT_INFO.DEPT_ID = DOCUMENT_DETAIL_CUSTOME_INFO.")
						.append(x.getDbItemName()).append(") AS ITEM_VAL").append(String.valueOf(index)).append(",");
			} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_POP_DEPT_MULT)) {// 部门检索(多选)
				selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY").append(String.valueOf(index))
						.append(",")
						.append("(SELECT DEPT_NAME+',' FROM M_DEPARTMENT_INFO WHERE CHARINDEX(M_DEPARTMENT_INFO.DEPT_ID,DOCUMENT_DETAIL_CUSTOME_INFO.")
						.append(x.getDbItemName()).append(") != 0 FOR XML PATH('')").append(") AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_POP_USER_MULT)) {// 人员检索(多选)
				selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY").append(String.valueOf(index))
						.append(",")
						.append("(SELECT SYS_USER.USER_NAME +',' FROM SYS_USER WHERE CHARINDEX(SYS_USER.USER_ID,DOCUMENT_DETAIL_CUSTOME_INFO.")
						.append(x.getDbItemName()).append(") != 0 FOR XML PATH('')").append(") AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
				// 由于用户信息数据量比较大，放入SQL中对效率影响比较大，因此人员名称在结果查询结束后在进行设置
				indexList.add(index);
			} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_FILE_SINGLE)) {// 实际业务没有需求，不用考虑

			} else if (x.getDocumentItemType().equals(Constant.ITEM_TYPE_FILE_MULT)) {// 实际业务没有需求，不用考虑

			} else if ("1".equals(x.getIsFixItem())) {
				selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY").append(String.valueOf(index))
						.append(",").append("DOCUMENT_DETAIL_INFO.").append(x.getDbItemName()).append(" AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else if ("1".equals(x.getIsFromToItem().toString())) {
				selectSql.append("'").append(x.getDbItemName().split("'")[0]).append("' AS ITEM_KEY")
						.append(String.valueOf(index)).append(",").append(x.getDbItemName()).append(" AS ITEM_VAL")
						.append(String.valueOf(index)).append(",");
			} else {// 其他
				selectSql.append("'").append(x.getDbItemName()).append("' AS ITEM_KEY").append(String.valueOf(index))
						.append(",").append(x.getDbItemName()).append(" AS ITEM_VAL").append(String.valueOf(index))
						.append(",");
			}

			index++;
		}

		// 设置查询字段
		List<List<DocSearchDto>> lists = new ArrayList<>();
		List<DocSearchDto> docList = null;

		// 按照数据项编号进行分类，将相同的数据项放入同一个列表
		List<DocSearchDto> tdocList = new ArrayList<>();
		tdocList.addAll(userCustomItems);
		for (int i = 0; i < userCustomItems.size(); i++) {
			docList = new ArrayList<DocSearchDto>();
			for (int j = 0; j < tdocList.size(); j++) {
				// 是否查询项目 = 0：否时，不进行处理
				if (userCustomItems.get(i).getDocumentItemCode().equals(tdocList.get(j).getDocumentItemCode())
						&& userCustomItems.get(i).getIsSearchItem().toString().equals("1")) {
					docList.add(tdocList.get(j));
					tdocList.remove(j);
					j--;
				}
			}
			if (docList.size() > 0) {
				lists.add(docList);
			}
		}
		// lists 已过滤掉查询项目 = 0数据

		// 设置数据项所在的DB表名
		List<String> dbTableName = new ArrayList<>();

		// 用于拼接sql
		List<String> pFile = new ArrayList<>();
		List<String> cFile = new ArrayList<>();
		List<String> docRead = new ArrayList<>();
		List<String> docitem = new ArrayList<>();
		List<String> fromto = new ArrayList<>();
		List<String> mul = new ArrayList<>();

		if (lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				StringBuilder dbName = new StringBuilder("");
				List<DocSearchDto> x = lists.get(i);

				// 默认值为空，不设置查询条件

				if ("DOCUMENT_ITEM_F00006".equals(x.get(0).getDocumentItemCode())) {// 主文件
					dbName.append("DOCUMENT_ITEM_F00006");
				} else if ("DOCUMENT_ITEM_F00007".equals(x.get(0).getDocumentItemCode())) {// 子文件
					dbName.append("DOCUMENT_ITEM_F00007");
				} else if ("DOCUMENT_ITEM_F00009".equals(x.get(0).getDocumentItemCode())) {// 文档可查看部门
					dbName.append("DOCUMENT_ITEM_F00009");
				} else if ("1".equals(x.get(0).getIsFixItem().toString())) {// 文档管理表
					dbName.append("DOCUMENT_DETAIL_INFO");
					dbTableName.add(dbName.toString());
				} else {// 文档自定义数据项表
					dbName.append("DOCUMENT_DETAIL_CUSTOME_INFO");
					dbTableName.add(dbName.toString());
				}
				// 是否FROM_TO项目 = 1：是
				if (x.get(0).getIsFromToItem().toString().equals("1")) {
					querySql = new StringBuilder("");
					// fromTo 排序
					List<DocSearchDto> z = x.stream().sorted(Comparator.comparing(DocSearchDto::getDocumentItemNo))
							.collect(Collectors.toList());
					querySql.append(" ( ");
					if (z.get(0).getDefaultItemValue().toString() != null && !z.get(0).getDefaultItemValue().toString().isEmpty()) {
						querySql.append(dbName).append(".").append(z.get(0).getDbItemName()).append(" >='")
								.append(z.get(0).getDefaultItemValue()).append("' AND ");
					}
					if (z.get(1).getDefaultItemValue().toString() != null && !z.get(1).getDefaultItemValue().toString().isEmpty()) {
						querySql.append(dbName).append(".").append(z.get(0).getDbItemName()).append(" <='")
								.append(z.get(1).getDefaultItemValue()).append("' AND");
					}
					querySql.append(" 1=1 ) AND ");
					// 添加至fromTo
					fromto.add(querySql.toString());
					lists.remove(x);
					i--;
				}

			}

			// 处理结束后，如果列表的长度 > 0时，继续对其进行循环处理
			if (lists.size() > 0) {
				for (List<DocSearchDto> li : lists) {
					querySql = new StringBuilder("");
					for (DocSearchDto x : li) {

						// 默认值为空，不设置查询条件
						if(x.getDefaultItemValue() ==null || x.getDefaultItemValue().isEmpty()) {
							continue;
						}

						// 设置数据项所在的DB表名
						StringBuilder dbName = new StringBuilder("");
						if ("DOCUMENT_ITEM_F00006".equals(x.getDocumentItemCode())) {// 主文件
							dbName.append("DOCUMENT_ITEM_F00006");
						} else if ("DOCUMENT_ITEM_F00007".equals(x.getDocumentItemCode())) {// 子文件
							dbName.append("DOCUMENT_ITEM_F00007");
						} else if ("DOCUMENT_ITEM_F00009".equals(x.getDocumentItemCode())) {// 文档可查看部门
							dbName.append("DOCUMENT_ITEM_F00009");
						} else if ("1".equals(x.getIsFixItem().toString())) {// 文档管理表
							dbName.append("DOCUMENT_DETAIL_INFO");
							dbTableName.add(dbName.toString());
						} else {// 文档自定义数据项表
							dbName.append("DOCUMENT_DETAIL_CUSTOME_INFO");
							dbTableName.add(dbName.toString());
						}

						if (dbName.toString().equals(Constant.DOCUMENT_ITEM_F00006)) {// 主文件
							if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_EQUAL)) {
								querySql.append(" WMF.FILE_NAME = '").append(x.getDefaultItemValue()).append("' ");
							} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_LIKE)) {
								querySql.append(" WMF.FILE_NAME LIKE '%").append(x.getDefaultItemValue()).append("%'");
							}
							pFile.add(querySql.append(" AND ").toString());
						} else if (dbName.toString().equals(Constant.DOCUMENT_ITEM_F00007)) {// 子文件
							if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_EQUAL)) {
								querySql.append(" WMF.FILE_NAME = '").append(x.getDefaultItemValue()).append("' ");
								;
							} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_LIKE)) {
								querySql.append(" WMF.FILE_NAME LIKE '%").append(x.getDefaultItemValue()).append("%'");
							}
							cFile.add(querySql.append(" AND ").toString());
						} else if (dbName.toString().equals(Constant.DOCUMENT_ITEM_F00009)) {// 文档可见部门管理表
							if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_EQUAL)) {
								querySql.append(" WDV.DEPT_ID = ").append(matchingIn(x.getDefaultItemValue()))
										.append(" ");
								;
							} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_IN)) {
								querySql.append(" WDV.DEPT_ID IN (").append(matchingIn(x.getDefaultItemValue()))
										.append(")");
							}
							docRead.add(querySql.append(" AND ").toString());
						} else if (Constant.ITEM_TYPE_CHECKBOX.equals(x.getDocumentItemType())
								|| Constant.ITEM_TYPE_POP_DEPT.equals(x.getDocumentItemType())
								|| Constant.ITEM_TYPE_POP_DEPT_MULT.equals(x.getDocumentItemType())) {
							String[] defaultValArray = null;
							if (x.getDefaultItemValue() != null && !x.getDefaultItemValue().isEmpty()) {
								defaultValArray = x.getDefaultItemValue().split(",");
								if (defaultValArray.length > 0) {
									querySql.append("(");
									for (String s : defaultValArray) {
										if ("DOCUMENT_DETAIL_INFO".equals(dbName.toString())) {
											querySql.append("CHARINDEX('").append(s.substring(0, s.indexOf("#")))
													.append("',").append(dbName).append(".").append(x.getDbItemName())
													.append(") != 0 OR ");

										} else {
											querySql.append("CHARINDEX('").append(s).append("',").append(dbName)
													.append(".").append(x.getDbItemName()).append(") != 0 OR ");
										}

									}
									// 删除最后一个OR
									querySql.delete(querySql.length() - 3, querySql.length());
									querySql.append(")");
								}
								mul.add(querySql.append(" AND ").toString());
							}

						} else {// 设置数据项的条件SQL
							// 去除无dbName项目，记录该项目用于显示
							if (!x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00006)
									&& !x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00007)
									&& !x.getDocumentItemCode().equals(Constant.DOCUMENT_ITEM_F00009)) {
								querySql.append(" ").append(dbName).append(".").append(x.getDbItemName());
								StringBuilder s = new StringBuilder(x.getDefaultItemValue());
								if ("DOCUMENT_DETAIL_INFO".equals(dbName.toString())) {
									if (x.getDefaultItemValue().indexOf("#") > 0) {
										s.delete(0, s.length()).append(x.getDefaultItemValue().substring(0,
												x.getDefaultItemValue().indexOf("#")));
									}
								}
								if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_EQUAL)) {
									querySql.append(" = '").append(s.toString()).append("' ");
								} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_LIKE)) {
									querySql.append(" LIKE '%").append(s.toString()).append("%' ");
								} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_GREATER_OR_EQUAL)) {
									querySql.append(" >= '").append(s.toString()).append("' ");
								} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_LESS_OR_EQUAL)) {
									querySql.append(" <= '").append(s.toString()).append("' ");
								} else if (x.getMatching().toString().equals(Constant.MATCHING_TYPE_IN)) {
									querySql.append(" IN (").append(matchingIn(s.toString())).append(") ");
									;
								}
								docitem.add(querySql.append(" AND ").toString());
							}

						}

					}

				}
			}
		}

		StringBuilder querySQL = new StringBuilder("");

		if (fromto.size() > 0) {
			if (querySQL.length() > 0) {
				querySQL.append(" AND ");
			}
			for (String str : fromto) {
				querySQL.append(str);
			}
			// 删除最后一个AND

			querySQL.delete(querySQL.length() - 4, querySQL.length());

		}
		if (pFile.size() > 0) {
			if (querySQL.length() > 0) {
				querySQL.append(" AND ");
			}
			querySQL.append(
					" EXISTS ( SELECT 1 FROM DOCUMENT_FILE WMF WHERE WMF.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND WMF.FILE_TYPE='1' AND (");
			for (String str : pFile) {
				querySQL.append(str);
			}
			// 删除最后一个AND

			querySQL.delete(querySQL.length() - 4, querySQL.length());

			querySQL.append(" ) )");

		}
		if (cFile.size() > 0) {
			if (querySQL.length() > 0) {
				querySQL.append(" AND ");
			}
			querySQL.append(
					" EXISTS ( SELECT 1 FROM DOCUMENT_FILE WMF WHERE WMF.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND WMF.FILE_TYPE='2' AND (");
			for (String str : cFile) {
				querySQL.append(str);
			}
			// 删除最后一个AND

			querySQL.delete(querySQL.length() - 4, querySQL.length());

			querySQL.append(" ) )");
		}
		if (docRead.size() > 0) {
			if (querySQL.length() > 0) {
				querySQL.append(" AND ");
			}
			querySQL.append(
					" EXISTS ( SELECT 1 FROM DOCUMENT_VIEW_DEPT WDV WHERE WDV.DOCUMENT_CODE=DOCUMENT_DETAIL_INFO.DOCUMENT_CODE AND (");
			for (String str : docRead) {
				querySQL.append(str);
			}
			// 删除最后一个AND

			querySQL.delete(querySQL.length() - 4, querySQL.length());

			querySQL.append(" ) )");
		}
		if (docitem.size() > 0) {
			if (querySQL.length() > 0) {
				querySQL.append(" AND ");
			}
			querySQL.append("(");
			for (String str : docitem) {
				querySQL.append(str);
			}
			// 删除最后一个AND

			querySQL.delete(querySQL.length() - 4, querySQL.length());

			querySQL.append(")");
		}
		if (mul.size() > 0) {
			if (querySQL.length() > 0) {
				querySQL.append(" AND ");
			}
			querySQL.append("(");
			for (String str : mul) {
				querySQL.append(str);
			}
			// 删除最后一个AND

			querySQL.delete(querySQL.length() - 4, querySQL.length());

			querySQL.append(")");
		}

		// 去除selectSql最后一个，
		int selectIndex = selectSql.lastIndexOf(",");
		if (selectIndex == selectSql.length() - 1) {
			selectSql.delete(selectIndex, selectSql.length());
		}

		// 查询sql
		List<TableSearchDto> query = docSearchDao.query(selectSql.toString(), querySQL.toString(), userId);
		// 数据处理

		// 获取存在值的列
		String[][] tableKey = new String[query.size()][index - 1];// key
		String[][] tableVal = new String[query.size()][index - 1];// value
		for (int i = 0; i < query.size(); i++) {
			TableSearchDto ta = query.get(i);
			Class<?> tableClass = ta.getClass();
			for (int j = 1; j < index; j++) {
				// 调用get方法
				Method method = tableClass.getMethod("getItemKey" + String.valueOf(j));
				if ((String) method.invoke(ta) != null) {
					tableKey[i][j - 1] = (String) method.invoke(ta);

					Method method1 = tableClass.getMethod("getItemVal" + String.valueOf(j));
					tableVal[i][j - 1] = (String) method1.invoke(ta);

				}

			}
		}

		// 删除数组元素为null的元素

		// 前四项为必须查找项目，key依次为：文档编号，文档状态，责任人，文档可查看部门，val为页面显示名称F01-17-00001, 3, 3, null,
		TableDto lines = new TableDto();
		lines.setTableKey(tableKey);
		lines.setTable(tableVal);
		lines.setUserId(userId);
		lines.setDepts(docSearchDao.getDeptIdByUserId(userId));

		return lines;
	}

	@Override
	public DocCodeLinkDto handleDocCode(TableDto table, String line) {
		DocCodeLinkDto baseDto = new DocCodeLinkDto();
		boolean flag = checkDeptIsInDocDepts(table, line);
		if (!flag) {
			baseDto.setBizCode(BizCode.BIZ_CODE_READ_DOC);
			return baseDto;
		}
		// 设置文档编号
		baseDto.setDocCode(table.getTable()[Integer.parseInt(line)][0]);
		// 设置文档状态
		String docStatus = table.getTableKey()[Integer.parseInt(line)][1];
		if (docStatus.equals("1")) {
			baseDto.setMode("1");
		} else {
			baseDto.setMode("0");
		}
		return baseDto;
	}

	@Override
	public BaseDto handleFile(TableDto table, String line, String fileNo) {
		DocFileEntity baseDto = new DocFileEntity();
		boolean flag = checkDeptIsInDocDepts(table, line);
		if (!flag) {
			baseDto.setBizCode(BizCode.BIZ_CODE_READ_DOC);
		}
		return baseDto;
	}

	@Override
	public BaseDto handleEdit(TableDto table, String line, String userId) {
		DocCodeLinkDto baseDto = new DocCodeLinkDto();
		boolean flag = checkIsEditOrDel(table, line, userId, true);
		if (!flag) {
			baseDto.setBizCode(BizCode.BIZ_CODE_EDIT_DOC_AUTHORITY);
			return baseDto;
		}
		// 设置文档编号
		baseDto.setDocCode(table.getTable()[Integer.parseInt(line)][0]);
		// 设置文档状态
		baseDto.setMode("1");
		return baseDto;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto handleDelete(TableDto table, String line, String userId) {
		DocCodeLinkDto baseDto = new DocCodeLinkDto();
		boolean flag = checkIsEditOrDel(table, line, userId, false);
		if (!flag) {
			baseDto.setBizCode(BizCode.BIZ_CODE_DELETE_DOC_AUTHORITY);
			return baseDto;
		}

		// 获取文档编号
		String docCode = table.getTable()[Integer.parseInt(line)][0];
		// 删除文档管理表
		docSearchDao.delDocDetailInfo(docCode);
		// 删除文档自定义数据项表
		docSearchDao.delDocCustom(docCode);
		// 删除文档文件表
		docSearchDao.delDocFile(docCode);
		// 删除文档可见部门管理表
		docSearchDao.delDocViewDept(docCode);

		return baseDto;
	}

	@Override
	public DocFileEntity handleExport(TableDto tableDto, List<DocSearchDto> listItems, LoginDto user) throws Exception {
		DocFileEntity baseDto = new DocFileEntity();

		List<Row> rows = new ArrayList<>();
		String[][] table = tableDto.getTable();
		// 获取总列数
		int CountColumnNum = listItems.size() + 1;
		// 创建Excel文档
		HSSFWorkbook hwb = new HSSFWorkbook();
		// sheet 对应一个工作页
		HSSFSheet sheet = hwb.createSheet();

		// 表头
		HSSFRow row = null;
		row = sheet.createRow(0);
		for (int j = 0; j < CountColumnNum; j++) {
			// 在一行内循环
			HSSFCell x = row.createCell(j);
			if (j == 0) {
				x.setCellValue(new HSSFRichTextString("No."));
			} else {
				x.setCellValue(new HSSFRichTextString(listItems.get(j - 1).getDocumentItemName()));
			}

		}
		// 行添加
		rows.add(row);

		// 查询结果数据处理
		for (int i = 0; i < table.length; i++) {
			// 创建一行
			row = sheet.createRow(i + 1);
			for (int j = 0; j < CountColumnNum; j++) {

				// 在一行内循环
				HSSFCell x = row.createCell(j);
				if (j == 0) {
					x.setCellValue(new HSSFRichTextString(String.valueOf(i + 1)));
				} else {
					// 数据处理

					x.setCellValue(new HSSFRichTextString(exportString(table[i][j + 4 - 1])));
				}

			}
			// 行添加
			rows.add(row);
		}

		// 设置文件名
		String fileName = user.getUserName().concat("_")
				.concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDDHHmmSS")));
		// 重新将数据写入Excel中
		FileOutputStream outputStream = new FileOutputStream("D:/".concat(fileName).concat(".xlsx"));
		hwb.write(outputStream);
		outputStream.flush();
		outputStream.close();

		baseDto.setFileName(fileName);
		return baseDto;
	}

	private boolean checkIsEditOrDel(TableDto table, String line, String userId, boolean flag) {
		// 获取文档编号
		String docCode = table.getTable()[Integer.parseInt(line)][0];
		// 获取文档信息
		DocDetailInfoEntity docEntity = docSearchDao.getdocEntity(docCode);
		if (flag) {// 编辑验证
			if (docEntity.getDirector().equals(userId) && !docEntity.getApprovalStatus().equals(String.valueOf(Constant.DOC_STATUS_AUDITING))) {
				return true;
			}
		} else {// 删除验证
			if (docEntity.getDirector().equals(userId) && docEntity.getApprovalStatus().equals(String.valueOf(Constant.DOC_STATUS_MAKING))) {
				return true;
			}
		}

		return false;
	}

	private boolean checkDeptIsInDocDepts(TableDto table, String line) {
		// 获取文档编号
		String docCode = table.getTable()[Integer.parseInt(line)][0];
		// 所属部门id
		List<UserDeptEntity> depts = table.getDepts();
		// 文档可查看部门id
		List<String> docDeptIds = docSearchDao.getViewDeptIds(docCode);

		if (docDeptIds != null && docDeptIds.size() > 0) {
			for (UserDeptEntity dept : depts) {
				if (docDeptIds.contains(dept.getDeptId())) {
					return true;
				}
			}
		}
		return false;
	}

	private String matchingIn(String in) {
		StringBuilder out = new StringBuilder("");
		if (in != null && !in.isEmpty()) {
			String[] array = in.split(",");
			for (String s : array) {
				if (s.indexOf("#") > 0) {// popup多选
					out.append("'").append(s.substring(0, s.indexOf("#"))).append("',");
				} else {// 下拉多选
					out.append("'").append(s).append("',");
				}

			}
			// 去除最后一个，
			out.delete(out.length() - 1, out.length());

		} else {
			out.append("''");
		}
		return out.toString();
	}

	private String exportString(String in) {
		StringBuilder out = new StringBuilder("");

		if (in == null || in.isEmpty() || (in.indexOf("]") == -1 && in.indexOf(",") == -1 && in.indexOf("#") == -1)) {
			if (in == null) {
				in = "";
			}
			out.append(in);
		} else if (in.lastIndexOf("]") == in.length() - 1) {
			if (in.indexOf("][") != -1) {// 多条数据处理
				String[] a1 = in.substring(1, in.length() - 1).split("\\]\\[");
				for (int i = 0; i < a1.length; i++) {
					out.append(a1[i].substring(a1[i].indexOf(",") + 1));
					if (i < a1.length - 1) {
						out.append(",");
					}
				}
			} else {// 单条数据
				out.append(in.substring(in.indexOf(",") + 1, in.length() - 1));
			}
		} else if (in.lastIndexOf(",") == in.length() - 1) {
			out.append(in.substring(0, in.length() - 1));
		} else {
			if (in.indexOf("#") != -1) {// xx#xx,xx#xx 数据
				if (in.indexOf(",") != -1) {// 多条数据处理
					String[] a1 = in.split(",");
					for (int i = 0; i < a1.length; i++) {
						out.append(a1[i].substring(a1[i].indexOf("#") + 1));
						if (i < a1.length - 1) {
							out.append(",");
						}
					}
				} else {// 单条数据
					out.append(in.substring(in.indexOf("#") + 1));
				}
			} else {// xx,xx 数据
				if (in.indexOf(",") != -1) {
					out.append(in.indexOf(",") + 1);
				} else {
					out.append(in);
				}

			}
		}

		return out.toString();

	}
}
