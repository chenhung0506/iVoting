package com.iVoting.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iVoting.dao.GetConnectionTest;
import com.iVoting.dao.IUserDAO;
import com.iVoting.dto.UserDTO;
import com.iVoting.constants.Protocol;
import com.iVoting.utils.AesUtils;
import com.iVoting.utils.CallApiUtils;
import com.iVoting.utils.CaptchaUtils;
import com.iVoting.utils.ConnectionUtils;
import com.iVoting.utils.FormatUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@Controller
public class LoginController {

	private static final Logger log = Logger.getLogger( LoginController.class );

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private CaptchaUtils captchaUtils;

	@Autowired
	private CallApiUtils callApiUtils;

	@Autowired
	private FormatUtils formatUtils;

	@Value( "${project.name}" )
	private String projectName;

	@Value( "${project.url}" )
	private String projectUrl;

	@RequestMapping( value = "/", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView login( HttpServletRequest request, HttpServletResponse response ) {
		ModelAndView mav = new ModelAndView( "login.view" );
		mav.addObject( "projectUrl", projectUrl );
		mav.addObject( "projectName", projectName );
		log.info( "show login.view" );
		return mav;
	}

	@RequestMapping( value = "/captcha", method = RequestMethod.GET )
	public void captcha( HttpServletRequest request, HttpServletResponse response ) {
		try {
			captchaUtils.getCaptcha( request, response );
		} catch ( Exception e ) {
			log.info( "取驗證碼圖案錯誤:" + e.getMessage() );
		}
	}

	public void callApiExample( HttpServletRequest request, HttpServletResponse response ) {
		try {
			String username = request.getParameter( "username" );
			String password = request.getParameter( "password" );
			String urlParameters = "username=" + URLEncoder.encode( username, "UTF-8" ) + "&password=" + URLEncoder.encode( password, "UTF-8" );
			log.info( callApiUtils.callApiPostMethod( "http://localhost:8080/spring-ibatis/j_spring_security_check", urlParameters ) );
		} catch ( Exception e ) {
			log.info( e.getMessage() );
		}
	}

	@RequestMapping( value = "/validateCaptcha", method = RequestMethod.POST )
	public String validateCaptcha( HttpServletRequest request, HttpServletResponse response ) {
		try {
			Assert.isTrue( "驗證碼正確".equals( captchaUtils.validateCaptcha( request ) ), "驗證碼錯誤 " );

			String username = request.getParameter( "username" );
			String password = AesUtils.encryptData( request.getParameter( "password" ) ).replace( "\r\n", "" );
			List<Map<String, Object>> userListMap = userDAO.getUser( formatUtils.arraysAsObjMap( "USER帶入參數: ", "ACCOUNT", username, "PASSWORD", password ) );

			Assert.isTrue( userListMap.size() == 1, "此會員不存在" );

			ArrayList<String> roleList = new ArrayList<String>();
			for ( Map<String, Object> map : userListMap ) {
				roleList.add( map.get( "ROLE_ID" ).toString() );
			}
			request.getSession().setAttribute( "user", userListMap.get( 0 ).get( "NAME" ) );
			request.getSession().setAttribute( "roleList", roleList );

			return "redirect:" + "/show";
		} catch ( Exception e ) {
			log.info( "登入驗證錯誤:" + e.getMessage() );
			return "redirect:" + "/";
		}
	}

	// @showJson
	@RequestMapping( value = "/ajaxShow", method = RequestMethod.GET )
	public @ResponseBody List<Map<String, Object>> ajaxShow() {
		log.info( "開始執行ajaxShow" );
		try {
			return userDAO.getUser( null );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}
		return null;
	}

	// @show
	@RequestMapping( value = "/show", method = { RequestMethod.GET, RequestMethod.POST } )
	public ModelAndView show( HttpServletRequest request, HttpServletResponse response ) {
		ModelAndView mav = new ModelAndView( "show.view" );
		log.info( "開始執行showTEST" );
		try {
			List<Map<String, Object>> employees = userDAO.getUser( null );
			mav.addObject( "employees", employees );
		} catch ( Exception e ) {
			log.info( e.getMessage() );
		}
		return mav;
	}

	// @Delete
	@RequestMapping( value = "/deleteEmployee", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8" )
	public @ResponseBody String DeleteEmployee( String id, HttpServletRequest request ) {
		log.info( "開始執行Delete" );
		try {
			Assert.isTrue( 1 == userDAO.updateUser( formatUtils.arraysAsObjMap( "USER帶入參數: ", "USER_ID", request.getParameter( "id" ) ) ), "USER_ID: " + id + ", 更新資料筆數不為一" );
			return Protocol.MESSAGE_DELETE_SUCCESS;
		} catch ( Exception e ) {
			log.error( e.getMessage() );
			return Protocol.MESSAGE_DELETE_FAIL;
		}
	}

	// @Add
//	@RequestMapping( value = "/addEmployee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/plain;charset=UTF-8" )
//	public @ResponseBody String AddEmployee( UserDTO user ) {
//		log.info( "開始執行Add" );
//		try {
////			Assert.isTrue( StringUtils.isBlank( employee.getUserId() ), "帳號不可為空" );
////			Assert.isTrue( StringUtils.isBlank( employee.getPassword() ), "密碼不可為空" );
////			Assert.isTrue( StringUtils.isBlank( employee.getName() ), "名子不可為空" );
//			String password = AesUtils.encryptData( user.getPassword().replace( "\r\n", "" ) );
//			Map<String, Object> map = new HashMap<String, Object>();
//			map = formatUtils.arraysAsObjMap( "USER帶入參數: ", "ACCOUNT", user.getAccount(), "PASSWORD", password, "PASSWORD_HINT", user.getPasswordHint(), "NAME", user.getName(), "ROLE_ID", user.getRoleId() );
//			Assert.isTrue( 1 == userDAO.insertUser( user ), "新增USER失敗，更新資料筆數不為一" );
//			Assert.isTrue( 1 == userDAO.insertUserRole( map ), "新增USER_ROLE失敗，更新資料筆數不為一" );
//		} catch ( Exception e ) {
//			log.error( e.getMessage() );
//			return Protocol.MESSAGE_ADD_FAIL;
//		}
//		log.info( user.getAccount() );
//		return Protocol.MESSAGE_ADD_SUCCESS;
//	}

	@RequestMapping( value = "/{action}", method = RequestMethod.POST )
	@ResponseBody
	public int action( HttpServletRequest request, @PathVariable String action ) throws Exception {
		try {

			Assert.isTrue( "insert".equals( action ) || "update".equals( action ) || "delete".equals( action ), "ACTION ERROR" );
			Gson gson = new Gson();
//			Map<String, String> params = new Gson().fromJson( request.getParameter( "params" ), new TypeToken<Map<String, String>>() {}.getType() );
			log.info( ":::" + request.getParameter( "account" ) );
			log.info( ":::" + request.getParameter( "data" ) );

			UserDTO user = gson.fromJson( request.getParameter( "data" ), new TypeToken<UserDTO>() {
			}.getType() );

			log.info( "ACTION: " + action );
//			log.info( ",Parm: " + user.toString() );
			switch ( action ) {
				case "insert":
					Assert.hasLength( user.getAccount(), "帳號不可為空" );
					Assert.hasLength( user.getPassword(), "密碼不可為空" );
					Assert.hasLength( user.getName(), "名稱不可為空" );
//					Assert.isTrue( user.getRoleList().isEmpty(), "權限不可為空" );
					user.setPassword( AesUtils.encryptData( user.getPassword() ).replace( "\r\n", "" ) );

					Assert.isTrue( 1 == userDAO.insertUser( user ), "新增USER失敗，更新資料筆數不為一" );
					Assert.isTrue( 1 == userDAO.insertUserRole( user ), "新增USER_ROLE失敗，更新資料筆數不為一" );
					log.info( "user:" + user );

//					Assert.isTrue( 1 == userDAO.insertUserRole( map ), "新增USER_ROLE失敗，更新資料筆數不為一" );
					break;
				case "update":

					break;
				case "delete":

					break;
				default:
					break;
			}
			return 0;
//			 return isIns ? employeeDAO.addEmployee( map ) : isUpd ?
//			 employeeDAO.updateImeiMember( map ) :
//			 employeeDAO.deleteImeiMember( map );

		} catch ( Exception e ) {
			log.error( "", e );

			return 0;

		}
	}

	// @showJson
//	@RequestMapping( value = "/{action}", method = RequestMethod.POST )
//	@ResponseBody
//	public int action1( HttpServletRequest request, @PathVariable String action ) throws Exception {
//		try {
//			boolean isIns = "insert".equals( action ), isUpd = "update".equals( action );
//			Assert.isTrue( isIns || isUpd || "delete".equals( action ), "ACTION ERROR" );
//			Map<String, String> map = new HashMap<String, String>();
//			String imei = StringUtils.trimToEmpty( request.getParameter( "imei" ) ), enable, susrId, name;
//			Assert.hasLength( imei, "IMEI銝�蝛�" );
//
//			map.put( "IMEI", imei );
//
//			if ( isIns || isUpd ) {
//				Assert.isTrue( "0".equals( enable = request.getParameter( "enable" ) ) || "1".equals( enable ), "ENABLE�隤�" );
//				map.put( "ENABLE", enable );
//
//				if ( isIns ) {
//					Assert.hasLength( susrId = StringUtils.trimToEmpty( request.getParameter( "susrId" ) ), "撣唾���蝛�" );
//					map.put( "SUSR_ID", susrId );
//
//				} else {
//					Assert.hasLength( name = StringUtils.trimToEmpty( request.getParameter( "name" ) ), "雿輻��迂銝�蝛�" );
//					map.put( "NAME", name );
//
//				}
//			}
//
//			log.info( "ACTION: " + action + ",Parm: " + map );
//			return 0;
////			 return isIns ? employeeDAO.addEmployee( map ) : isUpd ?
////			 employeeDAO.updateImeiMember( map ) :
////			 employeeDAO.deleteImeiMember( map );
//
//		} catch ( Exception e ) {
//			log.error( "", e );
//
//			return 0;
//
//		}
//	}

	// @Update
	@RequestMapping( value = "/updateEmployee", method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/plain;charset=UTF-8" )
	public @ResponseBody String UpdateEmployee( UserDTO employee ) {
		log.info( ":::" + employee );
		log.info( "開始執行Update" );
		if ( StringUtils.isBlank( employee.getUserId() ) ) {
			return Protocol.ERROR_ID_PARSING;
		} else if ( StringUtils.isBlank( employee.getName() ) ) {
			return Protocol.ERROR_NAME_PARSING;
		} else if ( StringUtils.isBlank( employee.getPassword() ) ) {
			return Protocol.ERROR_PASSWORD_PARSING;
		} else {
			try {
				return null;
//				return employeeDAO.updateEmployee( employee );
			} catch ( Exception e ) {
				log.error( e.getMessage() );
				return Protocol.MESSAGE_UPDATE_FAIL;
			}
		}
	}

	// @Select
	@RequestMapping( value = "/selectEmployee", method = RequestMethod.GET )
	public ModelAndView SelectEmployee( UserDTO employee ) {
		ModelAndView mav = new ModelAndView( "show.view" );
		log.info( "開始執行Select" );
		try {
//			List<Employee> employees = employeeDAO.selectEmployee( employee );
//			mav.addObject( "employees", employees );
		} catch ( Exception e ) {
			log.error( e.getMessage() );
		}
		return mav;
	}

	@RequestMapping( value = "/requestParam", method = RequestMethod.GET )
	public String get( HttpServletRequest request, HttpServletResponse response ) {
		log.info( request.getParameter( "account" ) );
		return "helloWorld";
	}

}
