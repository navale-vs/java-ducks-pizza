<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>   
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<link rel="stylesheet" href="/css/styles.css"/>
		<title>Check order status</title>
	</head>
	<script type="text/javascript" src="/scripts/scripts.js"></script>
	<body onload="includeHtml();">
		<div w3-include-html="header.html"></div>
		<div w3-include-html="sidebar.html"></div>
		<div>
		<table>
			<tr>
				<td>
					<form method="GET" action="/lookupOrder">
						<table class="editableContent">
							<tr>
								<td>
									<label for="byOrderNumber" class="customerLabel">By Order Number:</label>
									<input type="text" name="byOrderNumber" onchange="enableSearchButton(this)"/>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<td> -->
<!-- 									<label for="byEmail" class="customerLabel">By Email:</label> -->
<!-- 									<input type="text" name="byEmail" /> -->
<!-- 								</td> -->
<!-- 							</tr> -->
							<tr>
								<td>
									<input id="searchButton" class="menuButton" type="submit" value="Search" disabled/>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<core:if test="${noOrder != null}">
						<div class="staticContent">${noOrder}</div>						
					</core:if>
					<core:if test="${cartForDisplay != null}">
						<table id="orderStatus" class="staticContent" >
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
								<td class="price">${total}</td>
							</tr>
							<tr>
								<td>Order Status:</td>
								<td>${orderStatus}</td>
							</tr>
						</table>
					</core:if>
				</td>
			</tr>
		</table>
		</div>
		<div w3-include-html="footer.html"></div>
	</body>
	<script type="text/javascript">
		function enableSearchButton(element) {
			document.getElementById("searchButton").disabled = ((element.value == "") ? true : false);
		}
	</script>
</html>