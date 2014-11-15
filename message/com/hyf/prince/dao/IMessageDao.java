package com.hyf.prince.dao;

import java.util.List;

import com.hyf.core.exception.DataAccessException;
import com.hyf.prince.domain.Message;
import com.hyf.prince.domain.MessageBox;
import com.hyf.prince.domain.MessageLine;
import com.hyf.prince.result.FrontMessage;


public interface IMessageDao {

	List<Message> getOneSession(MessageBox messageBox)throws DataAccessException;

	void insertMessage(Message message) throws DataAccessException;

	void insertMessageBox(MessageBox mb)throws DataAccessException;

	void updateReadFlag(MessageBox mb)throws DataAccessException;

	void setDelete(MessageBox mb)throws DataAccessException;

	void insertMessageLine(MessageLine ml)throws DataAccessException;

	Integer getTotalUnReadNum(Integer currUserId) throws DataAccessException;

	List<FrontMessage> getAllSession(Integer userId)  throws DataAccessException;

	void deleteMessageBox(MessageBox mb) throws DataAccessException;

	Integer getCountByMessageId(Integer messageId) throws DataAccessException;

	void deleteMessageById(Integer messageId) throws DataAccessException;

	Integer getOneSessionUnReadNum(MessageBox mb) throws DataAccessException;

	List<Integer> getUserIdBySession(Integer consistId) throws DataAccessException;

	MessageLine getDeleteLine(MessageLine ml) throws DataAccessException;

	void updateDeleteLine(MessageLine ml) throws DataAccessException;

}
