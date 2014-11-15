package com.hyf.prince.resource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import com.hyf.prince.domain.UserBase;

public class BaseResource {
	@Context
	protected HttpServletRequest request;
	@Context
	protected HttpServletResponse response;
	@Context
	protected ServletContext scx;
	
	public BaseResource() {
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public HttpSession getHttpSession() {
		return request.getSession();
	}

	public Integer getCurrUserId(){
		UserBase userBase = (UserBase)request.getAttribute("loginUser");
		if(userBase != null){
			return userBase.getUserId();
		}
		return null;
	}
	
	public String getCurrUsername(){
		UserBase userBase = (UserBase)request.getAttribute("loginUser");
		if(userBase != null){
			return userBase.getUsername();
		}
		return null;
	}
	
}
