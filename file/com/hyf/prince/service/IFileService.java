package com.hyf.prince.service;

import org.apache.commons.fileupload.FileItem;


public interface IFileService {

	String uploadUserPhoto(FileItem fileItem, String photoRootPath, String photoUrlPart, Integer currUserId)throws Exception;

	String uploadActivityAttach(FileItem fileItem, String attachRootPath,
			String attachUrlPart, Integer currUserId,Integer activityId) throws Exception;
	
}
