package com.hyf.prince.service;

import com.hyf.core.exception.ServiceException;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.domain.UserDetail;
import com.hyf.prince.domain.UserInfo;

public interface IUserService {
		public UserBase register(UserDetail userDetail) throws ServiceException;
		
		public UserDetail updateUserInfo(UserDetail userDetail) throws ServiceException;

		public UserBase getUserBaseById(Integer userId) throws ServiceException;

		public UserInfo getUserInfoById(Integer userId) throws ServiceException;

		public UserBase login(UserDetail userDetail) throws ServiceException;

		public String getUserPhotoPath(Integer photoId)throws ServiceException;

		public void updatePhotoPath(UserBase userBase)throws ServiceException;

		public void quitSystem(Integer currUserId) throws ServiceException;

}
