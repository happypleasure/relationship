package com.hyf.prince.domain;

import java.util.Date;

public class HyfFile {
	
	private Integer fileId ; 
	private String fileName;
	private String filePath;
	private String fileExt ; 
	private Long fileSize;
	private Date uploadTime;
	private Integer uploadUserId;
	
	
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(Integer uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
}
