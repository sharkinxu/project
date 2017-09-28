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

<title>My JSP 'addStudentView.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/addView.css" type="text/css" rel="stylesheet">
<style type="text/css">
img {
	width: 60px;
	heigit: 60px;
}
</style>
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$("#upload").click(function() {

					var length = $("#photos img").length;
					if (length < 4) {
						$.ajax({
							url : "showTable?type=upload",
							type : "post",
							cache : false,
							data : new FormData($("#uploadFrom")[0]),
							processData : false,
							contentType : false,
							dataType : "text",
							success : function(data) {

								str = "<img  src='tu/"+data+"'/>";
								$("#photos").append(str);
							}
						});
					}
				});
				$("#addSure").click(
						function() {
							var name = $('#name').val();
							var sex = $('#sex').val();
							var age = parseInt($('#age').val());
							var bj_id =$('#bj').val();
							var photos = "";
							
							$("img").each(function() {

								var src = $(this).attr("src");

								var photo = src.split("/")[1];
								photos += photo + ":";
							});
							var src = "showTable?type=add2&name=" + name
									+ "&sex=" + sex + "&age=" + age + "&bj_id="+bj_id+"&photos="
									+ photos
							alert(src);
							location.href = src;
						});

			});
</script>

</head>

<body>
	<div class="login">
		<div class="message">添加学生</div>
		<div id="darkbannerwrap"></div>
		<input type="text" name="name" id="name" value="" placeholder="请输入姓名" />
		<select name="sex" id="sex">
			<option value="">请选择性别</option>
			<option value="男">男</option>
			<option value="女">女</option>
		</select> <input type="text" name="age" id="age" value="" placeholder="请输入年龄" />
		<select name="bj" id="bj">
			<option value="0">请选择班级</option>
			<c:forEach items="${banjis}" var="banji">
				<option value="${banji.id }">${banji.name }</option>

			</c:forEach>
		</select>
		<div id="photos"></div>
		<div>
			<form method="post" id="uploadFrom" action="showTable?type=upLoad"
				enctype="multipart/form-data">
				<input type="file" name="photo" style="width:60%;" /> <input
					id="upload" type="button" value="上传" style="width:38%;" />
			</form>
			<input type="button" value="确定" id="addSure" />

		</div>



		<hr class="hr20">

	</div>
</body>
</html>
