package com.dm.dto;

import com.dm.entity.DocDetailInfoEntity;

public class DocDetailInfoDto extends DocDetailInfoEntity{

	/*
	 * 文档可查看部门
	 */
	private String docReadDeptId;
	/*
	 * 主文件
	 */
	private String mainFile;
	/*
	 * 子文件
	 */
	private String subFile;


	public String getMainFile() {
		return mainFile;
	}

	public void setMainFile(String mainFile) {
		this.mainFile = mainFile;
	}

	public String getSubFile() {
		return subFile;
	}

	public void setSubFile(String subFile) {
		this.subFile = subFile;
	}

	public String getDocReadDeptId() {
		return docReadDeptId;
	}

	public void setDocReadDeptId(String docReadDeptId) {
		this.docReadDeptId = docReadDeptId;
	}


}
