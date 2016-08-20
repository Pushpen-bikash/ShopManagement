package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AdminsMessage")
public class AdminsMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	int cid;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String phn_no = request.getParameter("to");
		String message = request.getParameter("message");
		int phnNo = Integer.parseInt(phn_no);
		
		DateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
		Date javaDate = new Date();
		String sentDate = dateFormate.format(javaDate.getTime());
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql = "select customer_id from customer_info2 where phone_number='"+phnNo+"'";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){
				cid = rs.getInt(1);
			}
			String sql2 = "INSERT INTO adminsmessage(sentDate, customer_id, phn_no, message, message_check) VALUES (?,?,?,?,?)";
			ps = conn.prepareStatement(sql2);
			ps.setString(1, sentDate);
			ps.setInt(2, cid);
			ps.setInt(3, phnNo);
			ps.setString(4, message);
			ps.setString(5, "No");
			if(ps.executeUpdate()==1){
				out.println("Message Sent Successfully");
			}else{
				out.println("Problems occur while sending");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
