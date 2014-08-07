package com.hyf.prince.service.impl;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hyf.core.exception.DataAccessException;
import com.hyf.core.util.Util;
import com.hyf.prince.dao.IFileDao;
import com.hyf.prince.domain.ActivityAttach;
import com.hyf.prince.domain.HyfFile;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.service.IFileService;
import com.hyf.prince.service.IUserService;

@Service("fileService")
public class FileService implements IFileService{

	@Resource
	private IFileDao iFileDao;
	@Resource
	private IUserService userService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(FileService.class);
	
	@Override
	public String uploadUserPhoto(FileItem fileItem,String photoRootPath,String photoUrlPart, Integer currUserId) throws Exception{
		String photoUrl = "";
		try{
			String fileName = fileItem.getName();
			fileName = fileName.substring(
					fileName.lastIndexOf("/") + 1);
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			Long fileSize = fileItem.getSize();
			File photoRootDir = new File(photoRootPath + "\\userphoto");
			if(!photoRootDir.exists()){
				photoRootDir.mkdir();
			}
			File photoFile = new File(photoRootPath + "\\userphoto\\" + currUserId + '.' + fileExt);
			fileItem.write(photoFile);
			photoUrl = photoUrlPart + 
					"/attach/userphoto/"+currUserId + '.' + fileExt;
			HyfFile file = new HyfFile();
			file.setFileExt(fileExt);
			file.setFileName(fileName);
			file.setFilePath(photoUrl);
			file.setFileSize(fileSize);
			file.setUploadTime(new Date());
			file.setUploadUserId(currUserId);
			//获取用户的图片标识号
			UserBase userBase = userService.getUserBaseById(currUserId);
			if(userBase != null && userBase.getPhotoId() != null){
				file.setFileId(userBase.getPhotoId());
				iFileDao.updateFile(file);
			}else{
				iFileDao.insertFile(file);
			}
			userBase.setPhotoId(file.getFileId());
			userService.updatePhotoPath(userBase);
		}catch(DataAccessException e){
			logger.error("上传头像发生异常");
			logger.error("cause by:",e);
		}
		return photoUrl;
	}

	@Override
	public String uploadActivityAttach(FileItem fileItem,
			String attachRootPath, String attachUrlPart, Integer currUserId,Integer activityId) throws Exception {
		String attachUrl = "";
		try{
			String fileName = fileItem.getName();
			fileName = fileName.substring(
					fileName.lastIndexOf("/") + 1);
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			Long fileSize = fileItem.getSize();
			File photoRootDir = new File(attachRootPath + "\\activity\\"+activityId);
			if(!photoRootDir.exists()){
				photoRootDir.mkdir();
			}
			String randomNum = Util.getRandomString(10);
			File attachFile = new File(attachRootPath + "\\activity\\" 
					+ activityId+"\\"+ randomNum + '.' + fileExt);
			fileItem.write(attachFile);
			attachUrl = attachUrlPart + 
					"/attach/activity/"+activityId+"/"+randomNum+ '.' + fileExt;
			HyfFile file = new HyfFile();
			file.setFileExt(fileExt);
			file.setFileName(fileName);
			file.setFilePath(attachUrl);
			file.setFileSize(fileSize);
			file.setUploadTime(new Date());
			file.setUploadUserId(currUserId);
			iFileDao.insertFile(file);
			
			ActivityAttach activityAttach = new ActivityAttach();
			activityAttach.setActivityId(activityId);
			activityAttach.setAttachId(file.getFileId());
			activityAttach.setUserId(currUserId);
			activityAttach.setUploadTime(new Date());
			iFileDao.insertFileAttach(activityAttach);
		}catch(DataAccessException e){
			logger.error("上传头像发生异常");
			logger.error("cause by:",e);
		}
		return attachUrl;
	}

}
