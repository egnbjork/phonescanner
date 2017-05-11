<!DOCTYPE HTML>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>Phone Scanner</title>
<meta name="author" content="Yevgen Berberyan" />
<meta name="description" content="Scans and reformats phone numbers" />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="/resources/css/bootstrap.css" rel="stylesheet" />
<link href="/resources/css/main.css" rel="stylesheet" />
</head>
<body>
	<h2 id="main_header">Phone Scanner</h2>
	<hr>
	<div class="container">
		<div>
			<c:choose>
				<c:when test="${error != null}">
					<div style="color: red" align="center">
						<b>${error}</b>
					</div>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${phones == null}">
					<div class="col-xs-6">
						Check phone numbers from JUnit tests <br> <br> <br>
						<form action="/default">
							<input type="submit" value="JUnit">
						</form>
					</div>
					<div class="col-xs-6">
						<form method="post" enctype="multipart/form-data">
							Upload file to look phones in: <br> <input type="file"
								name="phonefile"> <br> <input type="submit"
								name="Upload">
						</form>
					</div>
					<hr>
				</c:when>
				<c:otherwise>
					<div class="text-center">
						<form action="/">
							<input type="submit" value="Try Again">
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<hr>
		<div>
			<table class="table table-hover table-condensed"
				style="width: 400px; margin: 0 auto;">
				<c:forEach var="phone" items="${phones}">
					<tr>
						<td style="text-align: center;"><c:out value="${phone}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>