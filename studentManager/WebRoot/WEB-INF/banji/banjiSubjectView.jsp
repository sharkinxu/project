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

<title>My JSP 'banjiSubjectView.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="css/addView.css" type="text/css" rel="stylesheet">
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

#addSubject,#deleteSubject {
	width: 100px;
	margin: 0px 20px;
	padding: 5px;
}

body {
	background-color: white;
}

#button-subject {
	width: 500px;
	margin: auto;
	padding-left: 50px;
}

#subject-list {
	padding: 30px;
	border: 1px #ccc solid;
	border-radius: 30px;
	margin: 20px auto;
	width: 240px;
	height: 200px;
	overflow: hidden;
	width: 600px;
}

li {
	float: left;
	list-style: none;
	width: 100px;
	padding: 3px;
	margin: 10px;
	color: white;
	text-align: center;
	background-color: #27A9E3;
	border-radius: 5px;
}

.onclicked {
	background: red;
	color: black;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {

		var bj_id = ${bj_id};
		
		$.ajax({
			type : "post",
			async : false,
			data : {
				"type" : "showInSubject",
				"id" : bj_id
			},
			url : "showBanji",
			datatype : "json",
			success : function(data) {
				data = JSON.parse(data);
				var lis = "";
				for (var i = 0; i < data.length; i++) {
					var id = data[i].id;
					var name = data[i].name;
					lis += "<li value="+id+">" + name + "</li>";
				}
				$("#inSubs").html(lis);
			}
		});
		
		$.ajax({
			type : "post",
			data : {
				"type" : "showOutSubject",
				"id" : bj_id
			},
			async : false,
			url : "showBanji",
			datatype : "json",
			success : function(data) {
				data = JSON.parse(data);
				var lis = "";
				for (var i = 0; i < data.length; i++) {
					var id = data[i].id;
					var name = data[i].name;
					lis += "<li value="+id+">" + name + "</li>";
				}
				$("#outSubs").html(lis);
			}
		});
	
		$("#addSubject").click(function() {
			var array = new Array();
			$("#outSubs li").each(function(index, element) {
				if ($(this).attr("class") == "onclicked") {
					array.push($(this).val());

				}
			});
			if (array.length == 0) {
				$("#message").html("请选中一条数据");
				setTimeout(function() {
					$("#message").html("");
				}, 1000);
			} else {
				array = array.toString();
				alert(typeof array);
				$.ajax({
					type : "post",
					async : false,
					data : {
						"type" : "addInSubject",
						"selectId" : array,
						"bj_id" : bj_id
					},
					url : "showBanji",
					datatype : "json",
					success : function(data) {

						var insub = data.split("^")[0];

						insub = JSON.parse(insub);
						alert(insub);
						var lis = "";
						for (var i = 0; i < insub.length; i++) {
							var id = insub[i].id;
							var name = insub[i].name;
							lis += "<li value="+id+">" + name + "</li>";
						}
						$("#inSubs").html(lis);
						var oninsub = data.split("^")[1];
						oninsub = JSON.parse(oninsub);
						var lis = "";
						for (var i = 0; i < oninsub.length; i++) {
							var id = oninsub[i].id;
							var name = oninsub[i].name;
							lis += "<li value="+id+">" + name + "</li>";
						}
						$("#outSubs").html(lis);

					}
				});

			}

		});
		
		$("#deleteSubject").click(function() {

			var array = new Array();
			$("#inSubs li").each(function(index, element) {
				if ($(this).attr("class") == "onclicked") {
					array.push($(this).val());

				}
			});
			if (array.length == 0) {
				$("#message").html("请选中一条数据");
				setTimeout(function() {
					$("#message").html("");
				}, 1000);
			} else {
				array = array.toString();
				alert(array);
				$.ajax({
					type : "post",
					async : false,
					data : {
						"type" : "outInSubject",
						"selectId" : array,
						"bj_id" : bj_id
					},
					url : "showBanji",
					datatype : "json",
					success : function(data) {

						var insub = data.split("^")[0];

						insub = JSON.parse(insub);
						alert(insub);
						var lis = "";
						for (var i = 0; i < insub.length; i++) {
							var id = insub[i].id;
							var name = insub[i].name;
							lis += "<li value="+id+">" + name + "</li>";
						}
						$("#inSubs").html(lis);
						var oninsub = data.split("^")[1];
						oninsub = JSON.parse(oninsub);
						var lis = "";
						for (var i = 0; i < oninsub.length; i++) {
							var id = oninsub[i].id;
							var name = oninsub[i].name;
							lis += "<li value="+id+">" + name + "</li>";
						}
						$("#outSubs").html(lis);

					}
				});

			}

		});
	//	$("li").each(function() {
	//		$(this).click(function() {
	//			$(this).toggleClass("onclicked");
	//		});
	//	});
		$('ul').on('click', 'li', function() {
   $(this).toggleClass("onclicked");
});
	});
</script>
</head>

<body>

	<div id="subject-list">
		以选择课程
		<ul id="inSubs">
		</ul>
	</div>

	<br />
	<br />
	<div id="button-subject">
		<input type="button" name="addSubject" id="addSubject" value="新增课程" />
		<input type="button" name="deleteSubject" id="deleteSubject"
			value="删除课程" />

	</div>
	<div id="subject-list">
		未选择课程
		<ul id="outSubs">
		</ul>
	</div>

</body>
</html>
