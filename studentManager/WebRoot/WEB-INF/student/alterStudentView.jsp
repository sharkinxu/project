<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'alterStudentView.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link href="css/addView.css" type="text/css" rel="stylesheet">
<style type="text/css">
#AlterPhoto-1,#AlterPhoto-2,#touxiang {
	float: left;
	margin-left:17px;
}
.photos {
	width: 50px;
	height: 50px;
}

#AlterPhoto-1 {
	position: relative;
}

img:hover {
	position: absolute;
	left: -40px;
	top: -40px;
	width: 100px;
	height: 100px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		alert("id=" + ${request.stu.id});
	});
</script>
</head>

<body>
	<div class="login">
		<div class="message">添加学生</div>
		<div id="darkbannerwrap"></div>

		<form method="post" action="showTable?type=alterStudent"
			enctype="multipart/form-data">
			<input type="hidden" name="id" value="${stu.id }"> 姓名 <input
				type="text" name="name" id="name" value="${stu.name }" /><br /> 性别<select
				name="sex" id="sex"> 
				<option value="">请选择性别</option>
				<option value="男" <c:if test="${stu.sex eq '男'}">selected</c:if>>男</option>
				<option value="女" <c:if test="${stu.sex eq '女'}">selected</c:if>>女</option>
			</select></br> 年龄 <input type="text" name="age" id="age" value="${stu.age }" /><br />
			<select name="bj" id="bj">
				<option value="0">请选择班级</option>
				<c:forEach items="${banjis}" var="banji">
					<option value="${banji.id }"
						<c:if test="${banji.id==stu.bj.id }">selected</c:if>>${banji.name }</option>

				</c:forEach>

			</select>
			头像
			<div id="AlterPhoto">

				<div id="AlterPhoto-1">
					<c:if test="${empty stu.photo  }">
						<c:set value="link.jpg" target="${stu }" property="photo" />
					</c:if>
					<img src="tu/${stu.photo }" class="photos" />
				</div>
				
				<div id="AlterPhoto-2">
					<input type="file" name="myFile" />
				</div>
			</div>
			<input type="submit" value="确定" style="width:100%;" />
			<hr class="hr20">

		</form>

	</div>
</body>
</html>
