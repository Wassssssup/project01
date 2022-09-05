<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>DB 연동</h4>
DBCP(DataBase Connection Pool)

<%
	Context context=null;
	DataSource dataSource=null;
	Connection connection=null;
	
	try{
		context=new InitialContext();
		dataSource=(DataSource)context.lookup("java:/comp/emv/jdbc/Oracle11g");
		connection=dataSource.getConnection();
	}catch(Exception e){
		e.printStackTrace();
	}
	out.println("DBCP 연동 성공");
%>
</body>
</html>