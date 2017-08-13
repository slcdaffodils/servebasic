package com.servebasic.filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servebasic.util.DBConnectionManager;
import com.servebasic.util.DBUtil;

public class JDBCFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	       //
	       String servletPath = req.getServletPath();
	        
	       //
	       // Only open Connection for special request.
	       // (For example: servlet, jsp, ..)
	       //
	       // Avoid open connection for the common request
	       // (For example image, css, javascript,... )
	       //        
	       if (servletPath.contains("/api")) {
	           Connection conn = null;
	           try {
	            
	               // Create a Connection
	               conn = DBUtil.getConnection();
	          
	               // Set auto commit false
	               conn.setAutoCommit(false);
	 
	        
	               // Store connection in attribute of request.
	               DBConnectionManager.storeConnection(request, conn);
	 
	          
	               // Go to next element (filter or target) in chain
	               chain.doFilter(request, response);
	 
	               res.setContentType("application/json");
	               res.setCharacterEncoding("UTF-8");
	               // Call commit() to commit transaction.
	               conn.commit();
	           } catch (Exception e) {
	        	   DBConnectionManager.rollbackQuietly(conn);
	               throw new ServletException();
	           } finally {
	        	   DBConnectionManager.closeQuietly(conn);
	           }
	       }
	    
	       // For common request.
	       else {
	        
	           // Go to next element (filter or target) in chain.
	           chain.doFilter(request, response);
	       }
	 
	   }
		
	

		 
		  
		 
}

