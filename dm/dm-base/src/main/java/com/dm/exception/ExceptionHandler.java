package com.dm.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {
	private Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
	
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {
		log.error("异常发生：\n", ex);
		
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("exMsg", ex.getMessage());
		return new ModelAndView("util/error",model);
	}

}
