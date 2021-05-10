package com.iVoting.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.iVoting.utils.FormatUtils;

@SuppressWarnings( "unchecked" )
public class Constants {
//	RBAC
//	Role-based access control

	public final static Map<String, ArrayList<String>> ROLE_MAP = new HashMap<String, ArrayList<String>>();

	public static ArrayList<String> ROLE_0_LIST = new ArrayList<String>();

	public static ArrayList<String> ROLE_1_LIST = new ArrayList<String>();

	public static ArrayList<String> ROLE_2_LIST = new ArrayList<String>();

	public static ArrayList<String> ROLE_3_LIST = new ArrayList<String>();

	public static ArrayList<String> ROLE_4_LIST = new ArrayList<String>();

	public static String COOKIE_PWD = "MD5ENCODE";

	static {
		// "/showJson", "/show"
		ROLE_3_LIST = FormatUtils.arraysAsStrList( "/show" );
		ROLE_2_LIST = FormatUtils.arraysAsStrList( "/addEmployee" );
		ROLE_1_LIST = FormatUtils.arraysAsStrList( "/deleteEmployee" );
		ROLE_0_LIST = FormatUtils.arraysAsStrList( ROLE_1_LIST, ROLE_2_LIST, ROLE_3_LIST );
		ROLE_MAP.put( "ROLE_0", ROLE_0_LIST );
		ROLE_MAP.put( "ROLE_1", ROLE_1_LIST );
		ROLE_MAP.put( "ROLE_2", ROLE_2_LIST );
		ROLE_MAP.put( "ROLE_3", ROLE_3_LIST );
	}

	public static void main( String[] str ) {
		for ( Map.Entry<String, ArrayList<String>> entry : ROLE_MAP.entrySet() ) {
			System.out.println( entry.getKey() + "/" + entry.getValue() );
		}

	}
}
