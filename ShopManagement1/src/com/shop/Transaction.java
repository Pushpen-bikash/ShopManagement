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
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("productName")==null || request.getParameter("perUnitPrice")==null || request.getParameter("numberOfProduct")==null){
			getServletContext().getRequestDispatcher("/transaction.jsp").forward(request, response);
		}
		HttpSession session = request.getSession(true);
		PrintWriter out = response.getWriter();
		
		String productName = request.getParameter("productName");
		String perUnitPrice = request.getParameter("perUnitPrice");
		String numberOfProduct = request.getParameter("numberOfProduct");
		
		int per_unit_price = Integer.parseInt(perUnitPrice);
		int number_of_product = Integer.parseInt(numberOfProduct);
		int texpense = per_unit_price * number_of_product;
		
		DateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
		Date javaDate = new Date();
		//java.sql.Timestamp time= new java.sql.Timestamp(javaDate.getTime());
		String time = dateFormate.format(javaDate.getTime());
	    session.setAttribute("time", time);
		
		int id=(int)session.getAttribute("cid");
		int phn = (int)session.getAttribute("pnubmer");
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql = "INSERT INTO `transaction2`(`transaction_date`, `customer_id`, `phone_number`, `product_name`, `unite_price`, `number_of_product`, `total_expense_per_product`)" 
					+ "VALUES ('"+time+"','"+id+"','"+phn+"','"+productName+"',"+per_unit_price+",'"+number_of_product+"','"+texpense+"')";
			
			//String sql2 = "INSERT INTO `transaction2`(`transaction_date`, `customer_id`, `phone_number`, `product_name`, `unite_price`, `number_of_product`, `total_expense_per_product`) "
					//+ "VALUES (?,?,?,?,?,?,?)";
			
			
			//PreparedStatement ps = conn.prepareStatement(sql2);
			//ps.setTimestamp(1, time);
			stm = conn.createStatement();
			if(stm.executeUpdate(sql)==1){
				out.print("\n provide next");
				getServletContext().getRequestDispatcher("/transaction.jsp").forward(request, response);
			}else{
				out.print("\nInsertion Problem");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
