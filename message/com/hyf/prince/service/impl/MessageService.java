package com.hyf.prince.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyf.core.cometd.MessageCometDServices;
import com.hyf.core.exception.DataAccessException;
import com.hyf.core.exception.ServiceException;
import com.hyf.core.util.Util;
import com.hyf.prince.dao.IMessageDao;
import com.hyf.prince.domain.Message;
import com.hyf.prince.domain.MessageBox;
import com.hyf.prince.domain.MessageLine;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.result.FrontMessage;
import com.hyf.prince.service.IMessageService;
import com.hyf.prince.service.IUserService;

@Service("messageService")
public class MessageService implements IMessageService{
	
	@Resource
	private IMessageDao iMessageDao;
	@Resource
	private IUserService iUserSer;
	@Resource
	private MessageCometDServices messageCometd;
	private static final Logger logger = LoggerFactory
			.getLogger(MessageService.class);
	
	@Override
	public List<Message> getOneSession(Integer currUserId, Integer userId) throws ServiceException {
		try{
			List<Message> messageList = null; 
			Integer consistId = Util.getConsistId(currUserId, userId);
			MessageBox messageBox = new MessageBox();
			messageBox.setConsistId(consistId);
			messageBox.setUserId(currUserId);
			messageList = iMessageDao.getOneSession(messageBox);
			return messageList;
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<FrontMessage> getAllSession(Integer userId) throws ServiceException {
		try{
			List<FrontMessage> latelyMessages = iMessageDao.getAllSession(userId);
			MessageBox mb = new MessageBox();
			for (FrontMessage fmessage : latelyMessages) {
				Integer consistId = fmessage.getConsistId();
				//获取每个会话的未读消息数目
				mb.setUserId(userId);
				mb.setConsistId(consistId);
				Integer unReadNum = iMessageDao.getOneSessionUnReadNum(mb);
				fmessage.setUnReadNum(unReadNum);
				//获取对话人信息
				List<Integer> userIds = iMessageDao.getUserIdBySession(consistId);
				for (Integer oneId : userIds) {
					if(!userId.equals(oneId)){
						UserBase userBase = iUserSer.getUserBaseById(oneId);
						fmessage.setUserBase(userBase);
					}
				}
			}
			return latelyMessages;
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public Message sendMessage(Message message) throws ServiceException {
		try{
			//将消息插入到t_message表中
			Integer sendUserId = message.getSendUserId();
			Integer receiveUserId = message.getReceiveUserId();
			Integer consistId = Util.getConsistId(sendUserId, receiveUserId);
			message.setConsistId(consistId);
			message.setSendTime(new Date());
			iMessageDao.insertMessage(message);
			//插入t_message_box记录
			MessageBox mb = new MessageBox();
			//收件人
			mb.setMessageId(message.getMessageId());
			mb.setConsistId(consistId);
			mb.setUserId(receiveUserId);
			mb.setReadFlag(false);
			iMessageDao.insertMessageBox(mb);
			//发件人
			mb.setUserId(sendUserId);
			mb.setReadFlag(true);
			iMessageDao.insertMessageBox(mb);
			//TODO 推送给前端
			messageCometd.sendMessage(message);
			message.setReadFlag(true);
			return message;
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void updateReadFlag(MessageBox mb) throws ServiceException {
		try{
			mb.setReadFlag(true);
			iMessageDao.updateReadFlag(mb);
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void deleteOneById(MessageBox mb) throws ServiceException {
		try{
			Integer messageId = mb.getMessageId();
			//删除本条消息
			iMessageDao.deleteMessageBox(mb);
			Integer count = iMessageDao.getCountByMessageId(messageId);
			if(count == null || count < 1){
				iMessageDao.deleteMessageById(messageId);
			}
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void deleteByUser(MessageLine ml) throws ServiceException {
		try{
			ml.setDeleteLine(new Date());
			/*
			 * 获取之前的删除线
			 */
			MessageLine existLine = iMessageDao.getDeleteLine(ml);
			if(null != existLine){
				iMessageDao.updateDeleteLine(ml);
			}else{
				iMessageDao.insertMessageLine(ml);
			}
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer getTotalUnReadNum(Integer currUserId) throws ServiceException {
		try{
			return iMessageDao.getTotalUnReadNum(currUserId);
		}catch(DataAccessException e){
			logger.error("cause by :" ,e);
			throw new ServiceException(e);
		}
	}

}
