<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>框架页面</title>
<link rel="stylesheet" href="/page/css/mainStyle.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="/page/css/tableStyle.css" type="text/css"
	media="screen" />
<script type="text/javascript" src="/page/js/jquery-1.8.0.min.js"></script>
</head>
<frameset rows="260,*,50" frameborder="NO" border="0" framespacing="0">
	<noframes> 
	<body> 
	很抱歉，馈下使用的浏览器不支援框架功能，请转用新的浏览器。 
	</body> 
	</noframes> 
	<frame name="top" src="../topMain.jsp" scrolling="NO" noresize> 
	<frameset cols="320,*" frameborder="NO" border="0" framespacing="0">
		<frame name="left" src="leftMain.jsp" scrolling="NO" noresize>
		<frame name="right" src="rightMain.jsp" >
	</frameset>
	<frame name="bottom" src="../../below.jsp"> 
</frameset>
</html>