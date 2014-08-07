package com.hyf.prince.domain;

import java.util.Date;

public class LoginChange {
	private Integer userId;
	private Date   lastLoginTime;
	private  Double loginCoordX;
	private  Double loginCoordY;
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Double getLoginCoordX() {
		return loginCoordX;
	}
	public void setLoginCoordX(Double loginCoordX) {
		this.loginCoordX = loginCoordX;
	}
	public Double getLoginCoordY() {
		return loginCoordY;
	}
	public void setLoginCoordY(Double loginCoordY) {
		this.loginCoordY = loginCoordY;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
