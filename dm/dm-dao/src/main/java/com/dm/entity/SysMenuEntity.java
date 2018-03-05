package com.dm.entity;

import java.math.BigInteger;

import com.dm.dto.BaseDto;

/**
 * SYS_MENU Entity
 * @author 张丽
 *
 */
public class SysMenuEntity extends BaseDto{

	/*
	 * 主键（菜单id）
	 */
	private String menuId;
	/*
	 * 父级菜单
	 */
	private String parent;
	/*
	 * 菜单文本
	 */
	private String text;
	/*
	 * 菜单标题
	 */
	private String title;
	/*
	 * 菜单图标
	 */
	private String icon;
	/*
	 * 序号
	 */
	private int sortNo;
	/*
	 * 是否受控
	 */
	private Boolean controlled;
	/*
	 * 是否显示
	 */
	private Boolean display;
	/*
	 * 数据版本
	 */
	private BigInteger version;
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public Boolean getControlled() {
		return controlled;
	}
	public void setControlled(Boolean controlled) {
		this.controlled = controlled;
	}
	public Boolean getDisplay() {
		return display;
	}
	public void setDisplay(Boolean display) {
		this.display = display;
	}
	public BigInteger getVersion() {
		return version;
	}
	public void setVersion(BigInteger version) {
		this.version = version;
	}

}
