<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="w" uri="http://javacrazyer.iteye.com/tags/tagtool" %>
<html>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://"
   + request.getServerName() + ":" + request.getServerPort()
   + path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>设置单位类型</title>
<link rel="stylesheet" type="text/css" href="<%=path %>/page/css/mainStyle.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/page/css/tableStyle.css">
</head>
<body>
	<form action="login.do?method=regtion" method="post" name="myform">
		<table >
			<tr>
				<th colspan="2"><b>设置单位类型</b></th>
			</tr>
			<tr>
				<th>类型名称：</th>
				<td><input type="text" name="comTypeName" id="comTypeName"/></td>
				<th>创建日期</th>
				<td><input type="text" name="starttime" id="starttime"  onkeyDown="inputdatetime('starttime')"  
				value="0000-00-00 00:00:00" size="20" maxlength="19"/>
				<input type="button" class="dtstyle" id="datebtn" name="datebtn" onclick="">
				</td>
			</tr>
			<tr>
				<th>昵称：</th>
				<td><input type="text" name="userName" id="userName"/></td>
			</tr>
			<tr>
			<th>密码：</th>
				<td><input type="password" name="pwd" id="pwd"/></td>
			</tr>
			<tr>
				<th>确认密码：</th>
				<td><input type="password" name="pwdtoo" id="pwdtoo"/></td>
			</tr>
			<tr>	
				<td ><input type="submit" value="注册"  class="btn" /></td>
				<td ><input type="reset" value="重置"  class="btn"/></td>
			</tr>
			<tr>
				<td colspan="2">
						<c:out value="${msgVal }"></c:out>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>