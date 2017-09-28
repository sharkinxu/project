<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index4.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
 * {
	padding: 0px;
	margin: 0px;
}
        	#head-1{
       		color: white;
        	overflow: hidden;
        	font-size: 20px;
        	background-color:rgb(4,109,174);
        	padding: 28px; 	
        	}
        	#myName{
        	font-size:10px;
        	text-align: right;
        	}
        </style>
  </head>
  
  <body>
	    <div id="head-1">
    		 美好时光教务管理系统
    		 <div id="myName">
    		 作者:07java三班 &nbsp &nbsp徐洋
    		 </div>
    	</div>	
  </body>
</html>
