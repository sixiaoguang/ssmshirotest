<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<center>

		<h4>登陆账号：<font color="blue"><shiro:principal/></font></h4>
		
		<hr/>
		JSP标签控制
		<shiro:hasRole name="系统管理员">
			<h4>
				<a href="admin.jsp" target="right">admin Page</a>
			</h4>
		</shiro:hasRole>

		<shiro:hasPermission name="user:add">
			<h4>
				<a href="add.jsp" target="right">添加用户</a>
			</h4>
		</shiro:hasPermission>


		<shiro:hasPermission name="user:list">
			<h4>
				<a href="userList" target="right">查询用户</a><br />
			</h4>
		</shiro:hasPermission>
	 
		<hr/>
		方法注解控制
			<h4>
				<a href="callAnnotation" target="right">新增用户页面_Annotation</a><br />
			</h4>
		<hr/>
		方法判断控制
			<h4>
				<a href="callMethod" target="right">新增用户页面_Method</a><br />
			</h4>
		<hr/>
		URL控制
			<h4>
				<a href="callUrl" target="right">新增用户页面_Url</a><br />
			</h4>
		<hr/>
		<h4>
			<a href="logout" target="_top">退出登陆</a>
		</h4>
		<h4>
			<a href="mathError" target="_top">触发错误</a>
		</h4>

	</center>
</body>
</html>