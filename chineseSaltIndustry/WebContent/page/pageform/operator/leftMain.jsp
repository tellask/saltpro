<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>左侧菜单页面</title>
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;text-decoration:none;}
body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}
</style>
<link rel="stylesheet" type="text/css" href="page/css/sdmenu.css" />
<script type="text/javascript" src="page/js/sdmenu.js"></script>
<script type="text/javascript">
var myMenu;
window.onload = function() {
	myMenu = new SDMenu("my_menu");
	myMenu.init();
};
</script>
</head>
<body>
	<div id="my_menu" class="sdmenu">
	<div class="collapsed">
		<span>在线工具</span>
		<a href="http://sc.chinaz.com/">图像优化</a>
		<a href="http://sc.chinaz.com/">收藏夹图标生成器</a>
		<a href="http://sc.chinaz.com/">邮件出谜语的人</a>
		<a href="http://sc.chinaz.com/">htaccess密码</a>
		<a href="http://sc.chinaz.com/">梯度图像</a>
		<a href="http://sc.chinaz.com/">按钮生成器</a>
	</div>
	<div class="collapsed">
		<span>支持我们</span>
		<a href="http://sc.chinaz.com/">推荐我们</a>
		<a href="http://sc.chinaz.com/">链接我们</a>
		<a href="http://sc.chinaz.com/">网络资源</a>
	</div>
	<div class="collapsed">
		<span>合作伙伴</span>
		<a href="http://sc.chinaz.com/">JavaScript工具包</a>
		<a href="http://sc.chinaz.com/">CSS驱动</a>
		<a href="http://sc.chinaz.com/">CodingForums</a>
		<a href="http://sc.chinaz.com/">CSS例子</a>
	</div>
	<div >
		<span>测试电流</span>
		<a href="http://sc.chinaz.com/">Current or not</a>
		<a href="http://sc.chinaz.com/">Current or not</a>
		<a href="http://sc.chinaz.com/">Current or not</a>
		<a href="http://sc.chinaz.com/">Current or not</a>
	</div>
</div>
</body>
</html>