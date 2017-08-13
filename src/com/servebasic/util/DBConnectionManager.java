package com.servebasic.util;

import java.sql.Connection;

import javax.servlet.ServletRequest;

public class DBConnectionManager {

	   public static final String ATT_NAME = "DB_CONNECTION";
	 
	   // Store Connection to attribute of request
	   // Information stored only exist during requests
	   // until the data is returned to the user browser.
	   public static void storeConnection(ServletRequest request, Connection conn) {
	       request.setAttribute(ATT_NAME, conn);
	   }
	 
	   // Get the Connection object has been stored in one attribute of the request.
	   public static Connection getStoredConnection(ServletRequest request) {
	       Connection conn = (Connection) request.getAttribute(ATT_NAME);
	       return conn;
	   }
	   
	   public static void closeQuietly(Connection conn) {
		    try {
		        conn.close();
		    } catch (Exception e) {
		    }
		}

		public static void rollbackQuietly(Connection conn) {
		    try {
		        conn.rollback();
		    } catch (Exception e) {
		    }
		}
}
