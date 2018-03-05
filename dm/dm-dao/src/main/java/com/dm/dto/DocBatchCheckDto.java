package com.dm.dto;

import java.util.List;

import com.dm.entity.DepartmentInfoEntity;

public class DocBatchCheckDto{



	/*
	 * 所属部门
	 */
	private List<DepartmentInfoEntity> deptList;
	/*
	 * 审核部门
	 */
	private List<DepartmentInfoEntity> approveDeptList;
	/*
	 * 查询列表数据
	 */
	private DocBatchCheckTableDto tableData;

}
