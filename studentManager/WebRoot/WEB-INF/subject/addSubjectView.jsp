<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addSubjectView.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="css/addView.css" type="text/css" rel="stylesheet"> 
<script src="js/jquery.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$(":submit").click(function() {
					var name = $('#name').val();
					if(name == "") {
						alert("课程姓名不能为空");
						return false;
					}
				});
			})
		</script>

  </head>
  
  <body>
 <div class="login">
    <div class="message">添加课程</div>
    <div id="darkbannerwrap"></div>
    
    <form method="p;ost" action="showSubject">

	<input type="text" name="name" id="name" value="" placeholder="请输入课程姓名称" /><br /> 	
				 <input type="hidden" name="type" id="type" value="addSubject" /><br /> 
				<input type="submit" value="确定" style="width:100%;"  />
		<hr class="hr20">
	</form>
	
</div>
  </body>
</html>
