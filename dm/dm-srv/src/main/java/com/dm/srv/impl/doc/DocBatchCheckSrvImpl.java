package com.dm.srv.impl.doc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.CheckApprovalProgressDao;
import com.dm.dao.DocBatchDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocBatchCheckTableDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocDetailInfoEntity;
import com.dm.entity.DocFileEntity;
import com.dm.entity.DocItemDataSourceCodeEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.ParmMstEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.doc.DocBatchCheckSrv;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.tool.Constant;

@Service
public class DocBatchCheckSrvImpl extends BaseSrvImp implements DocBatchCheckSrv {

	@Autowired
	private DocBatchDao docBatchDao;
	@Autowired
	private CheckApprovalProgressDao cAPDao;

	@Override
	public List<DocItemsEntity> queryDocItem() {
		return docBatchDao.queryDocItem();
	}

	@Override
	public List<ParmMstEntity> queryDocType(String docStatus) {
		// 默认 审核中
		ArrayList<ParmMstEntity> list = new ArrayList<>();
		list.addAll(docBatchDao.queryDocType(docStatus));
		// 调整元素位置，审核中为第一位,原位置元素删除
		list.add(0, list.get(1));
		list.remove(2);
		return list;
	}

	@Override
	public List<DepartmentInfoEntity> queryDeptListByUserId(String userId) {
		return docBatchDao.queryDeptListByUserId(userId);
	}

	@Override
	public List<DepartmentInfoEntity> queryDeptListByDeptId(List<DepartmentInfoEntity> subDept) {
		// TODO :删除与subDept 重复的数据
		return docBatchDao.queryDeptListByDeptId(subDept);
	}

	@Override
	public List<DocBatchCheckTableDto> batchCheckInit(List<DepartmentInfoEntity> subDept,
			 String docCode, String docType, String deptId, String approvalStatus,
			String director, String userId) {
		// 获取查询信息
		List<DocBatchCheckTableDto> list = docBatchDao.batchCheckInit(subDept, docCode, docType, deptId,
				approvalStatus, director, userId);

		//  过滤相同数据
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getDocumentCode().equals(list.get(i).getDocumentCode())) {
					list.remove(j);
				}
			}
		}
		// 存在查询信息，获取子文件信息
		if (list != null && list.size() > 0) {
			List<DocFileEntity> childFilelist = docBatchDao.getChildFileByParentFile(list);

			// 对子文件处理 ，放入对应审核文档中
			for (int i = 0; i < list.size(); i++) {
				List<DocFileEntity> tChildFile = new ArrayList<>();
				for (int j = 0; j < childFilelist.size(); j++) {
					DocFileEntity docFile = childFilelist.get(j);
					if (docFile.getDocumentCode().equals(list.get(i).getDocumentCode())) {
						tChildFile.add(docFile);
					}
				}
				// 设置子文件
				if (tChildFile.size() > 0) {
					// TODO: 子文件 设置为java常量
					list.get(i).setChildFileName("子文件...");
				} else {
					list.get(i).setChildFileName("");
				}
				list.get(i).setDocChildList(tChildFile);
			}
		}

		return list;
	}

	@Override
	public DocFileEntity queryFileInfo(String docCode, String fileNo, String fileType) {
		return docBatchDao.queryFileInfo(docCode, fileNo, fileType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto passOrRet(String[] docCode, String[] updateTime,String flag, String comment, String userId) {
		BaseDto baseDto = new BaseDto();
		List<String> bizMsgs = new ArrayList<>();

		// 插入文档审批履历表
		docBatchDao.addDocApproveHistory(docCode, flag, comment, userId);

		// 检查是否被其他用户更新
		boolean msgFlag = false;
		// 批量处理
		if(updateTime != null) {
			for (int i = 0; i < docCode.length; i++) {
				DocDetailInfoEntity details = docBatchDao.ckeckDocUpdateDT(docCode[i]);
				if(details.getUpdateDt()!= null && !updateTime[i].equals(String.valueOf(details.getUpdateDt().getTime()))) {
					bizMsgs.add("文档(" + docCode[i] + ")已经被其他用户修改，请重新查询后再进行审核处理。");
					baseDto.setBizCode(BizCode.BIZ_CODE_DOC_UPDATE_BY_OTHER);
					baseDto.setBizExeMsgs(bizMsgs);
					msgFlag = true;
				}
			}

			if (msgFlag) {
				return baseDto;
			}
		}

		boolean checkFlag = false;
		if (flag.equals("1")) {// 通过
			// 根据docCode获取docType
			for (int i = 0; i < docCode.length; i++) {
				DocDetailInfoEntity items = docBatchDao.getDocTypeByDocCode(docCode[i]);
				// 获取文档最终审核人
				List<String> lastApproveUsers = cAPDao.getLastApproveUser(items.getDocumentType());
				// 判断当前审核人在最终审核人名单
				for(String s :lastApproveUsers) {
					if(s.equals(userId)) {
						checkFlag = true;
					}
				}
				// 获取当前节点审核人id
				String[] deptIds = items.getApprovalDeptId().split(",");

				if (checkFlag) {// 当前审核人在最终审核人名单中时，更新文档管理表的文档状态
					docBatchDao.updDocStatus(null, docCode[i], Instant.now().toString(), userId);
				} else {
					// 获取当前审核部门上一级部门
					List<DepartmentInfoEntity> parents = docBatchDao.getParentDeptIds(deptIds);
					List<String> parentIds = parents.stream().map(x -> x.getDeptId()).collect(Collectors.toList());

					//去除重复审核部门id
					HashSet<String> h = new HashSet<String>(parentIds);
					parentIds.clear();
					parentIds.addAll(h);

					String approveIds ="";
					for(String s :parentIds) {
						if(approveIds.length()>0) {
							approveIds = approveIds.concat(",");
						}
						approveIds = approveIds.concat(s);
					}

					docBatchDao.updDocStatus(approveIds, docCode[i], Instant.now().toString(), userId);
					// 给下一级所有的审批人发送审批通知邮件：
					baseDto.setBizCode(BizCode.APPROVAL_SUCCESS);
					String[] appIds = approveIds.split(",");

					// 获取下一级部门所有审核人
					List<UserEntity> users = docBatchDao.getuserInfoByDeptId(appIds);
					// 去重复
					HashSet<UserEntity> hs = new HashSet<UserEntity>(users);
					users.clear();
					users.addAll(hs);
					// 获取邮件文档信息
					DocItemDataSourceCodeEntity doc = docBatchDao.getDocName(docCode[i]);
					for (UserEntity u : users) {
						bizMsgs.add(doc.getItemName().concat(",").concat(docCode[i]).concat(",")
								.concat(u.getUserName()).concat(",").concat(u.getEmail()));
					}

				}
			}

		} else {// 退回
			docBatchDao.updApproveComment(docCode, comment, userId, Instant.now().toString(), Constant.DOC_STATUS_MAKING);
			// 给文档责任人发送审批退回通知邮件
			for(String code:docCode) {
				UserEntity u = docBatchDao.getuserInfoByDirector(code);
				DocItemDataSourceCodeEntity doc = docBatchDao.getDocName(code);
				bizMsgs.add(doc.getItemName().concat(",").concat(code).concat(",")
						.concat(u.getUserName()).concat(",").concat(u.getEmail()));
			}

			baseDto.setBizCode(BizCode.APPROVAL_FAILED);
		}

		baseDto.setBizExeMsgs(bizMsgs);
		return baseDto;
	}

	@Override
	public DocItemsEntity getDocItemEntity(String docItemCode) {

		return docBatchDao.getDocItemsEntity(docItemCode);
	}


}
