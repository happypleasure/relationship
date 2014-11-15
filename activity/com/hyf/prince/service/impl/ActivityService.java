package com.hyf.prince.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyf.core.domain.CommConstant;
import com.hyf.core.exception.DataAccessException;
import com.hyf.core.exception.ServiceException;
import com.hyf.prince.dao.IActivityDao;
import com.hyf.prince.dao.IFileDao;
import com.hyf.prince.domain.Activity;
import com.hyf.prince.domain.ActivityAttach;
import com.hyf.prince.domain.ActivityComment;
import com.hyf.prince.domain.ActivityMember;
import com.hyf.prince.domain.UserBase;
import com.hyf.prince.service.IActivityService;
import com.hyf.prince.service.IUserService;

@Service("activityService")
public class ActivityService implements IActivityService{
	
	@Resource
	private IActivityDao iActivityDao;
	@Resource
	private IUserService userSer;
	@Resource
	private IFileDao iFileDao;
	private static final Logger logger = LoggerFactory
			.getLogger(ActivityService.class);
	
	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public Activity publishActivity(Activity activity) throws ServiceException {
		try{
			activity.setPublishTime(new Date());
			activity.setIsDelete(false);
			activity.setZanNum(0);
			iActivityDao.insertActivity(activity);
			//插入活动参与人
			ActivityMember am = new ActivityMember();
			am.setActivityId(activity.getActivityId());
			am.setUserId(activity.getSponsorId());
			am.setRole(CommConstant.SPONSOR);
			iActivityDao.insertMember(am);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
		return activity;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public Activity updateActivity(Activity activity) throws ServiceException {
		try{
			iActivityDao.updateActivity(activity);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
		return activity;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void joinActivity(ActivityMember am) throws ServiceException {
		try{
			am.setRole(CommConstant.MEMBER);
			iActivityDao.insertMember(am);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void likeActivity(Activity activity) throws ServiceException {
		try{
			iActivityDao.addZanNum(activity.getActivityId());
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Exception.class)
	public void commentActivity(ActivityComment activityComment)
			throws ServiceException {
		try{
			activityComment.setCreateTime(new Date());
			iActivityDao.insertComment(activityComment);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Activity getDetailById(Integer activityId) throws ServiceException {
		try{
			Activity activity = iActivityDao.getDetailById(activityId);
			Integer commentNum = iActivityDao.getCommentNum(activityId);
			Integer attachNum = iActivityDao.getAttachNum(activityId);
			Integer memberNum = iActivityDao.getMemberNum(activityId);
			activity.setCommentNum(commentNum);
			activity.setAttachNum(attachNum);
			activity.setMemberNum(memberNum);
			return activity;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<UserBase> getActivityMembers(Integer activityId)
			throws ServiceException {
		try{
			List<UserBase> members = iActivityDao.getActivityMembers(activityId);
			if(members != null && members.size() > 0){
				for (UserBase userBase : members) {
					userBase.setPhotoPath(userSer.getUserPhotoPath(userBase.getPhotoId()));
				}
			}
			return members;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<String,Object> getActivityComments(Integer activityId)
			throws ServiceException {
		try{
			Map<String,Object> mapResult = new HashMap<String,Object>();
			List<ActivityComment> comments = iActivityDao.getActivityComments(activityId);
			List<UserBase> commenters = new ArrayList<UserBase>();
			if(comments != null && comments.size() > 0){
				for (ActivityComment comment : comments) {
					Integer userId = comment.getUserId();
					UserBase ub = userSer.getUserBaseById(userId);
					if(commenters.contains(ub)){
						continue;
					}
					commenters.add(ub);
				}
			}
			mapResult.put("comments", comments);
			mapResult.put("commenters", commenters);
			return mapResult;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ActivityAttach> getActivityAttachs(Integer activityId)
			throws ServiceException {
		try{
			List<ActivityAttach> attachs = iActivityDao.getActivityAttach(activityId);
			if(attachs != null && attachs.size() > 0){
				for (ActivityAttach attach : attachs) {
					Integer attachId = attach.getAttachId();
					String path = getAttachPath(attachId);
					attach.setPath(path);
				}
			}
			return attachs;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	private String getAttachPath(Integer attachId) throws ServiceException {
		try{
			return iFileDao.getPathByAttachId(attachId);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Activity> getArroundActivitys(Double coordX , Double coordY , Double nearRange)
			throws ServiceException {
		try{
			Activity activity = new Activity();
			activity.setCoordX(coordX);
			activity.setCoordY(coordY);
			activity.setNearRange(nearRange);
			List<Activity> list = iActivityDao.getArroundActivitys(activity);
			return list;
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Activity> getSponsorActivitys(Integer currUserId)
			throws ServiceException {
		try{
			return iActivityDao.getSponsorActivitys(currUserId);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Activity> getMemberActivitys(Integer currUserId)
			throws ServiceException {
		try{
			return iActivityDao.getMemberActivitys(currUserId);
		}catch(DataAccessException e){
			logger.error("cause by:",e);
			throw new ServiceException(e);
		}
	}

}
