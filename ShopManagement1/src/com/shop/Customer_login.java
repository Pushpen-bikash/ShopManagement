package com.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Customer_login
 */
@WebServlet("/Customer_login")
public class Customer_login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection conn = null;
	Statement stm = null;
	ResultSet rs = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost/EXP";
	
    String customerName,address,shopeName,email,phoneNumber;
	int phn_no;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		
		/*if(request.getParameter("lcustomerName")==null ||request.getParameter("lshopeName")==null || request.getParameter("lemail")==null || request.getParameter("lphoneNumber")==null){
			getServletContext().getRequestDispatcher("/customer_login.jsp").forward(request, response);	
		}*/
		
		customerName = request.getParameter("lcustomerName").toString();
		shopeName = request.getParameter("lshopeName");
		email = request.getParameter("lemail");
		phoneNumber = request.getParameter("lphoneNumber");
		phn_no = Integer.parseInt(phoneNumber);
		out.print(phn_no+" "+customerName);
		
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"root","");
			stm = conn.createStatement();
		    String sql = "SELECT customer_id,customer_name,shop_name,email,phone_number from customer_info2";
		    rs = stm.executeQuery(sql);
		    int go1=0;
		    int go2=0;
		    int id = 0;
		    while(rs.next()){
		    	
		        id = rs.getInt("customer_id");
		    	String name = rs.getString("customer_name");
		    	String shop = rs.getString("shop_name");
		    	String eml = rs.getString("email");
		    	int pno = rs.getInt("phone_number");
		  
		    	out.print(pno);
		    	out.print(" "+name);
		    	int checkp=0;
		    	int checkn=0;
		    	if(pno==phn_no)
		    	{
		    		checkp=1;
		    	}
		    	if(name.equals(customerName)){
	    			checkn=1;
	    		}
		    	
		    	if(checkp==1 && checkn==1){
		    		go1=1;
		    		break;
		    	}
		    	else if(checkp==1 && checkn==0){
		    		out.print("Name is not matched ");
		    		go2=1;
		    		break;
		    	}
		    	/*else{
		    		out.print("Provide Valid informations .");
		    		break;
		    	}*/
		    }//end of while
		    
		    if(go2==0){
		    	if(go1==1 && go2==0){
		    		session.setAttribute("cname", customerName);
		    		session.setAttribute("cid", id);
		    		session.setAttribute("pnubmer", phn_no);
			    	getServletContext().getRequestDispatcher("/customer.jsp").forward(request, response);
			    }else{
			    	out.print("PhoneNumber Not found");
			    }
		    }
		    /*if(go1==1)
		    {
		    	request.setAttribute("customerName", customerName);
		    	request.setAttribute("id", id);
		    	getServletContext().getRequestDispatcher("/transaction.jsp").forward(request, response);
		    }else{
		    	out.print("PhoneNumber Not found");
		    }*/
		
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
