package com.dm.entity;

import java.math.BigInteger;

import com.dm.dto.BaseDto;

/**
 * sysAction  Entity
 * @author 张丽
 *
 */
public class SysActionEntity extends BaseDto{

	/*
	 * 主键（动作Id）
	 */
	private String actionId;
	/*
	 * 菜单/页面
	 */
	private String menuId;
	/*
	 * URL
	 */
	private String url;
	/*
	 * 助记文本
	 */
	private String actionText;
	/*
	 * 动作类型
	 */
	private String actionType;
	/*
	 * 匹配方式
	 */
	private String matchingType;
	/*
	 * 是否受控
	 */
	private Boolean controlled;
	/*
	 * 参数控制
	 */
	private Boolean paramCtrl;
	/*
	 * 元素Id
	 */
	private String elementId;
	/*
	 * 元素名称
	 */
	private String elementName;
	/*
	 * 元素样式
	 */
	private String elementClass;
	/*
	 * 搜索方式
	 */
	private String searchType;
	/*
	 * 序号
	 */
	private int sortNo;
	/*
	 * 数据版本
	 */
	private BigInteger version;
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getActionText() {
		return actionText;
	}
	public void setActionText(String actionText) {
		this.actionText = actionText;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getMatchingType() {
		return matchingType;
	}
	public void setMatchingType(String matchingType) {
		this.matchingType = matchingType;
	}
	public Boolean getControlled() {
		return controlled;
	}
	public void setControlled(Boolean controlled) {
		this.controlled = controlled;
	}
	public Boolean getParamCtrl() {
		return paramCtrl;
	}
	public void setParamCtrl(Boolean paramCtrl) {
		this.paramCtrl = paramCtrl;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getElementClass() {
		return elementClass;
	}
	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public BigInteger getVersion() {
		return version;
	}
	public void setVersion(BigInteger version) {
		this.version = version;
	}


}
