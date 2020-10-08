<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href="/css/styles.css"/>
		<title>Cart</title>
	</head>
	<body>
		<form method="POST" action="/checkout">
			<table id="cart">
				<tr>
					<th></th>
					<th>Description</th>
					<th>Price</th>
				</tr>
					<core:forEach var="item" items="${shoppingCart}">
						<tr>
							<td><input type="button" value="Remove"></td>
	<%-- 						<td>${item.id}</td> --%>
	<%-- 						<td>${item.price}</td> --%>
						</tr>
					</core:forEach>
			<tr>
				<td><input type="button" onclick="location.href = '/menu'" value="Back to Menu"></td>
				<td><input type="Submit" value="Place Order"></td>
				<td><input type="button" onclick="location.href = '/'" value="Cancel"></td>
			</tr>
			</table>
		</form>
	</body>
</html>