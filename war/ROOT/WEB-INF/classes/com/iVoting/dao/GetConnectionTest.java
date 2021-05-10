package com.iVoting.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.iVoting.utils.ConnectionUtils;

public class GetConnectionTest {
	private static final Logger log = Logger.getLogger( GetConnectionTest.class );

	public static void testConnection() {
		String sqlcmd = "select * from Community.USER_";
		try {
			Connection con = ConnectionUtils.getMSSQLConnection();
			PreparedStatement pstmt = con.prepareStatement( sqlcmd );
			// ResultSet rs = stmt.executeQuery(sqlcmd);
//			pstmt.setString( 1, "0120486003" );
//			pstmt.setString( 2, "A000000000" );
			ResultSet rs = pstmt.executeQuery();
			while ( rs.next() ) {
				log.info( rs.getArray( 0 ) );
				log.info( rs.getString( 1 ) );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
