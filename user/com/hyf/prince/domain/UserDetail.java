package com.hyf.prince.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class UserDetail {
	private Integer userId;
	private String registerAccount;
	private String username;
	private String password;
	private Integer age;
	private Integer sex;
	private String photoPath;
	private String signature;
	private String   mailAddress;
	private String   phone;
	private String   address;
	private String  interest;
	private String  qqCount;
	private String  job;
	private Date   birthday;
	private Date   lastLoginTime;
	private  Double loginCoordX;
	private  Double loginCoordY;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRegisterAccount() {
		return registerAccount;
	}
	public void setRegisterAccount(String registerAccount) {
		this.registerAccount = registerAccount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getQqCount() {
		return qqCount;
	}
	public void setQqCount(String qqCount) {
		this.qqCount = qqCount;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
