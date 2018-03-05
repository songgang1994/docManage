package com.dm.srv.departManage;

import java.util.List;
import com.dm.dto.DepartManageInfoDto;
import com.dm.entity.DepartmentInfoEntity;

/**
 * NSK-NRDC业务系统
 * 作成人：李辉
 *	组织结构维护Server
 */
public interface DepartManageSrv {
	//获取组织结构目录树数据
	List<DepartmentInfoEntity> getdepartManageTree();
	//新增部门
	DepartmentInfoEntity departAdd(DepartManageInfoDto departInfo);
	//根据deptId获取部门信息
	DepartManageInfoDto departDetail(String departId);
	//修改部门信息
	DepartManageInfoDto departUpd(DepartManageInfoDto departInfo);
	//删除部门
	DepartManageInfoDto departDelete(String deptId);
}
