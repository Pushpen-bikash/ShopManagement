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
import javax.servlet.http.HttpSession;


@WebServlet("/TransactionHistoryFromCustomerPage")
public class TransactionHistoryFromCustomerPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	HttpSession session;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		session = request.getSession(true);
		int cid= (int)session.getAttribute("cid");
		int phnNo= (int)session.getAttribute("pnubmer");
		String date = request.getParameter("date");
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql1 = "SELECT transaction_date,product_name,unite_price, number_of_product, total_expense_per_product FROM transaction2 WHERE phone_number='"+phnNo+"' and customer_id='"+cid+"'";
			String sql2 = "SELECT transaction_date,product_name,unite_price, number_of_product, total_expense_per_product FROM transaction2 WHERE phone_number='"+phnNo+"' and customer_id='"+cid+"' and transaction_date='"+date+"'";
			stm = conn.createStatement();
			response.setContentType("text/html");
			out.println("<html>");
			
			out.println("<table border='2' align='left'>");
			out.println("<tr>");
			out.println("<th><b>Transaction Date</b></th>");
			out.println("<th><b>Product Name</b></th>");
			out.println("<th><b>Unit Price</b></th>");
			out.println("<th><b>Number Of Products</b></th>");
			out.println("<th><b>Total Expense Per Product</b></th>");
			out.println("</tr>");
			
			
			if(date.length()==0){
			rs = stm.executeQuery(sql1);
			while(rs.next()){
				out.println("<tr>");
	            out.println("<td>"+rs.getString(1)+"</td>");
	            out.println("<td>"+rs.getString(2)+"</td>");
	            out.println("<td>"+rs.getDouble(3)+"</td>");
	            out.println("<td>"+rs.getInt(4)+"</td>");
	            out.println("<td>"+rs.getDouble(5)+"</td>");
	            out.println("</tr>");
			}
			}else{
				rs = stm.executeQuery(sql2);
				while(rs.next()){
					out.println("<tr>");
		            out.println("<td>"+rs.getString(1)+"</td>");
		            out.println("<td>"+rs.getString(2)+"</td>");
		            out.println("<td>"+rs.getDouble(3)+"</td>");
		            out.println("<td>"+rs.getInt(4)+"</td>");
		            out.println("<td>"+rs.getDouble(5)+"</td>");
		            out.println("</tr>");	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
