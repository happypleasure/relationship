package com.hyf.core.domain;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RtnObj {
	private Object data; 
	private String errCode ;
	private String errMsg;
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errorCode) {
		this.errCode = errorCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
