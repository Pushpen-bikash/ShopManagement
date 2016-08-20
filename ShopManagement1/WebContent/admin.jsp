<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
a:link {color:#FF0000;}      /* unvisited link */
a:visited {color:#00FF00;}  /* visited link */
a:hover {color:#FF00FF;}  /* mouse over link */
a:active {color:#0000FF;}

a:link {text-decoration:none;}
a:visited {text-decoration:none;}
a:hover {text-decoration:underline;}
a:active {text-decoration:underline;}

a {font-size:50px;}
a{margin-left:400px;}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<a href="login.jsp"><b>1.Customer Login</b></a><br />
<a href="Registration.jsp"><b>2.Customer Registration</b></a><br />
<a href="delete_customer.jsp"><b>3.Delete A Customer</b></a><br />
<a href="customer_transaction_history.jsp"><b>4.Customer Transaction History</b></a><br />
<a href="customer_payment_history.jsp"><b>5.Customer Payment History</b></a><br />
<a href="http://localhost:8083/ShopManagement1/Show_customers_due"><b>6.Customers Due</b></a><br />
<a href="admin_message.jsp"><b>7.Message A Customer</b></a><br />
<a href="http://localhost:8083/ShopManagement1/AdminInboxMessages"><b>8.Inbox Messagesr</b></a><br />
<a href="http://localhost:8083/ShopManagement1/CurrentCustomers"><b>9.Current Customers</b></a>
</body>
</html>