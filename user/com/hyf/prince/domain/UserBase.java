package com.hyf.prince.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.hyf.core.domain.BaseDomain;

@XmlRootElement
public class UserBase extends BaseDomain{
		private Integer userId;
		private String registerAccount;
		private String username;
		private String password;
		private Integer age;
		private Integer sex;
		private String photoPath;
		private Integer photoId;
		private String signature;
		private Date registerTime;
		private String token;
		
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
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
		public String getRegisterAccount() {
			return registerAccount;
		}
		public void setRegisterAccount(String registerAccount) {
			this.registerAccount = registerAccount;
		}
		public Date getRegisterTime() {
			return registerTime;
		}
		public void setRegisterTime(Date registerTime) {
			this.registerTime = registerTime;
		}
		public Integer getPhotoId() {
			return photoId;
		}
		public void setPhotoId(Integer photoId) {
			this.photoId = photoId;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
		@Override
		public int hashCode() {
			return this.userId.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == this){
				return true;
			}
			if(obj == null){
				return false;
			}
			if(obj instanceof UserBase){
				final UserBase uObj = (UserBase) obj;
				return this.userId.equals(uObj.getUserId());
			}
			return false;
		}
}
