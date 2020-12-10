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
	<script type="text/javascript" src="/scripts/cart.js"></script>
	<script type="text/javascript" src="/scripts/scripts.js"></script>
	<body onload="includeHtml(); enableContinue(${total});">
		<div w3-include-html="header.html"></div>
		<div w3-include-html="/sidebar"></div>
		<form method="POST" action="/loginCustomer" id="cartForm">
			<input type="hidden" id="itemsToDelete" name="itemsToDelete" value="" />
			<table class="editableContent">
				<tr>
					<td class="garbage">
						<img class="removeButton" height="25" width="25" src="/img/closedGarbage.png" onmousedown="src='/img/openGarbage.png'"
							onmouseup="src='/img/closedGarbage.png'" onclick="handleDelete(cartForm); cartForm.submit();"
							alt="Remove" title="Remove Checked Items"/>
					</td>
					<th>Description</th>
					<th>Price</th>
				</tr>
				<core:forEach var="item" items="${cartForDisplay}">
					<tr>
						<td><input id="${item.key.id}" class="checkToRemove" name="checkToRemove" type="checkbox"/></td>
						<td>${item.value}</td>
						<td class="price">${item.key.price}</td>
					</tr>
				</core:forEach>
				<tr>
					<td class="garbage">
						<img class="removeButton" height="25" width="25" src="/img/closedGarbage.png" onmousedown="src='/img/openGarbage.png'"
							onmouseup="src='/img/closedGarbage.png'" onclick="handleDelete(cartForm); cartForm.submit();"
							alt="Remove" title="Remove Item"/>
					</td>
					<td>
						<input type="button" class="cartButton" onclick="location.href = '/menu'" value="Back to Menu" />
						<input type="Submit" class="cartButton" onclick="loginCustomer(cartForm); removeItems(); cartForm.submit();" value="Continue" id="continue" />
						<input type="button" class="cartButton" onclick="location.href = '/cancel'" value="Cancel" />
					</td>
					<td class="price">${total}</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td></td> -->
<!-- 					<td> -->
<%-- 					<core:forEach var="item" items="${retrievalMethod}"> --%>
<!-- 						<td> -->
<%-- 							<input type="radio" id="${item.description}" name="retrievalMethod" value="${item.id}"/> --%>
<%-- 							<label for="${item.description}">${item.description}</label> --%>
<!-- 						</td> -->
<%-- 					</core:forEach> --%>
<!-- 					</td> -->
<!-- 					<td></td> -->
<!-- 				</tr> -->
			</table>
		</form>
		<div w3-include-html="footer.html"></div>
	</body>

<!-- javascript moved to cart.js -->
</html>