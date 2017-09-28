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

<title>My JSP 'onIndex.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/login.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$("#yanzhengma")
						.click(
								function() {
									$("#yanzhengma").attr(
											"src",
											"user?type=randomImage&t="
													+ Math.random());
								});

				$("#yanzheng").blur(function() {
					var varyCode = $(this).val();

					$.ajax({
						url : "user?type=contrast&varyCode=" + varyCode,
						type : "post",
						cache : false,
						data : {

						},
						processData : false,
						contentType : false,
						dataType : "text",
						success : function(data) {
							if (data == "success") {
								$("#varyCode").html("✔").css("color", "green");
							} else {

								$("#varyCode").html("×").css("color", "red");
							}
						}
					});
				});

			});
</script>
</head>

<div class="login">
	<div class="message">登录</div>
	<div id="darkbannerwrap"></div>

	<form method="post" action="user?type=login">
		<input name="action" value="login" type="hidden"> <input
			name="username" placeholder="用户名" required="required" type="text">
		<input name="password" placeholder="密码" required="required"
			type="password">
		<hr class="hr15">
		<div>
			<label> 验证码:</label> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input
				type="radio" name="time" id="" value="1" />不保存&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type="radio" name="time" id="" value="2" checked="checked" />半分钟&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			<input type="radio" name="time" id="" value="3" />一分钟<br />
		</div>
		<hr class="hr15">
		<div id="yanDiv">
			<label> 验证码:</label> <img id="yanzhengma" alt=""
				src="user?type=randomImage"> <input name="yanzheng"
				id="yanzheng" placeholder="请输入验证码" required="required" type="text"
				style="width:50%"> <span id="varyCode"> </span>
		</div>
		<input value="登录" style="width:100%;" type="submit">
		没有注册?&nbsp&nbsp&nbsp&nbsp<a href="user?type=loginView">点击这里</a>
		<hr class="hr20">

	</form>


</div>
</body>
</html>
