package com.iVoting.utils;

import java.io.IOException;
import java.security.SignatureException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesUtils {
	private static final Logger log = Logger.getLogger( AesUtils.class );

	// 加解密設定
	private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

	private static final String AES_STR = "AES";

	private static byte[] key = null;

	private static byte[] iv = null;

	// @Value( "${aesKey}" )
	// private static String aesKey;
	//
	// @Value( "${aesVi}" )
	// private static String aesIv;

	private static String aesKey = "7777777777777777777777";
								  
	private static String aesIv = "0122333444455555666666";

	static {
		System.out.println( "Initialization:" + aesKey + ",and:" + aesIv );
		key = DecodeBase64( aesKey );
		iv = DecodeBase64( aesIv );
	}

	/**
	 * @param data
	 * @return 將傳入參數AES加密
	 * @throws SignatureException 將參數的明文 (Plain Text) 進行AES加密成密文 (Cipher Text)。
	 */
	public static String encryptData( String data ) throws SignatureException {
		return encryptByAES( data, key, iv );
	}
	/**
	 * @param cipherText
	 * @return 將傳入參數AES解密
	 * @throws SignatureException 將參數的明文 (Plain Text) 進行AES加密成密文 (Cipher Text)。
	 */
	public static String decryptData( String cipherText ) throws SignatureException {
		return decryptByAES( cipherText, key, iv );
	}

	/**
	 * AES 加密
	 * 
	 * @param plainText
	 * @param key The signing key.
	 * @param iv The signing iv.
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException when signature generation fails
	 */
	private static String encryptByAES( String plainText, byte[] key, byte[] iv ) throws java.security.SignatureException {
		return processByAES( plainText, null, key, iv, true );
	}

	/**
	 * AES 解密
	 * 
	 * @param cipherText
	 * @param key The signing key.
	 * @param iv The signing iv.
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException when signature generation fails
	 */
	private static String decryptByAES( String cipherText, byte[] key, byte[] iv ) throws java.security.SignatureException {
		return processByAES( null, cipherText, key, iv, false );
	}

	/**
	 * 處理 AES 加解/解密流程
	 * 
	 * @param plainText
	 * @param cipherText
	 * @param key The signing key.
	 * @param iv The signing iv.
	 * @param toEncrypt true:encrypt/false:decrypt
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException when signature generation fails
	 */
	private static String processByAES( String plainText, String cipherText, byte[] key, byte[] iv, boolean toEncrypt ) throws java.security.SignatureException {
		String result;
		try {
			// log.info( key.length );
			// log.info( iv.length );
			Cipher cipher = Cipher.getInstance( AES_ALGORITHM );
			SecretKeySpec signingKey = new SecretKeySpec( key, AES_STR );
			IvParameterSpec params = new IvParameterSpec( iv );
			byte[] byteAry = null;
			if ( toEncrypt ) {
				cipher.init( Cipher.ENCRYPT_MODE, signingKey, params );
				// cipher.init( Cipher.ENCRYPT_MODE, signingKey );
				byteAry = cipher.doFinal( plainText.getBytes() );
				// byteAry = cipher.doFinal(plainText.getBytes("ISO-8859-1"));
			} else {
				byteAry = DecodeBase64( cipherText );
				cipher.init( Cipher.DECRYPT_MODE, signingKey, params );
				// cipher.init( Cipher.DECRYPT_MODE, signingKey );
				byteAry = cipher.doFinal( byteAry );
			}

			// encode
			if ( toEncrypt ) {
				result = EncodeBase64( byteAry );
			} else {
				result = new String( byteAry, FormatUtils.UTF8_STR );
				// result = new String(byteAry,"ISO-8859-1");
			}
		} catch ( Exception e ) {
			log.error( e.getMessage(), e );
			throw new SignatureException( "Failed to generate AES : " + e.getMessage() );
		}
		return result;
	}

	/**
	 * Base64格式 解碼
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static byte[] DecodeBase64( String str ) {
		return Base64.decodeBase64( str );
	}

	/**
	 * Base64格式編碼
	 * 
	 * @param rawData
	 * @return
	 */
	public static String EncodeBase64( byte[] rawData ) {
		return Base64.encodeBase64String( rawData );
	}

	// /**
	// * 設定KEY & IV (AES加解密會用到)
	// *
	// * @param group
	// * @param action
	// * @throws IOException
	// */
	// public void init() throws IOException {
	// // key
	// this.key = DecodeBase64( this.aesKey );
	// byte[] test = aesKey.getBytes();
	// System.out.println( "Size:" + test.length );
	// // iv
	// this.iv = DecodeBase64( this.aesIv );
	// }
}
