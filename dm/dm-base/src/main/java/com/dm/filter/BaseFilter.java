package com.dm.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.druid.support.logging.Log;
import com.dm.dto.SysCfgDto;
import com.dm.listener.InitCfgListener;

public class BaseFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(BaseFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();

		SysCfgDto sysCfgInfo = (SysCfgDto) servletContext
				.getAttribute("SysCfgInfo");

//		String[] allowDomain = { "http://localhost:14602", "http://10.30.100.54:14602", "http://58.241.166.82:14602" };
		Set<String> allowedOrigins = new HashSet<String>(sysCfgInfo.getAllowAccessDomains());
		String originHeader = ((HttpServletRequest) req).getHeader("Origin");

		if (allowedOrigins.contains(originHeader)) {
			res.setHeader("Access-Control-Allow-Origin", originHeader);
		}
		else
		{
			if(null != originHeader)
			{
				log.error("不被允许的域名访问：" + originHeader);
			}
//			return;//API直接访问需要
		}

		res.setContentType("application/json;charset=UTF-8");
		res.setHeader("Access-Control-Allow-Credentials", "true");//如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		res.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");//表明服务器支持的所有头信息字段
		res.setHeader("Access-Control-Max-Age", "1800");// 30 min
		res.setHeader("XDomainRequestAllowed","1");

		req.setAttribute("path", req.getContextPath());
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
