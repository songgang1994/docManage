package com.dm.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseCtrl {
	protected HttpServletRequest request;
	protected HttpServletResponse response ;
	protected HttpSession session ;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	
}
