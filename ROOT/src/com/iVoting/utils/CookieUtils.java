package com.iVoting.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import com.iVoting.constants.Constants;
import com.iVoting.controller.LoginController;

public class CookieUtils {

	private static final Logger log = Logger.getLogger( CookieUtils.class );

	public void setCookie( HttpServletResponse response, String key, String value, int maxTime ) {
		String pwdKey = Constants.COOKIE_PWD; // 自定义密钥
		String saveTime = System.currentTimeMillis() + "";
		String signToken = md5Encode( pwdKey, saveTime, maxTime + "", value );

		String cookieValue = signToken + "&&" + saveTime + "&&" + maxTime + "&&" + value;
		Cookie cookie = new Cookie( key, cookieValue );
		cookie.setMaxAge( maxTime );
		response.addCookie( cookie );

	}

	public String getCookie( String cookieValue ) {
		String pwdKey = Constants.COOKIE_PWD; // 自定义密钥
		if ( StringUtils.isNotBlank( cookieValue ) ) {
			String cookieStrings[] = cookieValue.split( "&&" );
			if ( null != cookieStrings && 4 == cookieStrings.length ) {
				String signToken = cookieStrings[ 0 ];
				String saveTime = cookieStrings[ 1 ];
				String maxTime = cookieStrings[ 2 ];
				String value = cookieStrings[ 3 ];
				String sign = md5Encode( pwdKey, saveTime, maxTime, value );

				// 保证 cookie 不被人为修改
				if ( sign.equals( signToken ) ) {
					long stime = Long.parseLong( saveTime );
					long maxtime = Long.parseLong( maxTime ) * 1000;
					// 查看是否过时
					if ( ( stime + maxtime ) - System.currentTimeMillis() > 0 ) {
						return value;
					}
				}
			}
		}
		return null;
	}

	public String md5Encode( String pwdKey, String saveTime, String maxTime, String value ) {
		try {
			String inputStr = pwdKey + saveTime + maxTime + value;
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			md.update( inputStr.getBytes() );
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary( digest ).toUpperCase();
			return myHash;
		} catch ( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e );
		} catch ( Exception e ) {
			log.info( e.getMessage() );
		}
		return null;
	}
	
	private static String md5( String input ) {
		try {
			MessageDigest md5 = MessageDigest.getInstance( "MD5" );
			byte[] cipher = md5.digest( input.getBytes( "UTF-8" ) );
			StringBuilder sb = new StringBuilder( 0 );
			for ( int i = 0; i < cipher.length; i++ ) {
				String toHexStr = Integer.toHexString( cipher[ i ] & 0xff );
				sb.append( toHexStr.length() == 1 ? "0" + toHexStr : toHexStr );
			}

			return sb.toString().toUpperCase();
		} catch ( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e );
		} catch ( UnsupportedEncodingException e ) {
			throw new RuntimeException( e );
		}
	}
}
