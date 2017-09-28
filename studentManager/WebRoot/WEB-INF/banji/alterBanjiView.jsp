<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'alterBanjiView.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<link href="css/addView.css" type="text/css" rel="stylesheet"> 

  </head>
  
  <body>
  <div class="login">
    <div class="message">修改班级</div>
    <div id="darkbannerwrap"></div>
    
    <form method="post" action="showBanji">
				<input type="hidden" name="type" value="alterBanji">
				<input type="hidden" name="id" value="${banji.id }">
				班级<input type="text" name="name" id="name" value="${banji.name }" /><br /> 
				<input type="submit" value="确定" style="width:100%;"  />
		<hr class="hr20">

	</form>
	
</div>
  </body>
</html>
