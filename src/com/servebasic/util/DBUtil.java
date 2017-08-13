package com.servebasic.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {

private static DataSource dataSource;

static {
	  Context envContext = null;

      try {
          envContext = new InitialContext();
          Context initContext  = (Context)envContext.lookup("java:/comp/env");
           dataSource = (DataSource)initContext.lookup("jdbc/servebasic");
          //DataSource ds = (DataSource)envContext.lookup("java:/comp/env/jdbc/testDB");
    } catch (NamingException e) { 
        e.printStackTrace();
    }
}

public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
}

}