package com.hyf.prince.resource;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.hyf.core.domain.CommConstant;
import com.hyf.core.domain.RtnObj;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.domain.UserDetail;
import com.hyf.prince.domain.UserInfo;
import com.hyf.prince.service.IUserService;

@Path("/user")
@Controller
public class UserResource extends BaseResource{
	@Resource
	private  IUserService iUserService;
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj register(UserDetail userDetail){
		RtnObj rtnObj = new RtnObj();
		try{
			UserBase result = iUserService.register(userDetail);
			rtnObj.setErrCode(result.getErrorCode());
			rtnObj.setData(result);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj login(UserDetail userDetail){
		RtnObj rtnObj = new RtnObj();
		try{
			UserBase result = iUserService.login(userDetail);
			String errorCode = result.getErrorCode();
			if(CommConstant.ERRC_SUCCESS.equals(errorCode)){
				getHttpSession().setAttribute("userBase", result);
			}
			rtnObj.setErrCode(result.getErrorCode());
			rtnObj.setData(result);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	@POST
	@Path("/updateuserinfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj updateUserInfo(UserDetail userDetail){
		RtnObj rtnObj = new RtnObj();
		try{
			UserDetail result = iUserService.updateUserInfo(userDetail);
			rtnObj.setData(result);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	@GET
	@Path("/getuserbase")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getUserBaseById(@QueryParam("userId")Integer userId){
		RtnObj rtnObj = new RtnObj();
		try{
			if(userId == null ){
				userId = getCurrUserId();
			}
			UserBase userBase = iUserService.getUserBaseById(userId);
			rtnObj.setData(userBase);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	@GET
	@Path("/getuserinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getUserDetailById(@QueryParam("userId")Integer userId){
		RtnObj rtnObj = new RtnObj();
		try{
			if(userId == null ){
				userId = getCurrUserId();
			}
			UserInfo userInfo = iUserService.getUserInfoById(userId);
			rtnObj.setData(userInfo);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	@POST
	@Path("/quitSystem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj quitSystem(){
		RtnObj rtnObj = new RtnObj();
		try{
		    iUserService.quitSystem(getCurrUserId());
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
}
