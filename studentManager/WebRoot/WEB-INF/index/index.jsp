<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Index2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style>
#all{
width:1200px;
margin:auto;
overflow:hidden;
}
 body{
 padding:0px;
 margin:0px;
    background:url(images/web_login_bg.jpg) no-repeat center;
    background-size: cover;
}

</style>
<script type="text/javascript">

</script>
  </head>
  
  <body>
  <div id="all">
   <iframe src="index?type=index2" width="1200px" height="85px"  frameborder="0"></iframe>
<iframe src="index?type=index3" width="195px"  height="600px"  frameborder="0"></iframe>
<iframe name="main" id="main" src="index?type=index1" width="800px" height="600px"  frameborder="0"></iframe>
<iframe src="index?type=index5" width="195px"  height="600px"  frameborder="0"></iframe>
<iframe src="index?type=index4" width="100%" height="200px" frameborder="0"></iframe>
</div>
  </body>
</html>
