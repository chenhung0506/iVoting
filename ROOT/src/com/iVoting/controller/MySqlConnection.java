//package com.iVoting.controller;
//
//public class MySqlConnection {
//	String url = "jdbc:mysql://localhost:3306/javabase";
//	String username = "java";   	//範例，使用者ID
//	String password = "d$7hF_r!9Y"	//範例，使用者密碼
//	Connection con = null;
//	Statement stat = null; 	//執行,傳入之sql為完整字串 
//	ResultSet rs = null; 	//結果集 
//	PreparedStatement pst = null; 
//	try {
//	    System.out.println("Connecting database...");
//	    con = DriverManager.getConnection(url, username, password);
//	    System.out.println("Database connected!");
//	} catch (SQLException e) {
//	    throw new RuntimeException("Cannot connect the database!", e);
//	} finally {
//	    System.out.println("Closing the connection.");
//	    if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
//	}
//}
