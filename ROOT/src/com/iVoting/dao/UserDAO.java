package com.iVoting.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.iVoting.dto.UserDTO;

@Component
public class UserDAO extends MyDaoSupport implements IUserDAO {

	private static final Logger log = Logger.getLogger( UserDAO.class );

	@SuppressWarnings( "unchecked" )
	public List<Map<String, Object>> getUser( Map<String, Object> map ) throws Exception {
		log.info(":::"+map);
		return getMySqlMapClient().queryForList( "user.getUser", map );
	}

	@SuppressWarnings( "unchecked" )
	public List<Map<String, Object>> getMySql( Map<String, Object> map ) throws Exception {
		return getMySqlMapClient().queryForList( "user.getMySql", map );
	}

	public int updateUser( Map<String, Object> map ) throws Exception {
		return getMySqlMapClient().update( "user.updateUser", map );
	}

	public int insertUser( UserDTO dto ) throws Exception {
		return getMySqlMapClient().update( "user.insertUser", dto );
	}

	public int insertUserRole( UserDTO dto ) throws Exception {
		return getMySqlMapClient().update( "user.insertUserRole", dto );
	}
}
