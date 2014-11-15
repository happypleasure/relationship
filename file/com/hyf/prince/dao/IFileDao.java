package com.hyf.prince.dao;

import com.hyf.core.exception.DataAccessException;
import com.hyf.prince.domain.ActivityAttach;
import com.hyf.prince.domain.HyfFile;

public interface IFileDao {

	public void insertFile(HyfFile file) throws DataAccessException;

	public void insertFileAttach(ActivityAttach activityAttach)throws DataAccessException;

	public String getPathByAttachId(Integer attachId)throws DataAccessException;

	public void updateFile(HyfFile file)throws DataAccessException;

}
