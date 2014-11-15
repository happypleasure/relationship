package com.hyf.prince.resource;

import java.util.List;
import java.util.Map;

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
import com.hyf.prince.domain.Activity;
import com.hyf.prince.domain.ActivityAttach;
import com.hyf.prince.domain.ActivityComment;
import com.hyf.prince.domain.ActivityMember;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.service.IActivityService;

@Path("/activity")
@Controller
public class ActivityResource  extends BaseResource{

	@Resource
	private IActivityService activitySer;
	private static final Logger logger = LoggerFactory
			.getLogger(ActivityResource.class);
	
	//发布活动
	@POST
	@Path("/publish")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj publishActivity(Activity activity){
		RtnObj rtnObj = new RtnObj();
		try{
			activity.setSponsorId(getCurrUserId());
			activity = activitySer.publishActivity(activity);
			rtnObj.setData(activity);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//修改活动
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj updateActivity(Activity activity){
		RtnObj rtnObj = new RtnObj();
		try{
			activity = activitySer.updateActivity(activity);
			rtnObj.setData(activity);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//参加活动
	@POST
	@Path("/join")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj joinActivity(ActivityMember am){
		RtnObj rtnObj = new RtnObj();
		try{
			am.setUserId(getCurrUserId());
			activitySer.joinActivity(am);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//喜欢活动
	@POST
	@Path("/like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj likeActivity(Activity activity){
		RtnObj rtnObj = new RtnObj();
		try{
			activitySer.likeActivity(activity);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//评论活动
	@POST
	@Path("/comment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj commentActivity(ActivityComment activityComment){
		RtnObj rtnObj = new RtnObj();
		try{
			activityComment.setUserId(getCurrUserId());
			activitySer.commentActivity(activityComment);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//活动详情
	@GET
	@Path("/detail")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getDetailById(@QueryParam("activityId") Integer activityId){
		RtnObj rtnObj = new RtnObj();
		try{
			Activity activity = activitySer.getDetailById(activityId);
			rtnObj.setData(activity);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//获取活动的参与人
	@GET
	@Path("/members")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getActivityMembers(@QueryParam("activityId") Integer activityId){
		RtnObj rtnObj = new RtnObj();
		try{
			List<UserBase> members = activitySer.getActivityMembers(activityId);
			rtnObj.setData(members);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//获取活动的评论
	@GET
	@Path("/commentlist")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getActivityComments(@QueryParam("activityId") Integer activityId){
		RtnObj rtnObj = new RtnObj();
		try{
			Map<String,Object> mapResult = activitySer.getActivityComments(activityId);
			rtnObj.setData(mapResult);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//获取活动的附件图片
	@GET
	@Path("/attachs")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getActivityAttachs(@QueryParam("activityId") Integer activityId){
		RtnObj rtnObj = new RtnObj();
		try{
			List<ActivityAttach> attachs = activitySer.getActivityAttachs(activityId);
			rtnObj.setData(attachs);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//获取附近的活动列表
	@GET
	@Path("/arroundlist")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getArroundActivitys(@QueryParam("coorX") Double coorX ,
									  @QueryParam("coorY") Double coorY ,
									  @QueryParam("nearRange")Double nearRange){
		RtnObj rtnObj = new RtnObj();
		try{
			List<Activity> arroundActivitys = 
					activitySer.getArroundActivitys(coorX,coorY,nearRange);
			rtnObj.setData(arroundActivitys);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//获取我发布的活动列表
	@GET
	@Path("/sponsorlist")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getSponsorActivitys(){
		RtnObj rtnObj = new RtnObj();
		try{
			List<Activity> sponsorActivitys = activitySer.getSponsorActivitys(getCurrUserId());
			rtnObj.setData(sponsorActivitys);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
	
	//获取我参与的活动列表
	@GET
	@Path("/memberlist")
	@Produces(MediaType.APPLICATION_JSON)
	public RtnObj getMemberActivitys(){
		RtnObj rtnObj = new RtnObj();
		try{
			List<Activity> memberActivitys = activitySer.getMemberActivitys(getCurrUserId());
			rtnObj.setData(memberActivitys);
			rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
		}catch(Exception e){
			logger.error("cause by:",e);
			rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
		}
		return rtnObj;
	}
}
