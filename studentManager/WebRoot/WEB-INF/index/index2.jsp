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

<title>My JSP 'index2.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script>
	var socket = new WebSocket("ws://192.168.0.120:8080/studentManager/socket");
		socket.onopen = function() {
		};
		socket.onerror = function(e) {
			alert("error " + e);
		};
		socket.onmessage = function(msg) {
			$("#count span").html(msg.data);
		};
		socket.onclose = function(e) {
			alert("connect closed:" + e.code);
		};
		
</script>
<style type="text/css">
* {
	padding: 0px;
	margin: 0px;
}

#head-1 {
	color: white;
	overflow: hidden;
	font-size: 20px;
	background-color: rgb(4, 109, 174);
	padding: 15px;
}
</style>
</head>

<body>
	<div id="head-1">欢迎来到美好时光教务管理系统
	<div id="count">
		当前在线人数:<span></span>
	</div>
	</div>
	
</body>
</html>
