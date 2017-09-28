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

<title>My JSP 'regrster.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="css/normalize.css">
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/script.js"></script>
<style type="text/css">
 #yanzhengma{
 padding-left:10px;
	float: left;
 }
</style>
</head>
<body>
	<div class="reg_div">
		<div class="message">注册用户信息</div>
		<div id="darkbannerwrap"></div>
		<form action="user?type=admin" method="post">



			<ul class="reg_ul">
				<li><span>用户名：</span> <input type="text" name="username"
					value="" placeholder="4-8位用户名" class="reg_user"> <span
					class="tip user_hint"></span></li>
				<li><br /> <span>密码：</span> <input type="password"
					name="password" value="" placeholder="6-16位密码" class="reg_password">
					<span class="tip password_hint"></span></li>
				<li><br /> <span>确认密码：</span> <input type="password" name=""
					value="" placeholder="确认密码" class="reg_confirm"> <span
					class="tip confirm_hint"></span></li>
				<li><br /> <span>验证码：</span> <img id="yanzhengma" alt=""
					src="user?type=randomImage"> <input type="text" id="input" />
					<span class="tip yanzheng_hint"></span></li>
				<li><br /> <br /> <input type="submit" value="注册"
					class="red_button" /></li>
			</ul>
		</form>
	</div>
	<div style="text-align:center;"></div>
</body>
</html>
