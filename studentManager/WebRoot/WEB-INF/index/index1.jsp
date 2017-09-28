<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>
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
body{
background-color: white;}
#welcome { 
	font-size: 20px;
	text-align: center;
	overflow: hidden;
	margin: 150px auto;
}

#manager {
	width: 500px;
	overflow: hidden;
	text-align: center;
	margin: auto;
}

#manager a {
	border-radius: 50%;
	border: 1px solid #666666;
	display: inline-block;
	background-color: rgb(277, 36, 33);
	padding: 10px;
	margin: 15px;
	color: white;
}
</style>
</head>

<body>
	<div id="welcome">欢迎来到学生管理系统</div>
	<div id="manager">
		<a href="showTable?type=selectStudent">学生管理</a> <a href="showBanji?type=selectBanji">班级管理</a> <a href="showSubject?type=selectSubject">课程管理</a>
		<a href="showScore?type=searchByCondition" style="width: 94px; ">成绩管理</a>
	</div>
</body>
</html>
