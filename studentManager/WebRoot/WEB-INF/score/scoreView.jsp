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
<link rel="stylesheet" type="text/css" href="css/score-css.css" />

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
</style>
<script type="text/javascript">
	$(document).ready(
			function() {
				var ye = 1;
				var maxPage = 1;
				ye = ${p.ye};
				maxPage = ${p.maxPage};
				var stuName = $("#stuName").val();
				var subId = $("#subId").val();
				var bjId = $("#bjId").val();
				$("#upPage").click(
						function() {

							if (ye < 1) {
								ye = 1;
							}
							if (ye > 1) {
								location.href = "showScore?ye=" + (ye - 1)
										+ "&type=searchByCondition&stuName="
										+ stuName + "&subId=" + subId
										+ "&bjId=" + bjId;
							}
						});

				$("#downPage").click(
						function() {

							if (ye > maxPage) {
								ye = maxPage;
							}
							if (ye < maxPage) {
								location.href = "showScore?ye=" + (ye + 1)
										+ "&type=searchByCondition&stuName="
										+ stuName + "&subId=" + subId
										+ "&bjId=" + bjId;
							}
						});

				$("tr").click(function() {
					$(this).toggleClass('onclicked');
				});

				$("#bjId").change(
						function() {
							var bjId = $(this).val();
							$.ajax({
								type : "post",
								data : {
									"type" : "subjectShow",
									"bjId" : bjId
								},
								url : "showScore",
								datatype : "json",
								success : function(data) {
									data = JSON.parse(data);

									var ops = "<option value='0' >请选择数据</option>";
									for (var i = 0; i < data.length; i++) {
										var id = data[i].id;
										var name = data[i].name;
										ops += "<option value="+ id+">" + name
												+ "</option>";
									}

									$("#subId").html("");
									$("#subId").html(ops);
								}

							});
						});

				$("#table input").each(
						function() {
							var oldScore;
							$(this).focus(function() {
								oldScore = $(this).val();
							});
							$(this).blur(
									function() {
										var upSubId = $(this).parent().prev()
												.attr("data-id");
										var upStuId = $(this).parent().parent()
												.find("td").eq(0).html();

										var newScore = $(this).val();

										$.ajax({
											type : "post",
											data : {
												"type" : "alterScore",
												"upSubId" : upSubId,
												"upStuId" : upStuId,
												"oldScore" : oldScore,
												"newScore" : newScore

											},
											url : "showScore",
											datatype : "json",
											success : function(data) {
												$(this).val(data);
											}

										});

									});
						});

			});
</script>
</head>

<body>
	<div id="all">
		<div id="head">
			<form action="showScore?type=searchByCondition" method="post">
				<input type="hidden" name="type" value="searchByCondition">
				姓名<input type="text" name="stuName" id="stuName"
					value="${condition.stu.name}" /> <select name="bjId" id="bjId">
					<option value="0">请选择班级</option>
					<c:forEach items="${banjis}" var="banji">
						<option value="${banji.id }">${banji.name }</option>

					</c:forEach>
				</select> <select name="subId" id="subId">
					<option value="">请选择课程</option>
					<c:forEach items="${subjects}" var="sub">
						<option value="${sub.id }">${sub.name }</option>

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
						<th>班级</th>
						<th>课程</th>
						<th>成绩</th>
					</tr>
					<c:forEach items="${list}" var="score">
						<tr>
							<td>${score.stu.id}</td>
							<td>${score.stu.name}</td>
							<td>${score.stu.bj.name }</td>
							<td data-id="${score.sub.id }">${score.sub.name }</td>
							<td><input type="text" name="type"
								<c:choose>
								<c:when test="${score.score == -1}">
								placeholder="未录入"
								</c:when>
								<c:otherwise>
								value="${score.score}"
								</c:otherwise>
								</c:choose>>
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
		</div>
	</div>
</body>
</html>
