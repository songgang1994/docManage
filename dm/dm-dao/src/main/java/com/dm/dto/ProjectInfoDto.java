package com.dm.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.SubjectDepEntity;
import com.dm.entity.SubjectEntity;
import com.dm.dto.SubjectLegalDto;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题Dto
 */
public class ProjectInfoDto extends SubjectEntity{
	//课题部署Id
	private String deptId;
	//部门名称
	private String deptName;
	//课题部署名称
	private String deptNames;
	//课题分类
	private String dispName;
	//课题部署Ids
	private String deptIds;
	//课题法人Ids
	private String legalIds;
	//课题比例Ids
	private String percentages;
	//课题部署List
	private String[] deptIdList;
	//课题部署名称List
	private String[] deptNameList;
	//课题部门集合
	private List<DepartmentInfoEntity> departmentInfo;
	//课题法人集合
	private List<SubjectLegalDto>subjectLegal;
	//课题部署集合
	private List<SubjectDepEntity>subjectdep;
	//年月
	private String dateInfo;
	// 工时合计
	private BigDecimal timesTotal;
	//年月
	private String projectDate;

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String[] getDeptNameList() {
		return deptNameList;
	}
	public void setDeptNameList(String[] deptNameList) {
		this.deptNameList = deptNameList;
	}
	public String getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	public String getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}
	public BigDecimal getTimesTotal() {
		return timesTotal;
	}
	public void setTimesTotal(BigDecimal timesTotal) {
		this.timesTotal = timesTotal;
	}
	public String[] getDeptIdList() {
		return deptIdList;
	}
	public void setDeptIdList(String[] deptIdList) {
		this.deptIdList = deptIdList;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getLegalIds() {
		return legalIds;
	}
	public void setLegalIds(String legalIds) {
		this.legalIds = legalIds;
	}
	public String getPercentages() {
		return percentages;
	}
	public void setPercentages(String percentages) {
		this.percentages = percentages;
	}
	public List<SubjectDepEntity> getSubjectdep() {
		return subjectdep;
	}
	public void setSubjectdep(List<SubjectDepEntity> subjectdep) {
		this.subjectdep = subjectdep;
	}
	public List<SubjectLegalDto> getSubjectLegal() {
		return subjectLegal;
	}
	public void setSubjectLegal(List<SubjectLegalDto> subjectLegal) {
		this.subjectLegal = subjectLegal;
	}
	public List<DepartmentInfoEntity> getDepartmentInfo() {
		return departmentInfo;
	}
	public void setDepartmentInfo(List<DepartmentInfoEntity> departmentInfo) {
		this.departmentInfo = departmentInfo;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
