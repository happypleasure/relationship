package com.hyf.prince.domain;

import java.util.Date;

public class Activity {
	private Integer activityId;
	private String    title;
	private Date publishTime;
	private Integer type;
	private String address;
	private Date planStartTime;
	private String description;
	private Integer maxUserNum;
	private Double coordX;
	private Double coordY;
	private Integer sponsorId;
	private String  sponsorName;
	private Boolean isDelete;
	private Integer commentNum;
	private Integer memberNum;
	private Integer attachNum;
	private Integer zanNum;
	private Double nearRange;
	
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMaxUserNum() {
		return maxUserNum;
	}
	public void setMaxUserNum(Integer maxUserNum) {
		this.maxUserNum = maxUserNum;
	}
	public Double getCoordX() {
		return coordX;
	}
	public void setCoordX(Double coordX) {
		this.coordX = coordX;
	}
	public Double getCoordY() {
		return coordY;
	}
	public void setCoordY(Double coordY) {
		this.coordY = coordY;
	}
	public Integer getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(Integer sponsorId) {
		this.sponsorId = sponsorId;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}
	public Integer getAttachNum() {
		return attachNum;
	}
	public void setAttachNum(Integer attachNum) {
		this.attachNum = attachNum;
	}
	public Integer getZanNum() {
		return zanNum;
	}
	public void setZanNum(Integer zanNum) {
		this.zanNum = zanNum;
	}
	public Double getNearRange() {
		return nearRange;
	}
	public void setNearRange(Double nearRange) {
		this.nearRange = nearRange;
	}
}
