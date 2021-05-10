package com.iVoting.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.Assert;

import com.iVoting.constants.Constants;
import com.iVoting.controller.LoginController;
import com.iVoting.utils.CallApiUtils;

//@Controller
@WebFilter( "/UrlFilter" )
public class UrlFilter implements Filter {
	private static final Logger log = Logger.getLogger( UrlFilter.class );

	@Value( "${project.url}" )
	private String projectUrl;

	@Autowired
	private CallApiUtils callApiUtils;

	@Autowired
	private LoginController loginController;

	public void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws IOException, ServletException {
		try {
			log.info( "有近來doFilterInternal" );
			String uri = request.getRequestURI();
			String username = request.getParameter( "username" );
			String password = request.getParameter( "password" );
			log.info( "uri:" + uri + ",username:" + username + ",password:" + password );
			request.getSession().getServletContext().getRequestDispatcher( "/login.jsp" ).forward( request, response );
		} catch ( Exception e ) {
			log.info( e.getMessage() );
		}
		// return;
	}

	@SuppressWarnings( "unchecked" )
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
		HttpServletRequest req = ( HttpServletRequest ) request;
//		HttpServletResponse res = ( HttpServletResponse ) response;
		boolean checkAuthority;
		String url = req.getRequestURI();
		String user = ( String ) req.getSession().getAttribute( "user" );
		ArrayList<String> roleList = ( ArrayList<String> ) req.getSession().getAttribute( "roleList" );

		if ( projectUrl != null ) {
			url = url.replace( projectUrl, "" );
		}

		checkAuthority = checkAuthority( user, url, roleList );
		log.info( "user:" + user + ", role:" + roleList + ", url:" + url + ", Enter Sucess" );

//		if ( checkAuthority ) {
//			chain.doFilter( request, response );
//		} else {
//			HttpServletResponse httpResponse = ( HttpServletResponse ) response;
//			httpResponse.sendRedirect( "/" + projectUrl );
//			// response.sendRedirect("Index.jsp");
//		}
		chain.doFilter( request, response );
	}

	public boolean checkAuthority( String user, String url, ArrayList<String> roleList ) {

		if ( !Constants.ROLE_MAP.get( "ROLE_0" ).contains( url ) ) {
			return true;
		}
		for ( Map.Entry<String, ArrayList<String>> entry : Constants.ROLE_MAP.entrySet() ) {
			if ( roleList.contains( entry.getKey() ) && entry.getValue().contains( url ) && user != null ) {
				return true;
			}
		}
		return false;
	}

	public UrlFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init( FilterConfig fConfig ) throws ServletException {
		// TODO Auto-generated method stub
	}

}
