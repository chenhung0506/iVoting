package com.iVoting.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.iVoting.filter.UrlFilter;

@Component
public class ConnectionUtils {
	private static final Logger log = Logger.getLogger( ConnectionUtils.class );

	public static Connection getMSSQLConnection() throws Exception {
		Connection sqlConn = null;
		int i = 0;
		boolean retry = true;

		if ( retry ) {
			try {
				log.info(":::ConnectionUtils");
//				Class.forName( "net.sourceforge.jtds.jdbc.Driver" );
				Class.forName( "com.mysql.cj.jdbc.Driver" );				
				sqlConn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/Community?characterEncoding=utf8&useSSL=false", "root", "123456" );
//				sqlConn = DriverManager.getConnection( "jdbc:mysql://35.222.204.128:3306/Community", "root", "123456" );
				log.info( "JDBC OK!" );
			} catch ( SQLException sqle ) {
				log.info( "JDBC SQL ERROR!" + sqle );
				throw sqle;
			} catch ( Exception e ) {
				log.info( "JDBC Exception!" + e );
				throw e;
			}
		}
		return sqlConn;
	}
}
