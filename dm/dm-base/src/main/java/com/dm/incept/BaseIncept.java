package com.dm.incept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.SysCfgDto;
import com.dm.tool.Constant;
import com.dm.tool.CookieTool;

public class BaseIncept extends HandlerInterceptorAdapter {

	private Logger log = LoggerFactory.getLogger(BaseIncept.class);

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("CtrlStartTime");

	 /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			if (handler instanceof DefaultServletHttpRequestHandler) {
				// CSS,image,js等非controller资源
			}
			if (handler instanceof HandlerMethod) {
				//记录执行开始时间
				ConfigCtrlStartTime(handler);
				// 设置BaseCtrl中的request,session,response对象
				ConfigReqResSess(request, response, handler);
				// 设置基本路径basePath
				ConfigBasePathSysInfo(request, response, handler);
				// 自动登录处理
				// AutoLogon(request, response,handler);

				// 登录状态检查
//				if (IsLogon(request, handler) == false) {
//					// 未登录时，访问无权限画面，禁止，直接跳转到登录画面
//					response.sendRedirect(request.getScheme() + "://"
//							+ request.getServerName() + ":"
//							+ request.getServerPort()
//							+ request.getContextPath()
//							+ "/login/init");
//					return false;
//				}

//				String url = "";
//				url = request.getScheme() +"://" + request.getServerName()
//				+ ":" +request.getServerPort()
//				+ request.getServletPath();
//				if (request.getQueryString() != null){
//				url += "?" + request.getQueryString();
//				}
//
//				String[] urlArray = url.split("/");
//
//            	Cookie UserRole = CookieTool.getCookieByName(request,
//        				"UserRole");
//            	Cookie cokLoginUserName = CookieTool.getCookieByName(request,
//        						"loginUserName");
//
//            	if((urlArray[3].equals("engineering") && !(Integer.parseInt(UserRole.getValue())== 0||
//						Integer.parseInt(UserRole.getValue())== 1))||
//
//				   (urlArray[3].equals("arrangement") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 4))||
//
//				   (urlArray[3].equals("schedule") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 1||Integer.parseInt(UserRole.getValue())== 3))||
//
//				   (urlArray[3].equals("check") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 1||Integer.parseInt(UserRole.getValue())== 2))||
//
//				   (urlArray[3].equals("statistics") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 1||Integer.parseInt(UserRole.getValue())== 2))||
//
//				   (urlArray[3].equals("engfinish") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 1))||
//
//				   (urlArray[3].equals("engfinishcheck") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 4))||
//
//				   (urlArray[3].equals("echarts") && !(Integer.parseInt(UserRole.getValue())== 0||
//				        Integer.parseInt(UserRole.getValue())== 1)))
//				{
//
//					response.sendRedirect(request.getScheme() + "://"
//							+ request.getServerName() + ":"
//							+ request.getServerPort()
//							+ request.getContextPath()
//							+ "/util/login/init?access=nologon");
//				}


			}
			if (request.getSession().getAttribute(Constant.LOGON_INFO_SESSION_KEY) == null)//判断session里是否有用户信息
            {

				if (request.getHeader("x-requested-with") != null
                     && request.getHeader("x-requested-with")
                             .equalsIgnoreCase("XMLHttpRequest"))//如果是ajax请求响应头会有，x-requested-with；
             {
                 response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
                 return false;
             }
         }
			 HttpServletRequest req = (HttpServletRequest) request;
             String servletPath = req.getServletPath();
             String conString = "";
             conString = req.getHeader("REFERER");//获取父url--如果不是直接输入的话就是先前的访问过来的页面，要是用户输入了，这个父url是不存在的
             if("".equals(conString) || null==conString){ //判断如果上一个目录为空的话，说明是用户直接输入url访问的
             if(!servletPath.contains("login")) {
            	 return false;
             }
             }
		} catch (Exception ex) {
			log.error("Request,Response,Session对象设置异常发生。", ex);
			return false;
		}

		return true;
	}

	/**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			//记录执行结束时间
			ConfigCtrlEndTime(handler);
		}

	}


	//记录执行结束时间
	private void ConfigCtrlEndTime(Object handler)
	{
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String methodName = handlerMethod.getMethod().getName();
		long endTime = System.currentTimeMillis();//2、结束时间
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;//3、消耗的时间

        log.info(String.format("[%s][%s][%s - %s] = [%s]ms 结束执行", handlerMethod.getBeanType().getName(),methodName,endTime,beginTime,consumeTime));
	}

	//记录执行开始时间
	private void ConfigCtrlStartTime(Object handler)
	{
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String methodName = handlerMethod.getMethod().getName();
		//记录开始时间
		long startTime = System.currentTimeMillis();
		startTimeThreadLocal.set(startTime);

		log.info(String.format("[%s][%s][%s]ms 开始执行", handlerMethod.getBeanType().getName(),methodName,startTime));
	}

	// 记住登录状态自动登录
	private void AutoLogon(HttpServletRequest request,
			HttpServletResponse response,Object handler) throws IOException, ServletException {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String currMethod = handlerMethod.getMethod().toString();

		if(currMethod.indexOf("com.mitp.ctrl.util.LoginCtrl.OnLoad") > -1
				&& null != request.getSession().getAttribute(
						Constant.LOGON_INFO_SESSION_KEY))
		{
			// 登录页初期化请求处理而且已经登录时，直接跳转到后台页面
			request.getRequestDispatcher("/index/init").forward(request, response);
		}

		if (currMethod.indexOf("com.mitp.ctrl.util.LoginCtrl.autoLogon") > -1 ||
				null != request.getSession().getAttribute(
				Constant.LOGON_INFO_SESSION_KEY)) {
			// 自动登录请求处理或者已经登录时，不做以下处理
			return;
		}

		Cookie cokLoginUserName = CookieTool.getCookieByName(request,
				"loginUserName");
		Cookie cokLoginPassword = CookieTool.getCookieByName(request,
				"loginPassword");

		if (null != cokLoginUserName && null != cokLoginPassword
				&& null != cokLoginUserName.getValue()
				&& null != cokLoginPassword.getValue()) {
			 response.sendRedirect(request.getScheme() + "://" +
			 request.getServerName()
			 + ":" + request.getServerPort() + request.getContextPath()
			 + "/util/login/autologon");
		}

	}

	// 登录状态检查
	private boolean IsLogon(HttpServletRequest request, Object handler) {
		List<String> exMappingMethods = new ArrayList<String>();
		exMappingMethods.add("com.mitp.ctrl.util.LoginCtrl.OnLoad");
		exMappingMethods.add("com.mitp.ctrl.util.LoginCtrl.Logon");
		exMappingMethods.add("com.mitp.ctrl.util.LoginCtrl.autoLogon");

		exMappingMethods.add("com.mitp.rest.account.RestAccount.login");

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		String currMethod = handlerMethod.getMethod().toString();

		boolean isExclude = false;
		for (int i = 0; i < exMappingMethods.size(); i++) {
			if (currMethod.indexOf(exMappingMethods.get(i)) > -1) {
				isExclude = true;
				break;
			}
		}
//        Cookie[] cookies = request.getCookies();
		if (null != request.getSession().getAttribute(
				Constant.LOGON_INFO_SESSION_KEY)
				|| isExclude == true) {
			return true;
		}

		return false;
	}

	// 设置BaseCtrl中的request,session,response对象
	private void ConfigReqResSess(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		BaseCtrl bc = (BaseCtrl) handlerMethod.getBean();
		bc.setRequest(request);
		bc.setResponse(response);
		bc.setSession(request.getSession());

	}

	// 设置基本路径
	private void ConfigBasePathSysInfo(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();

		SysCfgDto sysCfgInfo = (SysCfgDto) servletContext
				.getAttribute("SysCfgInfo");

		sysCfgInfo.setBasePath(request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/");
		sysCfgInfo.setCfgFinished(true);
	}
}
