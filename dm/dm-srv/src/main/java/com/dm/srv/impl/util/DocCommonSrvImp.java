package com.dm.srv.impl.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.dao.CheckApprovalProgressDao;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.UserDeptEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.util.DocCommonSrv;

/**
 * NSK-NRDC业务系统 作成人：李辉 共通实现Server
 */
@Service
public class DocCommonSrvImp extends BaseSrvImp implements DocCommonSrv {

	@Autowired
	private CheckApprovalProgressDao cAPDao;

	@Override
	public int getFYYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if (month < 3 /* April */) {
			year--;
		}
		return year;
	}

	@Override
	public boolean CheckApprovalProgress(String docType, String userId) {

		// 取得用户的所属部门ID
		List<DepartmentInfoEntity> deptlist = cAPDao.queryDeptListByUserId(userId);

		// 取得所属部门的所有负责人
		List<UserDeptEntity> leaders = cAPDao.getLeadersByDeptId(deptlist);
		// 如果该部门没有负责人，直接返回false
		if (leaders == null || leaders.size() == 0) {
			return false;
		}

		// 从文档审核管理表中取得文档类型的最终审核人
		List<String> lastApproveUsers = cAPDao.getLastApproveUser(docType);

		// 判断负责人是否在最终审核人名单中
		for (int i = 0; i < leaders.size(); i++) {
			for(String s :lastApproveUsers) {
				if (leaders.get(i).getUserId().equals(s)) {
					return true;
				}
			}

		}

		boolean ret = false;
		List<String> deptIds = deptlist.stream().filter(x -> x.getParentDeptId() != null )
				.map(x -> x.getParentDeptId()).collect(Collectors.toList());

		do {
			// 取得上一级部门信息
			List<DepartmentInfoEntity> parentDepts = cAPDao.getParentDeptByDeptId(deptIds);
			if (parentDepts != null) {// 存在上级部门
				// 上级部门id
				List<String> currentDeptIds = parentDepts.stream()
						.filter(x -> x.getDeptId() != null )
						.map(x -> x.getDeptId()).collect(Collectors.toList());

				// 取得上级部门的所有负责人
				List<UserDeptEntity> parents = cAPDao.getLeadersByParentDeptId(currentDeptIds);
				// 如果该部门没有负责人，直接返回false
				if (parents == null || parents.size() == 0) {
					return false;
				}
				// 判断负责人是否在最终审核人名单中
				for (int i = 0; i < parents.size(); i++) {
					for(String s :lastApproveUsers) {
						if (parents.get(i).getUserId().equals(s)) {
							return true;
						} else {
							ret = true;
						}
					}

				}
				// 设置下次查询当前部门
				List<String> parentDeptIds = parentDepts.stream()
						.filter(x -> x.getParentDeptId() != null )
						.map(x -> x.getParentDeptId()).collect(Collectors.toList());
				if(parentDeptIds == null || parentDeptIds.size() == 0) {
					return false;
				}
				deptIds = new ArrayList<>();
				for (String id : parentDeptIds) {
					deptIds.add(id);
				}

			} else {// 不存在上级部门，前一次部门为最终部门
					// 如果最高级部门负责人也不在最终审核人员名单中，方法直接返回false
				return false;
			}

		} while (ret);

		return false;
	}

}
