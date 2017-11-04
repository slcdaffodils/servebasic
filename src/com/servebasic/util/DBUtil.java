package com.servebasic.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
	     LOGGER.info("Configuring the database connections.....");
	try {
		
		
			 URI dbUri = new URI(System.getenv("DATABASE_URL"));
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
	} catch (URISyntaxException e) {
		LOGGER.error("Please set up the system db url");
		LOGGER.error(e.getMessage(),e);
	}
}

public static Connection getConnection() throws SQLException {
    return connectionPool.getConnection();
}

}