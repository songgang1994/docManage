package com.dm.entity;

import java.util.List;

public class MenuEntity {
	private String menuId;
	private String title;
	private String text;
	private String icon;
	private String url;
	private List<MenuEntity> children;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
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
	public List<MenuEntity> getChildren() {
		return children;
	}
	public void setChildren(List<MenuEntity> children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
