<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>   

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href="/css/styles.css"/>
		<title>Thank you for your order!</title>
	</head>
	<script type="text/javascript" src="/scripts/scripts.js"></script>
	<body onload="includeHtml();">
		<div w3-include-html="header.html"></div>
		<div w3-include-html="sidebar.html"></div>
		<table id="orderReview" class="staticContent" >
			<tr>
				<th colspan="2">Your order (${orderId}) has been submitted!</th>
			</tr>
			<tr>
				<th colspan="2">Order Review</th>
			</tr>
			<tr>
				<th>Description</th>
				<th>Price</th>
			</tr>
			<core:forEach var="item" items="${cartForDisplay}">
				<tr>
					<td>${item.value}</td>
					<td class="price">${item.key.price}</td>
				</tr>
			</core:forEach>
			<tr>
				<td>Total:</td>
				<td>${total}</td>
			</tr>
		</table>
		<div w3-include-html="footer.html"></div>
	</body>
</html>