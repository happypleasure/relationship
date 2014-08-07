package com.hyf.prince.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageLine {
	private Integer consistId;
	private Integer userId;
	private Date deleteLine;
	public Integer getConsistId() {
		return consistId;
	}
	public void setConsistId(Integer consistId) {
		this.consistId = consistId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getDeleteLine() {
		return deleteLine;
	}
	public void setDeleteLine(Date deleteLine) {
		this.deleteLine = deleteLine;
	}
}
