package com.iVoting.dao;

import com.iVoting.dto.LmsApiKeysVO;

public interface ILmsApiKeysDAO {
	public abstract LmsApiKeysVO getLmsApiKeyData(String sourceName)throws Exception; 
}
