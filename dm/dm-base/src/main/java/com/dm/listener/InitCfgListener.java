package com.dm.listener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

import com.dm.dto.SysCfgDto;
import com.dm.tool.PropUtil;
import com.dm.tool.StringUtil;

public class InitCfgListener implements ApplicationContextAware,
		ServletContextAware, InitializingBean,
		ApplicationListener<ContextRefreshedEvent> {

	private Logger log = LoggerFactory.getLogger(InitCfgListener.class);

	// @Autowired
	// private UserSrv userService;

	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 初始化全局配置信息
		if (event.getApplicationContext().getParent() == null) {


			// HttpServletRequest request = ((ServletRequestAttributes)
			// RequestContextHolder.getRequestAttributes()).getRequest();
			// sysCfgInfo.setBasePath(request.getScheme() + "://" +
			// request.getServerName()
			// + ":" + request.getServerPort() + request.getContextPath()
			// + "/");

		}
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	public void setServletContext(ServletContext srvContext) {
		// TODO Auto-generated method stub
		try {
			SysCfgDto sysCfgInfo = new SysCfgDto();

			// 读取系统全局配置信息
			(new PropUtil()).readPropFile(PropUtil.getPropSysGlobalCfg(),
					"sysconfig.properties");

			//允许访问的域名信息
			String allowAccessDomains = PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"ALLOW_ACCESS_DOMAINS");
			//获取域服务url
			String adServiceUrl= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"AD_SERVICE_URL");
			//获取登录用户后缀
			String userNameSuffix= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"USERNAME_SUFFIX");
			//获取LDAP访问安全级别(none,simple,strong)
			String ldapLevel= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"LDAP_LEVEL");
			//课题人工统计月度报表excel模板路径
			String projectHourMonthlyReportPath= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"PROJECT_HOUR_MONTHLY_REPORT_PATH");
			//课题一览报表excel模板路径
			String projectPath= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"PROJECT_PATH");
			//SMTP服务器地址
			String eSmtphost= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"EMAIL_SMTPHOST");
			String eAccount= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"EMAIL_ACCOUNT");
			String ePassword= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"EMAIL_PASSWORD");
			String index= PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"INDEX");
			// 技术企划室
			String technicalPlanRoom = PropUtil.getPropVal(
					PropUtil.getPropSysGlobalCfg(),
					"TECHNICAL_PLAN_ROOM");
			if(StringUtil.isEmpty(allowAccessDomains) == false)
			{
				sysCfgInfo.setAllowAccessDomains(Arrays.asList(allowAccessDomains.split(",")));
			}
			else
			{
				log.warn("未设置允许跨域访问的域名");
			}
			if(StringUtil.isEmpty(adServiceUrl) == false)
			{
				sysCfgInfo.setAdServiceUrl(adServiceUrl);
			}
			else
			{
				log.warn("未设置域服务地址");
			}
			if(StringUtil.isEmpty(userNameSuffix) == false)
			{
				sysCfgInfo.setUserNameSuffix(userNameSuffix);
			}
			else
			{
				log.warn("未设置登录用户后缀");
			}
			if(StringUtil.isEmpty(ldapLevel) == false)
			{
				sysCfgInfo.setLdapLevel(ldapLevel);
			}
			else
			{
				log.warn("未设置LDAP访问安全级别");
			}
			if(StringUtil.isEmpty(projectHourMonthlyReportPath) == false)
			{
				sysCfgInfo.setProjectHourMonthlyReportPath(projectHourMonthlyReportPath);
			}
			else
			{
				log.warn("未设置课题人工统计月度报表excel模板路径");
			}
			if(StringUtil.isEmpty(projectPath) == false)
			{
				sysCfgInfo.setProjectPath(projectPath);
			}
			else
			{
				log.warn("未设置课题一览报表excel模板路径");
			}
			if(StringUtil.isEmpty(index) == false) {
				sysCfgInfo.setIndex(index);
			}else {
				log.warn("未设置首页路径");
			}
			if(StringUtil.isEmpty(index) == false) {
				sysCfgInfo.setEmailSMTPHost(eSmtphost);
			}else {
				log.warn("未设置SMTP服务器地址");
			}
			if(StringUtil.isEmpty(index) == false) {
				sysCfgInfo.setEmailAccount(eAccount);
			}else {
				log.warn("未设置email用户名");
			}
			if(StringUtil.isEmpty(index) == false) {
				sysCfgInfo.setEmailPassword(ePassword);
			}else {
				log.warn("未设置email密码");
			}
			if(StringUtil.isEmpty(technicalPlanRoom) == false) {
				sysCfgInfo.setTechnicalPlanRoom(technicalPlanRoom);
			}else {
				log.warn("未设置技术企划室ID");
			}
//			WebApplicationContext webApplicationContext = ContextLoader
//					.getCurrentWebApplicationContext();
//			ServletContext servletContext = webApplicationContext
//					.getServletContext();
			srvContext.setAttribute("SysCfgInfo", sysCfgInfo);
			// 读取消息文件

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {


	}

}
