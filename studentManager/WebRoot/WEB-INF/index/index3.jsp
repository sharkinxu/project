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

<title>My JSP 'index3.jsp' starting page</title>

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

.nei  a {
	color: black;
	text-decoration: none;
	border: solid, black, 1px;
	line-height: 33px;
	display: inline-block;
	width: 140px;
	text-align: center;
}

#u1 {
	margin: 20px;
}

li {
	list-style: none;
	width: 150px;
	font-size: 15px;
	border: solid, black, 1px;
	background-color: #C9E3F3;
}

#u1>li {
	margin-top: 5px;
	border-radius: 0px 0px 5px 5px;
	line-height: 33px;
	text-align: center;
	cursor: pointer;
}

.nei li {
	font-size: 12px;
	width: 140px;
	position: relative;
	left: 4px;
}

li:hover {
	color: white;
	background-color: #3EAFE0;
}
</style>
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#u1>li").each(function() {
			$(this).click(function() {
				$(this).next().slideToggle(100);
			});
		});
	})
</script>
</head>

<body style="background-color:#E8F2FE;">
	<ul id="u1">
		<li class="wai">学生管理</li>
		<div id="" style="display: none;">
			<ul class="nei">
				<li><a href="showTable?type=selectStudent" target="main">查看学生</a></li>
				<li style=" border-radius:0px 0px 5px 5px; "><a
					href="showTable?type=addView" target="main">添加学生</a></li>
			</ul>
		</div>
		<li class="wai">班级管理</li>
		<div>
			<ul class="nei">
				<li><a href="showBanji?type=selectBanji" target="main">查看班级</a></li>
				<li style=" border-radius:0px 0px 5px 5px; "><a
					href="showBanji?type=addView" target="main">添加班级</a></li>
			</ul>
		</div>
		<li class="wai">课程管理</li>
		<div>
			<ul class="nei ">
				<li><a href="showSubject?type=selectSubject" target="main">查看课程</a></li>
				<li style=" border-radius:0px 0px 5px 5px; "><a
					href="showSubject?type=addSubject" target="main">添加课程</a></li>
			</ul>
		</div>
		<li class="wai">成绩管理</li>
		<div>
			<ul class="nei ">
				<li style=" border-radius:0px 0px 5px 5px; "><a href="showScore?type=searchByCondition" target="main">查看成绩</a></li>
				
			</ul>
		</div>
	</ul>
</body>
</html>
