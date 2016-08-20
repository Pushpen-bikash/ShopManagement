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

@WebServlet("/Delete_Customer")
public class Delete_Customer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("phn_no")==null){
			getServletContext().getRequestDispatcher("/delete_customer.jsp").forward(request, response);
		}
		
		PrintWriter out = response.getWriter();
		String phn_no = request.getParameter("phn_no");
		int phnNo = Integer.parseInt(phn_no);
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql = "delete from customer_info2 where phone_number='"+phnNo+"'";
			stm = conn.createStatement();
			if(stm.executeUpdate(sql)==1){
				out.println("Successufully Deleted");
			}else{
				out.println("There is some proble");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
