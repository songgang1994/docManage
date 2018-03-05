package com.dm.ctrl.util;

import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.LoginDto;
import com.dm.dto.SysCfgDto;
import com.dm.entity.MenuEntity;
import com.dm.srv.user.UserSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统 作成人：李辉 模块名：登录画面
 */
@Controller
@RequestMapping("/login")
public class LoginCtrl extends BaseCtrl {
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(LoginCtrl.class);

	// 登录用Service
	@Autowired
	private UserSrv userSrv;

	// 登录画面初始化
	@RequestMapping(value = "/init")
	public String login(@RequestParam(value = "docCode", required = false, defaultValue = "") String docCode,
			@RequestParam(value = "mode", required = false, defaultValue = "") String mode) {
		session.setAttribute("documentCode", docCode);
		session.setAttribute("mode", mode);
		return "util/login";
	}

	// 登录画面初始化
	@RequestMapping(value = "/login")
	public String loginRefresh() {
		return "util/login";
	}

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView Logon(@ModelAttribute("logonDto") LoginDto logonDto) {
		ModelAndView mv = new ModelAndView();
		LoginDto user = userSrv.getUser(logonDto);
		if (null == user) {
			// 用户不存在时
			mv.addObject("projectForm", logonDto);
			mv.addObject("message", Constant.LOGIN_MESSAGE_NO_USER);
			mv.setViewName("util/login");
			return mv;
		}
		boolean result = connect(logonDto.getUserCode(), logonDto.getPassword());
		// 当域服务检索用户不存在的场合
		if (!result) {
			mv.addObject("projectForm", logonDto);
			mv.addObject("message", Constant.LOGIN_MESSAGE_PWD_FAIL);
			mv.setViewName("util/login");
			return mv;
		}
		// 获取链接菜单
		List<MenuEntity> menu = userSrv.getMenu(user.getRoleIds());
		if (menu == null || menu.size() == 0) {
			mv.addObject("projectForm", logonDto);
			mv.addObject("message", Constant.LOGIN_MESSAGE_ROLE_FAIL);
			mv.setViewName("util/login");
			return mv;
		}
		request.setAttribute("root", menu);
		session.setAttribute(Constant.LOGON_INFO_SESSION_KEY, user);
		mv.setViewName("util/home");
		return mv;
	}

	/**
	 * 使用java连接AD域
	 *
	 * @author Herman.Xiong
	 * @date 2014-12-23 下午02:24:04
	 * @return void
	 * @throws 异常说明
	 * @param host
	 *            连接AD域服务器的ip
	 * @param post
	 *            AD域服务器的端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public static boolean connect(String username, String password) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		SysCfgDto sysCfgInfo = (SysCfgDto) servletContext.getAttribute("SysCfgInfo");
		String adServiceUrl = sysCfgInfo.getAdServiceUrl();
		String userNameSuffix = sysCfgInfo.getUserNameSuffix();
		String ldapLevel = sysCfgInfo.getLdapLevel();
		DirContext ctx = null;
		username = username + userNameSuffix;
		Hashtable<String, String> HashEnv = new Hashtable<String, String>();
		HashEnv.put(Context.SECURITY_AUTHENTICATION, ldapLevel); // LDAP访问安全级别(none,simple,strong)
		HashEnv.put(Context.SECURITY_PRINCIPAL, username); // AD的用户名
		HashEnv.put(Context.SECURITY_CREDENTIALS, password); // AD的密码
		HashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
		HashEnv.put("com.sun.jndi.ldap.connect.timeout", "3000");// 连接超时设置为3秒
		HashEnv.put(Context.PROVIDER_URL, adServiceUrl);// 默认端口389
		try {
			ctx = new InitialDirContext(HashEnv);// 初始化上下文
			return true;
		} catch (AuthenticationException e) {
			return false;
		} catch (javax.naming.CommunicationException e) {
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			if (null != ctx) {
				try {
					ctx.close();
					ctx = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
