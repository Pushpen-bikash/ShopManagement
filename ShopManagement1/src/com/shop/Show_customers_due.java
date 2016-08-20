package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Show_customers_due")
public class Show_customers_due extends HttpServlet {
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
			String sql = "select phone_number,min(due) from payment2 group by customer_id";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			response.setContentType("text/html");
			out.println("<html>");
			
			out.println("<table border='2' align='left'>");
			out.println("<tr>");
			out.println("<th><b>Phone Number</b></th>");
			out.println("<th><b>Due</b></th>");
			out.println("</tr>");
			
			while(rs.next()){
				out.println("<tr>");
	            out.println("<td>"+rs.getInt(1)+"</td>");
	            out.println("<td>"+rs.getDouble(2)+"</td>");
	            out.println("</tr>");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
