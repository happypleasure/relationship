package com.hyf.prince.service;

import java.util.List;
import java.util.Map;

import com.hyf.core.exception.ServiceException;
import com.hyf.prince.domain.Activity;
import com.hyf.prince.domain.ActivityAttach;
import com.hyf.prince.domain.ActivityComment;
import com.hyf.prince.domain.ActivityMember;
import com.hyf.prince.domain.UserBase;

public interface IActivityService {

	Activity publishActivity(Activity activity)throws ServiceException;

	Activity updateActivity(Activity activity)throws ServiceException;

	void joinActivity(ActivityMember am)throws ServiceException;

	void likeActivity(Activity activity) throws ServiceException;

	void commentActivity(ActivityComment activityComment) throws ServiceException;

	Activity getDetailById(Integer activityId) throws ServiceException;

	List<UserBase> getActivityMembers(Integer activityId) throws ServiceException;

	Map<String,Object> getActivityComments(Integer activityId) throws ServiceException;

	List<ActivityAttach> getActivityAttachs(Integer activityId) throws ServiceException;

	List<Activity> getArroundActivitys(Double coorX, Double coorY , Double nearRange) throws ServiceException;

	List<Activity> getSponsorActivitys(Integer currUserId) throws ServiceException;

	List<Activity> getMemberActivitys(Integer currUserId) throws ServiceException;

}
