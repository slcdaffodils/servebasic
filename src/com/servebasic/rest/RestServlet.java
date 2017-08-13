package com.servebasic.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servebasic.util.DBConnectionManager;
import com.servebasic.util.DBUtil;

public class RestServlet extends HttpServlet {

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
	
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		PrintWriter out = response.getWriter();
//		if("getallusers".equalsIgnoreCase(request.getParameter("query"))){
//		
//	        out.println("{\"userList\":[");
//	        out.println("{");
//	        out.println("\"firstname\": \"Devesh\",");
//	        out.println("\"lastname\": \"Sharma\",");
//	        out.println("\"type\": \"2\",");
//	        out.println("\"active\": \"true\"");
//	        out.println("},");
//	        
//	        out.println("{");
//	        out.println("\"firstname\": \"Devesh\",");
//	        out.println("\"lastname\": \"Sharma\",");
//	        out.println("\"type\": \"2\",");
//	        out.println("\"active\": \"true\"");
//	        out.println("}");   
//	        
//	        out.println("]}");
//		}else{
//	        out.println("{");
//	        out.println("\"firstname\": \"Devesh\",");
//	        out.println("\"lastname\": \"Sharma\",");
//	        out.println("\"type\": \"2\",");
//	        out.println("\"active\": \"true\"");
//	        out.println("}");  
//		}
//	   out.close();
	   
	   if("getallusers".equalsIgnoreCase(request.getParameter("query"))){
		try{
			  Connection con = DBConnectionManager.getStoredConnection(request);
	          
	          Statement stmt = con.createStatement();
	          String query = "select * from users";
	          ResultSet rs = stmt.executeQuery(query);
	          out.println("{\"userList\":[");
	          boolean first=true;
	          while(rs.next())
	          {
	        	  if(!first){
	        		  out.println(",");
	        	  }
	        	  	out.println("{");
		        	    out.println("\"id\": \""+rs.getString("id")+"\",");
			  	        out.println("\"firstname\": \""+rs.getString("firstname")+"\",");
			  	        out.println("\"lastname\":  \""+rs.getString("lastname")+"\",");
			  	        out.println("\"type\":  \""+rs.getString("type")+"\",");
			  	        out.println("\"active\":  \""+rs.getString("active")+"\"");
		  	        out.println("}");
	  	        first=false;
          
	          }
	          out.println("]}");
	          out.close();
	      }  catch (Exception e) {
	          e.printStackTrace();
	          throw new ServletException(e.getMessage());
	      } 
	    }else{
	    	
	    	try{
				  Connection con = DBConnectionManager.getStoredConnection(request);
		          
		          Statement stmt = con.createStatement();
		          String query = "select * from users where id=?";
		         // ResultSet rs = stmt.executeQuery(query);
		          PreparedStatement preparedStmt = con.prepareStatement(query);
		          preparedStmt.setString (1, request.getParameter("id"));
		          ResultSet rs=preparedStmt.executeQuery();
		          while(rs.next())
		          {
	
		        	  	out.println("{");
			        	    out.println("\"id\": \""+rs.getString("id")+"\",");
				  	        out.println("\"firstname\": \""+rs.getString("firstname")+"\",");
				  	        out.println("\"lastname\":  \""+rs.getString("lastname")+"\",");
				  	        out.println("\"type\":  \""+rs.getString("type")+"\",");
				  	        out.println("\"active\":  \""+rs.getString("active")+"\"");
			  	        out.println("}");
	
	          
		          }

		          out.close();
		      }  catch (Exception e) {
		          e.printStackTrace();
		          throw new ServletException(e.getMessage());
		      } 
	    	
	    }
	   
	   
	   
	   

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = resp.getWriter();
		 Connection con = DBConnectionManager.getStoredConnection(req);
		if("delete".equalsIgnoreCase(req.getParameter("query"))){
			try{
				 
		          
		          Statement stmt = con.createStatement();
		          String query = "delete from users where id=?";
		         // ResultSet rs = stmt.executeQuery(query);
		          PreparedStatement preparedStmt = con.prepareStatement(query);
		          preparedStmt.setString (1, req.getParameter("userId"));
		          int rowDeleted=preparedStmt.executeUpdate();
		          if(rowDeleted >0){
		        	     out.println("{");
		 		        out.println("\"msg\": \"Record deleted successfully......\"");
		 		      out.println("}");
		          }else{
		        	   out.println("{");
		 		        out.println("\"msg\": \"Record already deleted......\"");
		 		      out.println("}");
		          }

		          out.close();
		      }  catch (Exception e) {
		          e.printStackTrace();
		          throw new ServletException(e.getMessage());
		      } 
			
			
		 
		}else{
			try{
			String sql = "insert into Users (firstname, lastname, type, active) values (?,?,?,?)";
			 
			PreparedStatement statement = con.prepareStatement(sql);
			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
			String data = br.readLine().toString();
            Map<String,String> map=this.parsePut(data);
			statement.setString(1, map.get("firstname"));
			statement.setString(2, map.get("lastname"));
			statement.setString(3, map.get("type"));
			statement.setString(4, map.get("active"));

			 
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
			      out.println("{");
			        out.println("\"msg\": \"Record updated successfully......\"");
			      out.println("}"); 
			}else{
				 throw new ServletException("Not able to delete");
			}
			
			}catch(Exception e){
			     e.printStackTrace();
		          throw new ServletException(e.getMessage());
			}
	
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = resp.getWriter();
		 Connection con = DBConnectionManager.getStoredConnection(req);
	      
	      try{
				String sql = "UPDATE Users SET firstname=?, lastname=?, type=? , active=? WHERE id=?";
				BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
				String data = br.readLine().toString();
                Map<String,String> map=this.parsePut(data);
                System.out.println(map);
  
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setString(1, map.get("firstname"));
				statement.setString(2, map.get("lastname"));
				statement.setString(3, map.get("type"));
				statement.setString(4, map.get("active"));
				
				statement.setString(5, map.get("userid"));
				 
				int rowsUpdated = statement.executeUpdate();
				if (rowsUpdated > 0) {
				      out.println("{");
				        out.println("\"msg\": \"Record updated successfully......\"");
				      out.println("}"); 
				}else{
					 throw new ServletException("Not able to update record");
				}
				
				}catch(Exception e){
				     e.printStackTrace();
			          throw new ServletException(e.getMessage());
				}
	}

	private Map<String, String> parsePut(String value){

		value = value.substring(1, value.length()-1);           //remove curly brackets
		value = value.replace("\"", ""); //damn with this culprit caused 2 hours to identify man.......damn with this
		String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
		Map<String,String> map = new HashMap<String,String>();               
		for(String pair : keyValuePairs)                        //iterate over the pairs
		{
		    String[] entry = pair.split(":");                   //split the pairs to get key and value
		    map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
		    
		}
		return map;
	}
	
}