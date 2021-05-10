package com.iVoting.dao;

import com.iVoting.dto.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IUserDAO {
	List<Map<String, Object>> getUser( Map<String, Object> map ) throws Exception;
	
	int updateUser( Map<String, Object> map ) throws Exception;
	
	int insertUser( UserDTO dto ) throws Exception;
	
	int insertUserRole( UserDTO dto ) throws Exception;
	

//	List<Map<String, Object>> getIow( Map<String, Object> map ) throws Exception;

}
