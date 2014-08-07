package com.hyf.prince.resource;

import java.util.HashMap;
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
import com.hyf.prince.domain.Message;
import com.hyf.prince.domain.MessageBox;
import com.hyf.prince.domain.MessageLine;
import com.hyf.prince.result.FrontMessage;
import com.hyf.prince.service.IMessageService;

@Path("/message")
@Controller
public class MessageResource  extends BaseResource{
	 	
		@Resource
		private IMessageService messageSer;
		private static final Logger logger = LoggerFactory
				.getLogger(MessageResource.class);
		
		//发送消息
		@POST
		@Path("/sendmessage")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj sendMessage(Message message){
			RtnObj rtnObj = new RtnObj();
			 try{
				 message.setSendUserId(getCurrUserId());
				 message = messageSer.sendMessage(message);
				 rtnObj.setData(message);
				 rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
		
		//改变是否阅读的状态
		@POST
		@Path("/updatereadflag")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj updateReadFlag(MessageBox mb){
			RtnObj rtnObj = new RtnObj();
			 try{
				 mb.setUserId(getCurrUserId());
				 messageSer.updateReadFlag(mb);
				 rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
		
		//删除某一条消息
		@POST
		@Path("/deleteone")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj deleteMessageSession(MessageBox mb){
			RtnObj rtnObj = new RtnObj();
			 try{
				 mb.setUserId(getCurrUserId());
				 messageSer.deleteOneById(mb);
				 rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
		
		//删除和某个人的消息会话
		//param:consistId
		@POST
		@Path("/deletesession")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj deleteByUser(MessageLine ml){
			RtnObj rtnObj = new RtnObj();
			 try{
				 ml.setUserId(getCurrUserId());
				 messageSer.deleteByUser(ml);
				 rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
		
		//获取总未读消息数目
		@GET
		@Path("/totalunreadnum")
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj getTotalUnReadNum(){
			RtnObj rtnObj = new RtnObj();
			 try{
				Integer totalUnreadNum = 
						messageSer.getTotalUnReadNum(getCurrUserId());
				Map<String,Object> rtnMap = new HashMap<String,Object>();
				rtnMap.put("totalUnreadNum", totalUnreadNum);
				rtnObj.setData(rtnMap);
				rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
		

		//查询与所有人的会话列表以及未读消息数目
		@GET
		@Path("/sessionlist")
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj getAllSession(){
			RtnObj rtnObj = new RtnObj();
			 try{
				List<FrontMessage> messageList = 
						messageSer.getAllSession(getCurrUserId());
				rtnObj.setData(messageList);
				 rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
		
		//查询与某一个人的消息会话列表
		@GET
		@Path("/onesession")
		@Produces(MediaType.APPLICATION_JSON)
		public RtnObj getOneSession(@QueryParam("userId") Integer userId){
			RtnObj rtnObj = new RtnObj();
			 try{
				List<Message> messageList = 
						messageSer.getOneSession(getCurrUserId(),userId);
				rtnObj.setData(messageList);
				 rtnObj.setErrCode(CommConstant.ERRC_SUCCESS);
			 }catch(Exception e){
				 e.printStackTrace();
				 logger.error("cause by :",e);
				rtnObj.setErrCode(CommConstant.ERRC_SERVERERR);
			 }
			 return rtnObj;
		}
}
