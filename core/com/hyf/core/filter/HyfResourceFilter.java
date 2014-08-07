package com.hyf.core.filter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hyf.prince.dao.IUserDao;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.domain.UserToken;
import com.hyf.prince.resource.BaseResource;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

@Service("hyfFilter")
public class HyfResourceFilter implements ResourceFilter,
		ContainerRequestFilter, ContainerResponseFilter  {

	@Resource
	private ApplicationContext applicationContext;

	@Resource
	private IUserDao iUserDao;
	
	private static final Logger logger = LoggerFactory
			.getLogger(HyfResourceFilter.class);

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		try{
			//注册、登录、查看附近的活动、查看活动详情、发表活动评论
			 String uri = request.getPath();
			 logger.debug("access url : "+uri);
			if(uri.contains("register") || 
					uri.contains("login") ||
					uri.contains("activity/like") ||
					uri.contains("activity/detail") ||
					uri.contains("activity/members") ||
					uri.contains("activity/commentlist") ||
					uri.contains("activity/attachs") ||
					uri.contains("file/download") ||
					uri.contains("activity/arroundlist") ||
					uri.contains("cometd")
					){
				return request;
			}
			BaseResource baseResource = (BaseResource) applicationContext.getBean("userResource");
			HttpServletRequest httpRequest = baseResource.getRequest();
			
			String uriToken = httpRequest.getParameter("token");
			if(!StringUtils.hasText(uriToken)){
				logger.info("没有令牌访问!");
				throw new WebApplicationException(getErrorResponse());
			}
			UserToken userToken = iUserDao.getUserTokenById(uriToken);
			if(userToken == null){
				logger.info("令牌校验失败:token=" + uriToken);
				throw new WebApplicationException(getErrorResponse());
			}
			UserBase userBase = (UserBase) HyfCache.getCache("loginUser", userToken.getUserId().toString());
			if(userBase == null){
				userBase = iUserDao.getUserBaseById(userToken.getUserId());
			}
			if(userBase == null){
				logger.info("令牌校验失败:token=" + uriToken);
				throw new WebApplicationException(getErrorResponse());
			}else{
				HyfCache.putCache("loginUser", userToken.getUserId().toString(), userBase);
				httpRequest.setAttribute("loginUser", userBase);
			}
			return request;
		}catch(Exception e){
			logger.error("filter 发生异常");
			logger.error("cause by:",e);
			throw new WebApplicationException(getErrorResponse());
		}
	}

	public Response getErrorResponse() {
		Response jerseyResponse;
		ResponseBuilder responseBuilder = null;
		responseBuilder = Response.ok("{\"errCode\":\"0000100\"}");
		jerseyResponse = responseBuilder.build();
		return jerseyResponse;
	}


	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return this;
	}

	@Override
	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		return response;
	}

}
