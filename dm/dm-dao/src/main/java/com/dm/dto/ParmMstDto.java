package com.dm.dto;
/**
 * 区分MST Dto
 * @author 张丽
 *
 */
public class ParmMstDto extends BaseDto{
	/*
	 * 类型1
	 */
	private String type1;
	/*
	 * 类型1名称
	 */
	private String type1Name;
	/*
	 * 通用维护Flag
	 */
	private String editFlag;

	 /*
     * 大区分
     */
	private String bigType;
	/*
	 * 小区分
	 */
	private String smallType;
	/*
	 * codeNo
	 */
	private String codeNo;
	/*
	 * value
	 */
	private String value;
	/*
	 * 显示名称
	 */
	private String dispName;
    /*
     * 行数
     */
     private String maxNum;


	public String getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
	public String getSmallType() {
		return smallType;
	}
	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}
	public String getCodeNo() {
		return codeNo;
	}
	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDispName() {
		return dispName;
	}
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType1Name() {
		return type1Name;
	}
	public void setType1Name(String type1Name) {
		this.type1Name = type1Name;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}





}
