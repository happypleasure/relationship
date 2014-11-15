package com.hyf.prince.service;

import java.util.List;

import com.hyf.core.exception.ServiceException;
import com.hyf.prince.domain.Message;
import com.hyf.prince.domain.MessageBox;
import com.hyf.prince.domain.MessageLine;
import com.hyf.prince.result.FrontMessage;


public interface IMessageService {

	List<Message> getOneSession(Integer currUserId, Integer userId) throws ServiceException;

	List<FrontMessage> getAllSession(Integer currUserId)throws ServiceException;

	Message sendMessage(Message message)throws ServiceException;

	void deleteOneById(MessageBox mb)throws ServiceException;

	void deleteByUser(MessageLine ml)throws ServiceException;

	Integer getTotalUnReadNum(Integer currUserId)throws ServiceException;

	void updateReadFlag(MessageBox mb)throws ServiceException;

}
