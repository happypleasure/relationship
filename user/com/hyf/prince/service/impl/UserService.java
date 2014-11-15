package com.hyf.prince.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hyf.core.domain.CommConstant;
import com.hyf.core.exception.DataAccessException;
import com.hyf.core.exception.ServiceException;
import com.hyf.core.filter.HyfCache;
import com.hyf.core.util.MurmurHash;
import com.hyf.core.util.Util;
import com.hyf.prince.dao.IUserDao;
import com.hyf.prince.domain.LoginChange;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.domain.UserDetail;
import com.hyf.prince.domain.UserInfo;
import com.hyf.prince.domain.UserToken;
import com.hyf.prince.service.IUserService;

@Service("userService")
public class UserService implements IUserService{
	@Resource
	private IUserDao  iUserDao;
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);
	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public UserBase register(UserDetail userDetail) throws ServiceException{
		UserBase result = new UserBase();
		try{
			String registerAccount = userDetail.getRegisterAccount();
			String password = userDetail.getPassword();
			if(!StringUtils.hasText(registerAccount)){
				result.setErrorCode( CommConstant.ACCOUNTERROR);
				return result;
			};
			if(!StringUtils.hasText(password) && password.length() < 6){
				result.setErrorCode( CommConstant.PWDERROR);
				return result;
			};
			String accountTmp = iUserDao.isExist(registerAccount);
			if(accountTmp != null){
				result.setErrorCode( CommConstant.USEREXIST);
				return result;
			}
			MurmurHash hashSecr = new MurmurHash();
			String secartPwd = hashSecr.hash(password);
			UserBase userBase = new UserBase();
			userBase.setRegisterAccount(registerAccount);
			userBase.setPassword(secartPwd);
			userBase.setRegisterTime(new Date());
			iUserDao.register(userBase);
			LoginChange loginChange = new LoginChange(); 
			loginChange.setLastLoginTime(new Date());
			loginChange.setUserId(userBase.getUserId());
			loginChange.setLoginCoordX(userDetail.getLoginCoordX());
			loginChange.setLoginCoordY(userDetail.getLoginCoordY());
			iUserDao.insertLoginChange(loginChange);
			result.setUserId(userBase.getUserId());
			String token = this.setToken(userBase.getUserId());
			result.setToken(token);
			result.setErrorCode(CommConstant.ERRC_SUCCESS);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hyf.prince.service.IUserService#updateUserInfo(com.hyf.prince.domain.UserDetail)
	 */
	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public UserDetail updateUserInfo(UserDetail userDetail) throws ServiceException {
		try{
			UserBase userBase = new UserBase();
			BeanUtils.copyProperties(userDetail, userBase);
			iUserDao.updateUserBase(userBase);
			UserInfo userInfo = new UserInfo();
			BeanUtils.copyProperties(userDetail, userInfo);
			Integer userId = iUserDao.hasUserInfo(userDetail.getUserId());
			if(userId != null){
				iUserDao.updateUserInfo(userInfo);
			}else{
				iUserDao.insertUserInfo(userInfo);	
			}
			return userDetail;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public UserBase getUserBaseById(Integer userId) throws ServiceException {
		try{
			UserBase userBase = iUserDao.getUserBaseById(userId);
			userBase.setPhotoPath(getUserPhotoPath(userBase.getPhotoId()));
			return userBase;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}
	
	@Override
	public UserInfo getUserInfoById(Integer userId) throws ServiceException {
		try{
			UserInfo userInfo = iUserDao.getUserInfoById(userId);
			return userInfo;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}


	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public UserBase login(UserDetail userDetail) throws ServiceException {
		UserBase userBase = new UserBase();
		try{
			String password = userDetail.getPassword();
			String registerAccount = userDetail.getRegisterAccount();
			if(password == null){
				userBase.setErrorCode(CommConstant.PWDERROR);
				return userBase;
			}
			if(!StringUtils.hasText(registerAccount)){
				userBase.setErrorCode( CommConstant.ACCOUNTERROR);
				return userBase;
			};
			userBase.setPassword(Util.getSecreatPwd(password));
			userBase.setRegisterAccount(registerAccount);
			UserBase result = iUserDao.login(userBase);
			if(result == null){
				userBase.setErrorCode( CommConstant.ACCOUNTERROR);
				return userBase;
			}
			//更新登录的坐标信息
			LoginChange lc = new LoginChange();
			lc.setLoginCoordX(userDetail.getLoginCoordX());
			lc.setLoginCoordY(userDetail.getLoginCoordY());
			lc.setLastLoginTime(new Date());
			lc.setUserId(result.getUserId());
			iUserDao.updateLoginChange(lc);
			String token = this.setToken(result.getUserId());
			result.setToken(token);
			result.setErrorCode(CommConstant.ERRC_SUCCESS);
			return result;
		}catch(Exception e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}


	@Override
	public String getUserPhotoPath(Integer photoId) throws ServiceException {
		try{
			return  iUserDao.getUserPhotoPath(photoId);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}
	
	private String setToken(Integer userId) throws DataAccessException {
		iUserDao.deleteToken(userId);
		UserToken ut = new UserToken();
		String token = Util.getRandomString(20);
		ut.setToken(token);
		ut.setUserId(userId);
		iUserDao.inserToken(ut);
		return token;
	}

	@Override
	public void updatePhotoPath(UserBase userBase) throws ServiceException {
		try{
			 iUserDao.updatePhotoPath(userBase);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void quitSystem(Integer currUserId) throws ServiceException {
		try{
			if(currUserId  !=  null){
				iUserDao.deleteToken(currUserId);
				HyfCache.removeKey("loginUser", currUserId.toString());
			}
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

}
