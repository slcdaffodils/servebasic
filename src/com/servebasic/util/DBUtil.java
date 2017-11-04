package com.servebasic.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import org.apache.commons.dbcp.*;
import org.apache.log4j.Logger;

import com.servebasic.rest.RestServlet;

/*
//mysql://root:root@localhost:3306/servebasic
**/

public class DBUtil {

	private static BasicDataSource connectionPool;
	private final static Logger LOGGER = Logger.getLogger(DBUtil.class);
  static {
	     System.out.println("Configuring the database connections.....");
	     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	     InputStream input = classLoader.getResourceAsStream("system.properties");
	     try {
	     Properties properties = new Properties();
	     properties.load(input);
	     
	     LOGGER.info("Configuring the database connections tomcat start up.....->"+properties.getProperty("DATABASE_URL"));
	     LOGGER.info("Configuring the database connections system environment.....->"+System.getenv("DATABASE_URL"));
	
		
//		 Map<String, String> env = System.getenv();
//	        for (String envName : env.keySet()) {
//	            System.out.format("%s=%s%n",
//	                              envName,
//	                              env.get(envName));
//	        }
		    String passedURL=System.getenv("DATABASE_URL")==null?properties.getProperty("DATABASE_URL"):System.getenv("DATABASE_URL");
			 URI dbUri = new URI(passedURL);
			 LOGGER.info("got the URL");
			  String dbUrl = "jdbc:mysql://" + dbUri.getHost()+":"+dbUri.getPort() + dbUri.getPath();
			  if(dbUri.getPort()==-1){
				  dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
			  }
			  LOGGER.debug("DB URL of the system:->"+dbUrl);
			  connectionPool = new BasicDataSource();

			  if (dbUri.getUserInfo() != null) {
			    connectionPool.setUsername(dbUri.getUserInfo().split(":")[0]);
			    connectionPool.setPassword(dbUri.getUserInfo().split(":")[1]);
			  }
			  connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
			  connectionPool.setUrl(dbUrl);
			  connectionPool.setInitialSize(5);
	} catch (Exception e) {
		LOGGER.error("Please set up the system db url");
		LOGGER.error(e.getMessage(),e);
	}
}

public static Connection getConnection() throws SQLException {
    return connectionPool.getConnection();
}

}