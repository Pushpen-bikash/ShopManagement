package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


@WebServlet("/Payment")
public class Payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection conn = null;
	Statement stm = null;
	Statement stm2 = null;
	ResultSet rs = null;
	ResultSet rs2 = null;
	PreparedStatement ps = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	double totalExpense,previousDue,totalCost;
	int cid;
	String time;
	HttpSession session;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("payAmount")==null){
			
			/*payAmount first a null thakba.Null pala mana ai block a database thaka value niea total expense
			 bar korta hoba.Sata setParameter diea set kora payment.jsp ta pathai diea oi khana print diea
			 dita hoba*/
		     session = request.getSession(true);
			 cid = (int)session.getAttribute("cid");
			 time = (String)session.getAttribute("time");
			try {
				Class.forName(jdbc_driver);
				conn = DriverManager.getConnection(jdbc_url,"root","");
				String sql = "select sum(total_expense_per_product) from transaction2 where transaction_date='"+time+"' and customer_id='"+cid+"'";
				String sql2 = "select due from payment2 where customer_id='"+cid+"' and due_inc= (select max(due_inc) from payment2 where customer_id='"+cid+"' group by customer_id)";
				stm = conn.createStatement();
				stm2 = conn.createStatement();
				rs = stm.executeQuery(sql);
				rs2 = stm2.executeQuery(sql2);
				while(rs.next()){
					totalExpense = rs.getDouble(1);
				}
				while(rs2.next()){
					previousDue = rs2.getDouble(1);
				}
				session.setAttribute("totalExpense", totalExpense);
				session.setAttribute("previousDue", previousDue);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//request.setAttribute("totalExpense",totalExpense);
			getServletContext().getRequestDispatcher("/payment.jsp").forward(request, response);
		}else{
		/*ar jokhyn payAmount null thakba na tokhun mana ai block payAmount ar value ta niea sata total expense
		 thaka bad diea database a insert kora dita hoba*/
			PrintWriter out = response.getWriter();
			//out.print("In Payment.java "+cid+" "+time+" "+totalExpense);
			
			int phn = (int)session.getAttribute("pnubmer");
			String payformInput = request.getParameter("payAmount");
			double pay = Double.parseDouble(payformInput);
			
			totalCost = totalExpense+previousDue;
			double due = (totalExpense+previousDue)-pay;
			
			out.print("\n"+" "+pay+" "+due);
			
			session.setAttribute("due", due);
			session.setAttribute("pay", pay);
			session.setAttribute("totalCost", totalCost);
			
			DateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
			Date javaDate = new Date();
			String time2 = dateFormate.format(javaDate.getTime());
			
		    String sql2 ="INSERT INTO `payment2`(`customer_id`, `phone_number`, `transaction_date`, `total_expense`, `pay`, `due`) VALUES (?,?,?,?,?,?)";
		    try {
				ps = conn.prepareStatement(sql2);
				ps.setInt(1, cid);
				ps.setInt(2, phn);
				ps.setString(3, time2);
				ps.setDouble(4, totalExpense);
				ps.setDouble(5, pay);
				ps.setDouble(6, due);
				if(ps.executeUpdate()==1){
					out.print("Payment Successful");
					getServletContext().getRequestDispatcher("/forPrinting.jsp").forward(request, response);
				}else{
					out.print("There is some problem");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
