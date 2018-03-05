package com.dm.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dm.entity.DepartmentInfoEntity;


/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
public class PersonMonthHourInfo extends BaseDto {

	private static final  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

	private String dateTm;
	private String projectNo;
	private String projectName;
	private String deptName;
	private BigDecimal workingtimes;
	private List<DepartmentInfoEntity> department;
	private Date dateTime;



	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public List<DepartmentInfoEntity> getDepartment() {
		return department;
	}
	public void setDepartment(List<DepartmentInfoEntity> department) {
		this.department = department;
	}
	public String getDateTm() {
		return dateTm;
	}
	public void setDateTm(Date dateTm) {

		this.dateTm = sdf.format(dateTm);
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public BigDecimal getWorkingtimes() {
		return workingtimes;
	}
	public void setWorkingtimes(BigDecimal workingtimes) {
		this.workingtimes = workingtimes;
	}


}
