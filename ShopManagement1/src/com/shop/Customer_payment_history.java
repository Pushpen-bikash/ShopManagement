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

@WebServlet("/Customer_payment_history")
public class Customer_payment_history extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("phn_no")==null){
			getServletContext().getRequestDispatcher("/customer_payment_history.jsp").forward(request, response);
		}
			
			PrintWriter out = response.getWriter();
			
			String phn_no = request.getParameter("phn_no");
			String date = request.getParameter("date");
			int phnNo = Integer.parseUnsignedInt(phn_no);
			
			try {
				Class.forName(jdbc_driver);
				conn = DriverManager.getConnection(jdbc_url,"root","");
				String sql1 = "SELECT  transaction_date, total_expense, pay, due FROM payment2 WHERE phone_number='"+phnNo+"'";
				String sql2 = "SELECT  transaction_date, total_expense, pay, due FROM `payment2 WHERE phone_number='"+phnNo+"' and tratransaction_date='"+date+"'";
				stm = conn.createStatement();
				response.setContentType("text/html");
				out.println("<html>");
				
				out.println("<table border='2' align='left'>");
				out.println("<tr>");
				out.println("<th><b>Transaction Date</b></th>");
				out.println("<th><b>Total Expense</b></th>");
				out.println("<th><b>Pay</b></th>");
				out.println("<th><b>Due</b></th>");
				out.println("</tr>");
				
				
				if(date.length()==0){
				rs = stm.executeQuery(sql1);
				while(rs.next()){
					out.println("<tr>");
		            out.println("<td>"+rs.getString(1)+"</td>");
		            out.println("<td>"+rs.getDouble(2)+"</td>");
		            out.println("<td>"+rs.getDouble(3)+"</td>");
		            out.println("<td>"+rs.getDouble(4)+"</td>");
		            out.println("</tr>");
				}
				}else{
					rs = stm.executeQuery(sql2);
					while(rs.next()){
						out.println("<tr>");
			            out.println("<td>"+rs.getString(1)+"</td>");
			            out.println("<td>"+rs.getDouble(2)+"</td>");
			            out.println("<td>"+rs.getDouble(3)+"</td>");
			            out.println("<td>"+rs.getDouble(4)+"</td>");
			            out.println("</tr>");	
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

