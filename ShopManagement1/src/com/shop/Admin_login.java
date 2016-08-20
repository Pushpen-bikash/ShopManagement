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

@WebServlet("/Admin_login")
public class Admin_login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("adminPassword")==null){
			getServletContext().getRequestDispatcher("/admin_login.jsp").forward(request, response);
		}
		
		PrintWriter out = response.getWriter();
		String pass = request.getParameter("adminPassword");
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql = "SELECT password FROM adminpassword";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while(rs.next()){
			 String	password = rs.getString(1);
			 if(password.equals(pass)){
					getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
					out.println("ok");
					break;
				}else{
					out.println("Wrong Password");
					break;
				}
			}
			//out.print("Password :"+password+" "+"Pass :"+pass);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
