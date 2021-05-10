package com.iVoting.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iVoting.utils.AesUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class CallApiUtils {

	private static final Logger log = Logger.getLogger( CallApiUtils.class );

	public String callApiGetMethod( String url ) throws Exception {
		URL apiUrl;
		StringBuilder output = new StringBuilder();
		BufferedReader br = null;
		try {
			// URL連線建立
			apiUrl = new URL( url );
			HttpURLConnection conn = ( HttpURLConnection ) apiUrl.openConnection();
			// 去資料表內撈取連線所需帳密，並且經由AES解密後，產出變數加入API連線
			log.info( "getApiKey" );
			String userName = AesUtils.decryptData( "" );
			String password = AesUtils.decryptData( "" );
			String userpass = userName + ":" + password;
			log.info( "after:" + userpass );
			String basicAuth = "Basic " + DatatypeConverter.printBase64Binary( userpass.getBytes() );

			conn.setRequestMethod( "GET" );
			conn.setRequestProperty( "accept", "application/json" );
			conn.setRequestProperty( "Authorization", basicAuth );
			// CheckStatus: 打過去沒有成功 (Status =200)
			if ( conn.getResponseCode() != 200 ) {
				throw new Exception( "連線失敗，Http Status Code:" + conn.getResponseCode() );
			}
			// 讀出打回來的資料
			br = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
			String dataReturnFromNextWmsApi = null;
			while ( ( dataReturnFromNextWmsApi = br.readLine() ) != null ) {
				output.append( dataReturnFromNextWmsApi );
			}
		} catch ( MalformedURLException e ) {
			throw new Exception( "CALL API 網址組成錯握:" + e.getMessage() );
		} catch ( IOException e ) {
			throw new Exception( "CALL API URL連線錯誤:" + e.getMessage() );
		} catch ( Exception e ) {
			throw new Exception( "CALL API失敗:" + e.getMessage() );
		} finally {
			br.close();
		}
		return output.toString();
	}

	public String callApiPostMethod( String url, String jsonPara ) throws Exception {
		URL apiUrl;
		StringBuilder output = new StringBuilder();
		BufferedReader br = null;
		try {
			// URL連線建立
			apiUrl = new URL( url );
			HttpURLConnection conn = ( HttpURLConnection ) apiUrl.openConnection();
			// String userName = AesUtils.decryptData("10");
			// String password = AesUtils.decryptData("10");
			// String userpass = userName + ":" + password;
			// String basicAuth = "Basic " +
			// DatatypeConverter.printBase64Binary(userpass.getBytes());
			String encoding = Base64.getEncoder().encodeToString( "user:password".getBytes() );

			conn.setDoOutput( true );
			conn.setDoInput( true );
			conn.setRequestMethod( "POST" );
			conn.setInstanceFollowRedirects( false );
			// conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );// text/html;charset=ISO-8859-1
			conn.setRequestProperty( "Accept", "application/json" );
			conn.setRequestProperty( "Authorization", encoding );

			OutputStreamWriter wr = new OutputStreamWriter( conn.getOutputStream() );
			wr.write( jsonPara.toString() );
			wr.flush();

			if ( conn.getResponseCode() != HttpURLConnection.HTTP_OK ) {
				throw new Exception( "連線失敗，Http Status Code:" + conn.getResponseCode() );
			}
			// 讀出打回來的資料
			br = new BufferedReader( new InputStreamReader( conn.getInputStream(), "utf-8" ) );
			String dataReturnFromNextWmsApi = null;
			// log.info( "看有無資料" + br.readLine() );

			while ( ( dataReturnFromNextWmsApi = br.readLine() ) != null ) {
				output.append( dataReturnFromNextWmsApi );
			}
		} catch ( MalformedURLException e ) {
			throw new Exception( "CALL API 網址組成錯握:" + e.getMessage() );
		} catch ( IOException e ) {
			throw new Exception( "CALL API URL連線錯誤:" + e.getMessage() );
		} catch ( Exception e ) {
			throw new Exception( "CALL API失敗:" + e.getMessage() );
		} finally {
			br.close();
		}
		return output.toString();
	}

//	public String callApiPostMethod(String url, String jsonPara) throws Exception {
//		URL apiUrl;
//		StringBuilder output = new StringBuilder();
//		StringBuilder output2 = new StringBuilder();
//		BufferedReader br = null;
//		try {
//			// URL連線建立
//			apiUrl = new URL(url);
//			HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
//			// String userName = AesUtils.decryptData("10");
//			// String password = AesUtils.decryptData("10");
//			// String userpass = userName + ":" + password;
//			// String basicAuth = "Basic " +
//			// DatatypeConverter.printBase64Binary(userpass.getBytes());
//			String encoding = Base64.getEncoder().encodeToString("user:password".getBytes());
//			// String basicAuth = "Basic " + userpass;
//			// log.info("after:" + userpass);
//			// 參數設定
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			conn.setRequestMethod("POST");
//			conn.setInstanceFollowRedirects(false);
//			// conn.setRequestProperty("Content-Type", "application/json");
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// text/html;charset=ISO-8859-1
//			conn.setRequestProperty("Accept", "application/json");
//			conn.setRequestProperty("Authorization", encoding);
//
//			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//			wr.write(jsonPara.toString());
//			wr.flush();
//
//			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
//				throw new Exception("連線失敗，Http Status Code:" + conn.getResponseCode());
//			}
//			// 讀出打回來的資料
//			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
//			String dataReturnFromNextWmsApi = null;
//			// log.info( "看有無資料" + br.readLine() );
//
//			while ((dataReturnFromNextWmsApi = br.readLine()) != null) {
//				output.append(dataReturnFromNextWmsApi);
//			}

//			String newUrl = conn.getHeaderField("Location");
//			String cookies = conn.getHeaderField("Set-Cookie");
//
//			log.info("newUrl:::" + newUrl + ",cookies:::" + cookies);
//
//			String strSpringAuthURL = "http:/localhost:8080/j_spring_security_check";
//			String urlParameters = jsonPara;
//
//			System.out.println("Redirected to URL : " + strSpringAuthURL);
//			System.out.println("Response code from Spring Auth URL is " + conn.getResponseCode());
//			// conn = (HttpURLConnection) new URL(strSpringAuthURL +
//			// ";jsessionid=" + J_SESSION_ID).openConnection();
//			conn = (HttpURLConnection) new URL(strSpringAuthURL).openConnection();
//			// Pass on the cookie
//			conn.setRequestProperty("Cookie", cookies);
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//
//			conn.setInstanceFollowRedirects(false);
//			conn.setRequestMethod("POST");
//			DataOutputStream wr1 = new DataOutputStream(conn.getOutputStream());
//			// Pass on the credentials
//			wr1.writeBytes(urlParameters);
//			wr1.flush();
//			wr1.close();
//
//			// System.out.println("Redirect to URL : " + strSpringAuthURL
//			// + ";jsessionid=" + J_SESSION_ID);
//			System.out.println("Redirected to URL : " + strSpringAuthURL);
//			System.out.println("Response code from Spring Auth URL is " + conn.getResponseCode());
//
//			// Get the Web Service URL from the http header
//			String strOrigWSUrl = conn.getHeaderField("Location");
//
//			String cookies1 = conn.getHeaderField("Set-Cookie");
//
//			// conn = (HttpURLConnection) new URL(strOrigWSUrl +
//			// ";jsessionid=" + J_SESSION_ID).openConnection();
//
//			// Connection is made to the original Web Service URL that we want
//			conn = (HttpURLConnection) new URL(strOrigWSUrl).openConnection();
//
//			// ************* IMP *************
//			// The cookies that we received after authentication is passed here
//			// again.
//			conn.setRequestProperty("Cookie", cookies1);
//
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//
//			conn.setInstanceFollowRedirects(false);
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept", "application/json");
//			conn.setUseCaches(false);
//
//			System.out.println("Redirect to URL strOrigWSUrl : " + strOrigWSUrl);
//			// We get a 200 Response code now
//			System.out.println(" Response code 555 is " + conn.getResponseCode());
//
//			// } //end-if for the condition where we checked if the Response
//
//			BufferedReader br2 = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//			String line;
//
//			while ((dataReturnFromNextWmsApi = br2.readLine()) != null) {
//				output2.append(dataReturnFromNextWmsApi);
//			}
//			log.info("output2:::" + output2);
	// while ((line = br2.readLine()) != null) {
	// JSONArray jsa = new JSONArray(line);
	//
	// for (int i = 0; i < jsa.length(); i++) {
	// JSONObject jo = (JSONObject) jsa.get(i);
	//
	// AlarmBean alarmBean = new AlarmBean(jo
	// .getInt("alarmId"), jo.getString("timestamp"),
	// jo.getString("duration"), jo
	// .getString("meterName"), jo
	// .getString("alarmType"), jo
	// .getString("status"), jo
	// .getInt("dcId"), jo
	// .getInt("count"), jo
	// .getString("deviceId"), jo
	// .getInt("buildingId"), jo
	// .getBoolean("disabled"), jo
	// .getString("location"), jo
	// .getString("dc"), jo
	// .getString("buildingName"), jo
	// .getInt("acknowledgedBy"), jo
	// .getString("ackUserName"), jo
	// .getString("acknowledgedOn"), jo
	// .getString("thresholdValue"), jo
	// .getString("actualValue"));
	// alarmBeanList.add(alarmBean);
	// }
	// }

//		} catch (MalformedURLException e) {
//			throw new Exception("CALL API 網址組成錯握:" + e.getMessage());
//		} catch (IOException e) {
//			throw new Exception("CALL API URL連線錯誤:" + e.getMessage());
//		} catch (Exception e) {
//			throw new Exception("CALL API失敗:" + e.getMessage());
//		} finally {
//			br.close();
//		}
//		return output.toString();
//	}

	public static void main( String[] args ) throws NoSuchAlgorithmException {
		testCallCancelOrder();
		// testCallQueryOrderStatus();
		// testCallQueryStockInStatus();

	}

	private static void testCallCancelOrder() {

		try {
			// JSONObject jsonPara = new JSONObject();
			// JSONObject cancel_request = new JSONObject();
			// cancel_request.put( "order_number", "SO2018051601" );
			// cancel_request.put( "comment", "customer request" );
			// jsonPara.put( "cancel_request", cancel_request.toString() );
			// log.info( "json:" + jsonPara.toString() );
			// String userName = AesUtils.decryptData(
			// "w3349xz4r/Dpz4BWShgRqQ==" );
			// String password = AesUtils.decryptData(
			// "3QrJ/eAqeAOknQE7yIezug==" );
			Map<String, Object> map = new HashMap<>();

			map.put( "account", "10" );
			map.put( "password", "10" );
			Gson gson = new Gson();
			String jsonPara = gson.toJson( map );
			log.info( "jsonPara: " + jsonPara );

			String url = "http://211.76.135.3:6080/upiitf/cus_ob_order_cancel_sphx/post";
			// String output = test.callNextWmsApiPostMethod( url, jsonPara );
			// log.info( "output:" + output );
			//
			// NextWmsCallApiService nextWmsCallApiService = new
			// NextWmsCallApiService();
			// NextWmsDeleteOrderResponseDTO finalData =
			// nextWmsCallApiService.transferApiDataToNextWmsDeleteOrderResponseDTO(
			// output );
			// log.info( finalData );

		} catch ( Exception e ) {
			// TODO Auto-generated catch block
			log.info( "ApiTestError => " + e );
		}

	}

}
