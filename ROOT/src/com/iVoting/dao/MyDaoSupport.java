package com.iVoting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
//public class MyDaoSupport extends SqlMapClientDaoSupport {
public class MyDaoSupport {
//	@Autowired
//	public SqlMapClient oracleSqlMapClient;
//
//	public SqlMapClient getOracleSqlMapClient() {
//		return oracleSqlMapClient;
//	}
//
//	public void setOracleSqlMapClient( SqlMapClient oracleSqlMapClient ) {
//		this.oracleSqlMapClient = oracleSqlMapClient;
//	}
//
//	@Autowired
//	public SqlMapClient iowSqlMapClient;
//
//	public SqlMapClient getIowSqlMapClient() {
//		return iowSqlMapClient;
//	}
//
//	public void setIowSqlMapClient( SqlMapClient iowSqlMapClient ) {
//		this.iowSqlMapClient = iowSqlMapClient;
//	}
	   
	@Autowired
	public SqlMapClient mySqlMapClient;

	public SqlMapClient getMySqlMapClient() {
//		try {
//			String current = new java.io.File( "." ).getCanonicalPath();
//	        System.out.println("Current dir:"+current);
//	        URL url = getClass().getResource("user.xml");
//	        System.out.print(url.getPath());
//	        File file = new File(url.getPath());
//			Reader rd = Resources.getResourceAsReader("com/iVoting/dao/user.xml");
//			mySqlMapClient = SqlMapClientBuilder.buildSqlMapClient(rd);
//			System.out.println( mySqlMapClient.queryForList( "getUser" ) );
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		return mySqlMapClient;
	}

	public void setMySqlMapClient( SqlMapClient mySqlMapClient ) {
		this.mySqlMapClient = mySqlMapClient;
	}
}
