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

<title>My JSP 'studentView.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/addView.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/student-css.css" />

<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-theme.css.map" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<script src="js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
#list {
	overflow: hidden;
	margin-left: 40px;
	width: 400px;
	cursor: pointer;
	width: 400px;
	cursor: pointer;
	width: 500px;
}

.onclicked {
	background: #CCCCCC;
	color: white;
}

#message {
	color: red;
	position: relative;
	top: 5px;
	left: 100px;
}

select {
	width: 100px;
}

.photos {
	width: 18px;
	height: 18px;
}

#bigPhoto {
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
	$(document)
			.ready(
					function() {
						var ye = 1;
						var maxPage = 1;
						ye = ${p.ye};
						maxPage = ${p.maxPage};
						$("#upPage").click(
								function() {
									var name = $("#name").val();
									var sex = $("#sex").val();
									var age = $("#age").val();
									if (ye < 1) {
										ye = 1;
									}
									if (ye > 1) {
										location.href = "showTable?ye="
												+ (ye - 1)
												+ "&type=selectStudent&name="
												+ name + "&sex=" + sex
												+ "&age=" + age;
									}
								});

						$("#downPage").click(
								function() {
									var name = $("#name").val();
									var sex = $("#sex").val();
									var age = $("#age").val();
									if (ye > maxPage) {
										ye = maxPage;
									}
									if (ye < maxPage) {
										location.href = "showTable?ye="
												+ (ye + 1)
												+ "&type=selectStudent&name="
												+ name + "&sex=" + sex
												+ "&age=" + age;
									}
								});
						$("#addStudent").click(function() {
							location.href = "showTable?type=addView";
						});

						$("#submit").click(function() {
							location.href = "showTable?type=selectStudent";
						});

						$("tr").click(function() {
							$(this).toggleClass('onclicked');
						});

						$("#AlterStudent")
								.click(
										function() {
											var array = new Array();
											$("tr")
													.each(
															function(index,
																	element) {
																if ($(this)
																		.attr(
																				"class") == "onclicked") {
																	array
																			.push($(
																					this)
																					.find(
																							"td")
																					.eq(
																							0)
																					.text());

																}
															});

											if (array.length == 0) {
												$("#message").html("请选中一条数据");
												setTimeout(function() {
													$("#message").html("");
												}, 1000);
											} else if (array.length == 1) {
												var id = array[0];
												location.href = "showTable?type=alterView&id="
														+ id;
											} else {
												$("#message").html(
														"请只选中一条数据进行修改");
												setTimeout(function() {
													$("#message").html("");
												}, 1000);
											}
										});

						$("#deleteStudent")
								.click(
										function() {
											var array = new Array();
											$("tr")
													.each(
															function(index,
																	element) {
																if ($(this)
																		.attr(
																				"class") == "onclicked") {
																	array
																			.push($(
																					this)
																					.find(
																							"td")
																					.eq(
																							0)
																					.text());

																}
															});
											if (array.length == 0) {
												$("#message").html("请选中一条数据");
												setTimeout(function() {
													$("#message").html("");
												}, 1000);
											} else {
												var type = confirm("确认要删除数据吗？");
												if (type) {
													location.href = "showTable?type=deleteStudent&selectId="
															+ array;
												}

											}

										});
					});
</script>
</head>

<body>
	<div id="all">
		<div id="head">
			<form action="showTable?type=selectStudent" method="post">
				<input type="hidden" name="type" value="selectStudent"> 姓名<input
					type="text" name="name" id="name" value="${stu.name}" /> 性别 <input
					type="text" name="sex" id="sex" value="${condition.sex}" /> 年龄 <input
					type="text" name="age" id="age" value="${condition.age}" /> <select
					name="bj" id="bj">
					<option value="">请选择班级</option>

					<c:forEach items="${banjis}" var="banji">
						<option value="${banji.id }"
							<c:if test="${banji.id==condition.bjId }">selected</c:if>>${banji.name }</option>
					</c:forEach>
				</select> <input type="submit" name="submit" id="submit" value="确定" />
			</form>
		</div>
		<div id="teble-all">
			<div id="table">
				<table class='table table-striped table-hover'>

					<tr>
						<th>id</th>
						<th>姓名</th>
						<th>性别</th>
						<th>年龄</th>
						<th>班级</th>
						<th>图片</th>
					</tr>
					<c:forEach items="${list}" var="stu">
						<tr>
							<td>${stu.id}</td>
							<td>${stu.name }</td>
							<td>${stu.sex }</td>
							<td>${stu.age }</td>
							<td>${st1.bj.name }</td>
							<td>
								<div id="bigPhoto">
									<c:if test="${empty stu.photo  }">
										<c:set value="link.jpg" target="${stu }" property="photo" />
									</c:if>
									<img src="tu/${stu.photo }" class="photos" />
								</div>
							</td>
						<tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div id="foot">
			<label id="message"></label>
			<div id="list">
				<label id="message"></label>
				<ul class="pagination">

					<li id="upPage"><a>上一页</a></li>
					<c:forEach begin="${p.begin}" end="${p.end }" varStatus="status">
						<li name="numPage"
							<c:if test="${p.ye==status.index }"> class="active"</c:if>><a>${status.index }</a></li>
					</c:forEach>
					<li id="downPage"><a>下一页</a></li>
				</ul>
			</div>

			<div>
				<input type="button" name="addStudent" id="addStudent" value="新增学生" />
				<input type="button" name="AlterStudent" id="AlterStudent"
					value="修改学生" /> <input type="button" name="deleteStudent"
					id="deleteStudent" value="删除学生" />
			</div>
		</div>
	</div>
</body>
</html>
