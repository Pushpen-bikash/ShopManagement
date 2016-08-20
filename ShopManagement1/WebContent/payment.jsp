<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<%
out.println("In payment.jsp\n");
out.println("<BR>");
double tExpense = (double)session.getAttribute("totalExpense");
double pDue = (double)session.getAttribute("previousDue");
double total_cost = pDue+tExpense;
out.println("\nPrevious Due :"+pDue);
out.println("<BR>");
out.println("\nToday Expense :"+tExpense);
out.println("<BR>");
out.println("\nTotal Cost: "+total_cost);
%>
<div>
<form action="Payment" method="post">
Pay Amount:<input type="text" name="payAmount"/><br />
<input type="submit" value="Pay"/>
</form>
</div>
</body>
</html>