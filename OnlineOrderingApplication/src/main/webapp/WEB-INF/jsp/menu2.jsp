<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="/css/styles.css"/>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Get ready for delicious!</title>
	</head>
	<body>
		<form:form method="POST" action="/addItem">
			<table id="editableContent">
			</table>
		</form:form>
	
	</body>
</html>