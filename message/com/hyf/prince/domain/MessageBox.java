package com.hyf.prince.domain;



public class MessageBox {
	 private Integer messageId;
	 private Integer userId;
	 private Integer consistId;
	 private Boolean readFlag;
	 
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getConsistId() {
		return consistId;
	}
	public void setConsistId(Integer consistId) {
		this.consistId = consistId;
	}
	public Boolean getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Boolean readFlag) {
		this.readFlag = readFlag;
	}
}
