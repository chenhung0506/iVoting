package com.iVoting.utils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.Log4jConfigurer;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class EnvInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
	private static final Logger log = Logger.getLogger( EnvInitializer.class );

	public void initialize( ConfigurableWebApplicationContext context ) {
		try {
			String hostname = InetAddress.getLocalHost().getHostName(), env = "local";

			if ( "was136.treemall.com.tw".equals( hostname ) ) {
				env = "production";

			} else if ( "was139.treemall.com.tw".equals( hostname ) ) {
				env = "staging";

			}

			Map<String, Object> source = new HashMap<String, Object>();

			source.put( "env.prefix", env );

			context.getEnvironment().getPropertySources().addFirst( new MapPropertySource( "env.source", source ) );

			Log4jConfigurer.initLogging( "classpath:" + env + "-log4j.properties" );

			log.info( "主機名稱: " + hostname + ", 環境: " + env );

		} catch ( Exception e ) {
			log.error( "環境設定初始化失敗!", e );

		}
	}
}