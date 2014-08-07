package com.hyf.prince.dao;

import com.hyf.core.exception.DataAccessException;
import com.hyf.prince.domain.LoginChange;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.domain.UserInfo;
import com.hyf.prince.domain.UserToken;

public interface IUserDao {

	public String isExist(String registerAccount) throws DataAccessException;
	
	public Integer register(UserBase userBase) throws DataAccessException;
	
	public void updateUserBase(UserBase userBase) throws DataAccessException;
	
	public Integer hasUserInfo(Integer userId) throws DataAccessException;

	public void insertUserInfo(UserInfo userInfo) throws DataAccessException;

	public void updateUserInfo(UserInfo userInfo) throws DataAccessException;

	public void insertLoginChange(LoginChange loginChange)throws DataAccessException;

	public UserBase getUserBaseById(Integer userId) throws DataAccessException;

	public UserInfo getUserInfoById(Integer userId) throws DataAccessException;

	public UserBase login(UserBase userBase) throws DataAccessException;

	public void updateLoginChange(LoginChange lc) throws DataAccessException;

	public void inserToken(UserToken ut)throws DataAccessException;

	public void deleteToken(Integer userId)throws DataAccessException;

	public void updatePhotoPath(UserBase userBase)throws DataAccessException;

	public UserToken getUserTokenById(String uriToken)throws DataAccessException;

	public String getUserPhotoPath(Integer photoId) throws DataAccessException;

}
