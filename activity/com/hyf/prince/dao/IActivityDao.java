package com.hyf.prince.dao;

import java.util.List;

import com.hyf.core.exception.DataAccessException;
import com.hyf.prince.domain.Activity;
import com.hyf.prince.domain.ActivityAttach;
import com.hyf.prince.domain.ActivityComment;
import com.hyf.prince.domain.ActivityMember;
import com.hyf.prince.domain.UserBase;

public interface IActivityDao {

	public void insertActivity(Activity activity) throws DataAccessException;

	public void insertMember(ActivityMember am) throws DataAccessException;

	public void updateActivity(Activity activity) throws DataAccessException;

	public void addZanNum(Integer activityId) throws DataAccessException;

	public void insertComment(ActivityComment activityComment)throws DataAccessException;

	public Activity getDetailById(Integer activityId) throws DataAccessException;

	public Integer getCommentNum(Integer activityId)throws DataAccessException;

	public Integer getAttachNum(Integer activityId)throws DataAccessException;

	public Integer getMemberNum(Integer activityId)throws DataAccessException;

	public List<UserBase> getActivityMembers(Integer activityId) throws DataAccessException;

	public List<ActivityComment> getActivityComments(Integer activityId) throws DataAccessException;

	public List<ActivityAttach> getActivityAttach(Integer activityId) throws DataAccessException;

	public List<Activity> getArroundActivitys(Activity activity) throws DataAccessException;

	public List<Activity> getSponsorActivitys(Integer currUserId) throws DataAccessException;

	public List<Activity> getMemberActivitys(Integer currUserId) throws DataAccessException;


}
