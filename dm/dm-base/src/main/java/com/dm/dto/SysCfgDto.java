package com.dm.dto;

import java.util.List;

public class SysCfgDto {
	/**
	 * 基本路径
	 */
	private String basePath;

	/**
	 * 页面标题
	 */
	private String pageTitle;

	/**
	 * SEO关键字
	 */
	private String seoKeywords;

	/**
	 * 网站描述
	 */
	private String siteDescription;

	/**
	 * 允许访问的域名列表
	 */
	private List<String> allowAccessDomains;

	/**
	 * 域服务地址
	 */
	private String adServiceUrl;

	/**
	 * 登录用户后缀
	 */
	private String userNameSuffix;

	/**
	 * LDAP访问安全级别
	 */
	private String ldapLevel;

	/**
	 * 系统全局信息是否配置完成
	 */
	private boolean isCfgFinished;

	/**
	 * 课题人工统计月度报表excel模板路径
	 */
	private String projectHourMonthlyReportPath;

	/**
	 * 课题一览报表excel模板路径
	 */
	private String projectPath;

	/*
	 * SMTP服务器地址
	 */
	private String emailSMTPHost;
	/*
	 * email用户名
	 */
	private String emailAccount;
	/*
	 * email密码
	 */
	private String emailPassword;
	/*
	 * 首页地址
	 */
	private String index;

	/*
	 * 技术企划室id
	 */
	private String technicalPlanRoom;

	public String getTechnicalPlanRoom() {
		return technicalPlanRoom;
	}

	public void setTechnicalPlanRoom(String technicalPlanRoom) {
		this.technicalPlanRoom = technicalPlanRoom;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getEmailSMTPHost() {
		return emailSMTPHost;
	}

	public void setEmailSMTPHost(String emailSMTPHost) {
		this.emailSMTPHost = emailSMTPHost;
	}

	public String getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getProjectHourMonthlyReportPath() {
		return projectHourMonthlyReportPath;
	}

	public void setProjectHourMonthlyReportPath(String projectHourMonthlyReportPath) {
		this.projectHourMonthlyReportPath = projectHourMonthlyReportPath;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getUserNameSuffix() {
		return userNameSuffix;
	}

	public void setUserNameSuffix(String userNameSuffix) {
		this.userNameSuffix = userNameSuffix;
	}

	public String getLdapLevel() {
		return ldapLevel;
	}

	public void setLdapLevel(String ldapLevel) {
		this.ldapLevel = ldapLevel;
	}

	public String getAdServiceUrl() {
		return adServiceUrl;
	}

	public void setAdServiceUrl(String adServiceUrl) {
		this.adServiceUrl = adServiceUrl;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSiteDescription() {
		return siteDescription;
	}

	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}

	public boolean isCfgFinished() {
		return isCfgFinished;
	}

	public void setCfgFinished(boolean isCfgFinished) {
		this.isCfgFinished = isCfgFinished;
	}

	public List<String> getAllowAccessDomains() {
		return allowAccessDomains;
	}

	public void setAllowAccessDomains(List<String> allowAccessDomains) {
		this.allowAccessDomains = allowAccessDomains;
	}

}
