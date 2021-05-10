package com.iVoting.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FormatUtils {
	private static final Logger log = Logger.getLogger( FormatUtils.class );

	public static final String DATE_FORMATE = "yyyyMMdd";

	public static final String DATE_FORMATE2 = "yyyy-MM-dd";

	public static final String DATE_TIME_FORMATE = "yyyyMMddHHmmss";

	public static final String DATE_TIME_FORMATE2 = "yyyy-MM-dd HH";

	public static final String DATE_TIME_FORMATE3 = "yyyy-MM-dd HH:mm";

	public static final String DATE_TIME_FORMATE4 = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIMES_FORMATE = "yyyyMMddHHmmssSSS";

	public static final String DIGITAL_0_STR = "0";

	public static final String DIGITAL_1_STR = "1";

	public static final String DIGITAL_2_STR = "2";

	public static final String DIGITAL_3_STR = "3";

	public static final String DIGITAL_4_STR = "4";

	public static final String DIGITAL_5_STR = "5";

	public static final String DIGITAL_6_STR = "6";

	public static final String DIGITAL_7_STR = "7";

	public static final String DIGITAL_8_STR = "8";

	public static final String DIGITAL_9_STR = "9";

	public static final String Y_STR = "Y";

	public static final String N_STR = "N";

	public static final String B_STR = "B";

	public static final String Default_STR = "Default";

	public static final String UTF8_STR = "UTF-8";

	public static final String xml_extension = ".xml";

	public static final String pdf_extension = ".pdf";

	public static final String txt_extension = ".txt";

	public static final String xlsx_extension = ".xlsx";

	public static final String json_extension = ".json";

	public static final String BLANK_STR = "";

	public static final String ONE_BLANK_STR = " ";

	public static final String UNDERLINE_STR = "_";

	public static final String DASH_STR = "-";

	public static final String EQUAL_STR = "=";

	public static final String POUND_STR = "#";

	public static final String THREE_POUND_STR = "###";

	public static final String STAR_STR = "*";

	public static final String SEMICOLON_STR = ";";

	public static final String COLON_STR = ":";

	public static final String AMPERSAND_STR = "&";

	public static final String SLASH_STR = "/";

	public static final String BACK_SLASH_STR = "\\";

	public static final String QUESTION_SIGN_STR = "?";

	public static final String COMMA_STR = ",";

	public static final String DOT_STR = ".";

	public static final String AT_SIGN_STR = "@";

	public static final String TILDE_STR = "~";

	public static final String SINGLE_QUOTE_STR = "'";

	public static final String DOUBLE_QUOTE_STR = "\"";

	public static final String NULL_STR = "[NULL]";

	public static final String LEFT_BRACKET1_STR = "(";

	public static final String RIGHT_BRACKET1_STR = ")";

	public static final String LEFT_BRACKET2_STR = "[";

	public static final String RIGHT_BRACKET2_STR = "]";

	public static final String LEFT_BRACKET3_STR = "{";

	public static final String RIGHT_BRACKET3_STR = "}";

	public static final String NEW_LINE_STR = "\r\n";

	public static final String TWO_NEW_LINE_STR = "\r\n\r\n";

	public static final String FOR_TAG_STR = "<FOR>";

	public static final String FOR_TAG_END_STR = "</FOR>";

	public static final String CNT_STR = "CNT";

	public static final String EVEN_ODD_STR = "EVEN_ODD";

	public static final String LIST_CNT_TAG = "#ListCnt#";

	public final static String[] DEFAULT_NEW_LINE_AFTER_ARY = { COMMA_STR, LEFT_BRACKET3_STR };

	public final static String[] DEFAULT_NEW_LINE_BEFORE_ARY = { RIGHT_BRACKET3_STR };

	private final static String LMS_TEMPLATE_CLS_ROOT = "com/iVoting/lms/template/";

	private final static String FILE_NOT_FOUND_STR = "FILE NOT FOUND!";

	public final static String VALID_PHONE_STR = "電話號碼格式錯誤:";

	private static String LMSTemplateClassRoot;

	private static String env;

	public static final String CELL_PHONE_FORMAT = "[0]{1}[9]{1}[0-9]{8}";

	/**
	 * @return
	 */
	public static boolean isTestEnv() {
		return "test".equalsIgnoreCase( env );
	}

	/**
	 * @return
	 */
	public static boolean isLocalHost() {
		return "localhost".equalsIgnoreCase( env );
	}

	/**
	 * @return
	 */
	public static boolean isProdEnv() {
		return "prod".equalsIgnoreCase( env );
	}

	@Value( "${env}" )
	private void setENV( String envPrifix ) {

		env = envPrifix;
		log.info( "formatutils env:" + env );

	}

	public static String getENV() {
		return env;
	}

	public static ArrayList<String> arraysAsStrList( ArrayList<String>... lists ) {
		ArrayList<String> newList = new ArrayList<String>();
		for ( ArrayList<String> list : lists ) {
			for ( String str : list ) {
				newList.add( str );
			}
		}
		return newList;
	}

	public static ArrayList<String> arraysAsStrList( String... strs ) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			for ( String str : strs ) {
				list.add( str );
			}
		} catch ( Exception e ) {
			log.info( e );
		}
		return list;
	}

	public ArrayList<String> arraysAsStrList( ArrayList<String> list, String... strs ) {
		ArrayList<String> newList = new ArrayList<String>();
		try {
			for ( String str : list ) {
				newList.add( str );
			}
			for ( String str : strs ) {
				newList.add( str );
			}
		} catch ( Exception e ) {
			log.info( e );
		}
		return newList;
	}

	public Map<String, String> arraysAsStrMap( String... strs ) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		boolean hasPrintStr = strs.length % 2 == 1;
		return arraysAsStrMap( hasPrintStr ? strs[ 0 ] : "", map, hasPrintStr ? ( String[] ) ArrayUtils.remove( strs, 0 ) : strs );
	}

	public Map<String, String> arraysAsStrMap( Map<String, String> map, String... strs ) throws Exception {
		return arraysAsStrMap( "", map, strs );
	}

	public Map<String, String> arraysAsStrMap( String printStr, Map<String, String> map, String... strs ) throws Exception {
		if ( strs.length % 2 == 1 ) {
			throw new IllegalArgumentException( "參數比數有誤" );
		}
		for ( int i = 0; i < strs.length; i += 2 ) {
			map.put( strs[ i ].toString(), strs[ i + 1 ] );
		}
		if ( StringUtils.isNotEmpty( printStr ) ) {
			log.info( printStr + map.toString() );
		}
		return map;
	}

	public Map<String, Object> arraysAsObjMap( Object... objs ) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean hasPrintStr = objs.length % 2 == 1;
		return arraysAsObjMap( hasPrintStr ? ( String ) objs[ 0 ] : "", map, hasPrintStr ? ArrayUtils.remove( objs, 0 ) : objs );
	}

	public Map<String, Object> arraysAsObjMap( Map<String, Object> map, Object... objs ) throws Exception {
		return arraysAsObjMap( "", map, objs );
	}

	public Map<String, Object> arraysAsObjMap( String printStr, Map<String, Object> map, Object... objs ) throws Exception {
		if ( objs.length % 2 == 1 ) {
			throw new IllegalArgumentException( "參數比數有誤" );
		}
		for ( int i = 0; i < objs.length; i += 2 ) {
			map.put( objs[ i ].toString(), objs[ i + 1 ] );
		}
		if ( StringUtils.isNotEmpty( printStr ) ) {
			log.info( printStr + map.toString() );
		}
		return map;
	}

}
