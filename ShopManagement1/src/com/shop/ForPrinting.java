package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.servlet.http.HttpSession;

@WebServlet("/ForPrinting")
public class ForPrinting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement stm1,stm2,stm3 = null;
	ResultSet rs1,rs2,rs3 = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	int cid,phn;
	String customer_name,product_Name;
	int phone_number,number_of_Product;
	double unit_price,total_expense_per_product;
	HttpSession session;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession(true);
		double totalExpense = (double)session.getAttribute("totalExpense");
		double previousDue = (double)session.getAttribute("previousDue");
		double totalCost = (double)session.getAttribute("totalCost");
		double pay = (double)session.getAttribute("pay");
		double due = (double)session.getAttribute("due");
		
		int cid = (int)session.getAttribute("cid");
		int phn = (int)session.getAttribute("pnubmer");
		
		DateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
		Date javaDate = new Date();
		String time2 = dateFormate.format(javaDate.getTime());
		
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			String sql1 = "SELECT customer_name from customer_info2 where customer_id='"+cid+"'";
			String sql2 = "SELECT product_name,unite_price,number_of_product,total_expense_per_product FROM transaction2 where customer_id='"+cid+"' and transaction_date='"+time2+"'";
			//String sql3 = "SELECT total_expense,pay,due FROM payment2 where customer_id='"+cid+"' and transaction_date='"+time2+"'";
			stm1 = conn.createStatement();
			stm2 = conn.createStatement();
			//stm3 = conn.createStatement();
			rs1 = stm1.executeQuery(sql1);
			rs2 = stm2.executeQuery(sql2);
			//rs3 = stm3.executeQuery(sql3);
			response.setContentType("text/html");
			out.println("<html>");
			while(rs1.next()){
				customer_name = rs1.getString(1);
				out.println("Date :"+time2+"<BR>");
				out.print("Customer Name:"+customer_name+"<BR><BR>");
			}
			if(totalExpense!=0){
				out.println("<table border='2' align='left'>");
				out.println("<tr>");
				out.println("<th><b>Product Name</b></th>");
				out.println("<th><b>Unit Price</b></th>");
				out.println("<th><b>Number Of Products</b></th>");
				out.println("<th><b>Total Expense Per Product</b></th>");
				out.println("<th><b>Previous Due</b></th>");
				out.println("<th><b>Today Expense</b></th>");
				out.println("<th><b>Total Cost</b></th>");
				out.println("<th><b>Pay</b></th>");
				out.println("<th><b>Due</b></th>");
				out.println("</tr>");
			}
			if(totalExpense!=0){
			while(rs2.next()){
	             product_Name              = rs2.getString(1);
	             unit_price                = rs2.getDouble(2);
	             number_of_Product         = rs2.getInt(3);
	             total_expense_per_product = rs2.getDouble(4);
	             /*out.print("Product Name             : "+product_Name+"<BR>");
	             out.print("Unit Price               : "+unit_price+"<BR>");
	             out.print("Number Of Products       : "+number_of_Product+"<BR>");
	             out.print("Total Expense Per Product: "+total_expense_per_product+"<BR><BR>");*/
	            out.println("<tr>");
	            out.println("<td>"+product_Name+"</td>");
	            out.println("<td>"+unit_price+"</td>");
	            out.println("<td>"+number_of_Product+"</td>");
	            out.println("<td>"+total_expense_per_product+"</td>");
	            out.println("</tr>");
			}
			}
			if(totalExpense!=0){
				/*out.print("Previous Due :"+previousDue+"<BR>");
				out.print("Total Cost :"+totalCost+"<BR>");
				out.print("Pay :"+pay+"<BR>");
				out.print("Due :"+due+"<BR>");*/
				out.println("<tr>");
				out.println("<td colspan='4'></td>");
				out.println("<td>"+previousDue+"</td>");
				out.println("<td>"+totalExpense+"</td>");
				out.println("<td>"+totalCost+"</td>");
				out.println("<td>"+pay+"</td>");
				out.println("<td>"+due+"</td>");
				//out.println("/tr");
				//out.println("/table");
			}
			
			/*while(rs3.next()){
				
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(totalExpense==0){
		/*out.print("PhoneNumber :"+phn);
		out.print("<BR>");
		out.print("Date :"+time2);
		out.print("<BR>");
		out.print("Today Expense:"+totalExpense);
		out.print("<BR>");
		out.print("Previous Due :"+previousDue);
		out.print("<BR>");
		out.print("Total Cost :"+totalCost);
		out.print("<BR>");
		out.print("Pay :"+pay);
		out.print("<BR>");
		out.print("Due :"+due);*/
			out.println("<table border='2' align='center'>");
			out.println("<tr>");
			out.println("<th><b>Today Expense</b></th>");
			out.println("<th><b>Previous Due</b></th>");
			out.println("<th><b>Total Cost</b></th>");
			out.println("<th><b>Pay</b></th>");
			out.println("<th><b>Due</b></th>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>"+totalExpense+"</td>");
			out.println("<td>"+previousDue+"</td>");
			out.println("<td>"+totalCost+"</td>");
			out.println("<td>"+pay+"</td>");
			out.println("<td>"+due+"</td>");
			out.println("</tr>");
			//out.println("</table>");
	}
		//out.println("/html");
	}

}
