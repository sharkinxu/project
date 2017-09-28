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

<title>My JSP 'SubjectView.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/addView.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/banji-css.css" />

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
#addSubject,#AlterSubject,#deleteSubject{
width:20%;
margin:0px 40px 0px 30px;
}
</style>
<script type="text/javascript">
	$(document).ready(
			function() {
				var ye = 1;
				var maxPage = 1;
				ye = ${p.ye};
				maxPage = ${p.maxPage};

				$("#upPage").click(
						function() {
							var name = $("#name").val();
				
							if (ye < 1) {
								ye = 1;
							}
							if (ye > 1) {
								location.href = "showSubject?ye=" + (ye - 1)
										+ "&type=selectSubject&name=" + name;
							}
						});
						
				$("#downPage").click(
						function() {
							var name = $("#name").val();
							
							if (ye > maxPage) {
								ye = maxPage;
							}
							if (ye < maxPage) {
								location.href = "showSubject?ye=" + (ye + 1)
										+ "&type=selectSubject&name=" + name;
							}
						});
				$("#addSubject").click(function() {
					location.href = "showSubject?type=addView";
				});

				$("#submit").click(function() {
						location.href = "showSubject?type=selectSubject";
				});
			
				$("tr").click(function() {
					$(this).toggleClass('onclicked');
				});
				
				$("#AlterSubject").click(function() {
				var array = new Array();
						$("tr").each(function(index, element) {
				if ($(this).attr("class") == "onclicked") {
					array.push($(this).find("td").eq(0).text());
				
				}
				});
				
				if (array.length == 0) {
				$("#message").html("请选中一条数据");
						setTimeout(function() {
							$("#message").html("");
						}, 1000);
				} else if(array.length==1){
					var id =array[0];
					location.href = "showSubject?type=alterView&id=" + id;
				}else{
					$("#message").html("请只选中一条数据进行修改");
					setTimeout(function() {$("#message").html("");}, 1000);	
					}
				});
				
				
				
				$("#deleteSubject").click(
						function() {
						var array = new Array();
						$("tr").each(function(index, element) {
				if ($(this).attr("class") == "onclicked") {
					array.push($(this).find("td").eq(0).text());
				
				}});
				if (array.length == 0) {
				$("#message").html("请选中一条数据");
						setTimeout(function() {
							$("#message").html("");
						}, 1000);
			} else {
				var type = confirm("确认要删除数据吗？");
				if (type) {
					location.href = "showSubject?type=deleteSubject&selectId=" + array;
				}

			}
			
						
			});
			});
</script>
</head>

<body>
	<div id="all">
		<div id="head">
			<form action="showSubject?type=selectSubject" method="post">
				<input type="hidden" name="type" value="selectSubject"> 课程名称<input
					type="text" name="name" id="name" value="${session.SubjectName}" /><input
					type="submit" name="submit" id="submit" value="确定" />
			</form>
		</div>
		<div id="teble-all">
			<div id="table">
				<table class='table table-striped table-hover'>

					<tr>
						<th>id</th>
						<th>课程名称</th>
					
					</tr>
					<c:forEach items="${list}" var="subject">
						<tr>
							<td>${subject.id}</td>
							<td>${subject.name }</td>
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
				<input type="button" name="addSubject" id="addSubject" value="新增课程" />
				<input type="button" name="AlterSubject" id="AlterSubject"
					value="修改课程" /> <input type="button" name="deleteSubject"
					id="deleteSubject" value="删除班级" />
			</div>
		</div>
	</div>
</body>
</html>
