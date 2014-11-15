package com.hyf.prince.result;

import java.util.Date;

import com.hyf.prince.domain.UserBase;

public class FrontMessage {
	private Integer consistId ; 
	private String messageContent;
	private UserBase userBase;
	private Date sendTime;
	private Integer unReadNum;

	public Integer getConsistId() {
		return consistId;
	}
	public void setConsistId(Integer consistId) {
		this.consistId = consistId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public UserBase getUserBase() {
		return userBase;
	}
	public void setUserBase(UserBase userBase) {
		this.userBase = userBase;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getUnReadNum() {
		return unReadNum;
	}
	public void setUnReadNum(Integer unReadNum) {
		this.unReadNum = unReadNum;
	}
}
