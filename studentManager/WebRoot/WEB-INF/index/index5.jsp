<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index5.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
*{
margin:0;
padding:0;
}
			#index1,#index2{
				width: 190px;
				padding: 20px 0px;;
				text-align: center;
				background-color: #E8F2FE;
				color: #046DAE;
				font-size: 15px;
			}
			a{
				text-decoration: none;
			}
		</style>
  </head>
  
  <body>
<div id="index1">
			<a href="user?type=loginView" target="main">登录</a>
		</div>
		<div id="index2">
			<a href="user?type=adminView" target="main">注册</a>
		</div>
  </body>
</html>
