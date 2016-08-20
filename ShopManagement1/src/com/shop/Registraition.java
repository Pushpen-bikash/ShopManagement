package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Registraition")
public class Registraition extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	Statement stm = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	
    String customerName,address,shopeName,email,phoneNumber;
	int phn_no;
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("customerName")==null || request.getParameter("address")==null ||request.getParameter("shopeName")==null || request.getParameter("email")==null || request.getParameter("phoneNumber")==null){
			getServletContext().getRequestDispatcher("/Registration.jsp").forward(request, response);	
		}
		PrintWriter out = response.getWriter();
		
		customerName = request.getParameter("customerName");
		address = request.getParameter("address");
		shopeName = request.getParameter("shopeName");
		email = request.getParameter("email");
		phoneNumber = request.getParameter("phoneNumber");
		phn_no = Integer.parseInt(phoneNumber);
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql = "insert into customer_info2 values('"+0+"','"+customerName+"','"+shopeName+"','"+address+"','"+email+"','"+phn_no+"')";
			stm = conn.createStatement();
			
			if(stm.executeUpdate(sql)==1)
				out.print("Successufully Registered");
			else
				out.print("Registration Problem with duplicate Phone Number");
		} catch (Exception e) {		
			e.printStackTrace();
		}
		
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
