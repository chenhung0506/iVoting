package com.iVoting.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import com.ibatis.sqlmap.client.SqlMapClient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;

import com.iVoting.dao.GetConnectionTest;
import com.iVoting.utils.AesUtils;
import com.iVoting.utils.ConnectionUtils;

public class MainTest {
	private static final Logger log = Logger.getLogger( MainTest.class );

	private static SqlMapClient client = null;

	public static void main( String[] args ) {

		List<String> listParm = Arrays.asList( "String", "String" );
		List<Object> pList = new ArrayList<Object>();
		pList.add("String1");
		pList.add("String2");
		log.info( "pList: " + pList );

		try {
			log.info( "測試開始" );
			doQuery(pList);
//			testGetConnection();
			log.info( "測試結束" );
		} catch ( Exception e0 ) {
			log.info( "", e0 );
		}
	}
	 public static void doQuery( List params) throws Exception {
		 PreparedStatement pStmt = null;
		 Connection connDo = ConnectionUtils.getMSSQLConnection();

		 pStmt = connDo.prepareStatement("select ? * from ?");
	     for (int idx = 0; idx < params.size(); idx++) {
	    	 pStmt.setObject(idx + 1, params.get(idx));
	       }
	 }
	public static void testGetConnection() throws Exception {

		String sqlcmd12 = "";
		PreparedStatement ps = null;
		Connection con1 = null;
//		con1 = client.getDataSource().getConnection();

		String sql_1 = "select * from Community.LOTTERY_MEMBER where zip = ?";

		sqlcmd12 = "SELECT TOP ? destcategory as DESTCATEGORY,msgdata as MSGDATA,ordertime as ORDERTIME,serialno as SERIALNO,destno as DESTNO ,statusflag as STATUSFLAG , mid as MID , statustime as STATUSTIME " + " FROM MsgInfo WHERE serialno NOT IN ( "
				+ " SELECT TOP ? serialno FROM MsgInfo where  destname= ? and Filler <>'Y' AND StatusFlag <> '0'  and statustime > ? ORDER BY ORDERTIME DESC ,serialno ASC  ) " + " and destname=? and Filler <>'Y' AND StatusFlag <> '0' and statustime > ? ORDER BY ORDERTIME DESC,serialno ASC ";

		con1 = ConnectionUtils.getMSSQLConnection();
		PreparedStatement pstmt_1 = con1.prepareStatement( sql_1 );
//		PreparedStatement pstmt_2 = con1.prepareStatement( sqlcmd12 );

//		pstmt_1.setString( 1, Integer.toString( 1 ) );
//		pstmt_1.setString( 2, Integer.toString( 1 ) );
//		pstmt_1.setString( 3, "111" );
//		pstmt_1.setString( 4, "111" );
//		pstmt_1.setString( 5, "111" );
//		pstmt_1.setString( 6, "111" );
//
//		System.out.println( pstmt_1.toString() );

		pstmt_1.setString( 1, "100" );
		ResultSet rs = pstmt_1.executeQuery();

		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();

		while ( rs.next() ) {
			for ( int i = 1; i <= columnCount; i++ ) {
				String columnName = md.getColumnLabel( i );
				String colValue = rs.getString( columnName );
				log.info( "columnName: " + columnName + "colValue" + colValue );
			}
		}
	}

	public static void testAesUtils() throws Exception {

		System.out.println( "測試開始" );
		AesUtils aesUtils;
		System.out.println( AesUtils.encryptData( "10" ) );
		System.out.println( AesUtils.encryptData( "11" ) );
		System.out.println( AesUtils.decryptData( "8nDPjQIfq9/n11kvv6E3nQ==" ) );
		System.out.println( AesUtils.decryptData( "8nDPjQIfq9/n11kvv6E3nQ==" ) );

	}
}
