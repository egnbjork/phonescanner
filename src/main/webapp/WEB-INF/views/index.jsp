<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Phone Scanner</title>
</head>
<body>
	<h2>Phone Scanner</h2>
	<form method="post" enctype="multipart/form-data">
		File to look phones in: <br> <br> <input type="file"
		name="phonefile"> <br> <br> <input type="submit"
		name="Upload">
	</form>
	<c:forEach var="phone" items="${phones}">
		<c:out value="${phone}"/>
	</c:forEach>
</body>
</html>