package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CustomersMessage")
public class CustomersMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql = "select sentDate,message from adminsmessage where message_check='No'";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){
				String date = rs.getString(1);
				String message = rs.getString(2);
				out.println(date +"&nbsp");
				out.println(message+"<BR><BR>");
			}
			String sql2 = "update adminsmessage set message_check='Yes' where message_check='No'";
			stm = conn.createStatement();
			if(stm.executeUpdate(sql2)==1){
				out.println("Message Check Status Set to 'Yes'");
			}else{
				out.println("Problem occur or No unread Messages");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
