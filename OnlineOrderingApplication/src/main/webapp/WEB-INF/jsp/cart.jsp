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
		<form method="POST" action="/addorder">
			<input type="hidden" id="itemsToDelete" name="itemsToDelete" value="" />
			<table id="cart">
				<tr>
					<td class="garbage">
						<img class="removeButton" height="25" width="25" src="/img/closedGarbage.png" onmousedown="src='/img/openGarbage.png'"
							onmouseup="src='/img/closedGarbage.png'" onblur="src='/img/closedGarbage.png'" onclick="handleDelete()"
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
							onmouseup="src='/img/closedGarbage.png'" onclick="handleDelete()" alt="Remove" title="Remove Item"
							onclick="location.href = '/menu'"/>
					</td>
					<td>
						<input type="button" class="cartButton" onclick="location.href = '/menu'" value="Back to Menu">
						<input type="Submit" class="cartButton" onclick="removeItems();" value="Place Order">
						<input type="button" class="cartButton" onclick="location.href = '/cancel'" value="Cancel">
					</td>
					<td class="price">${total}</td>
				</tr>
			</table>
		</form>
	</body>

	<script type="text/javascript"> //will move to separate file later, then change to use getCheckedElements() in menu.jsp
		function handleDelete() {
			console.log("in handleDelete");
			removeItems();
			console.log("document.getElementById(\"itemsToDelete\").value: " + document.getElementById("itemsToDelete").value);
			location.href = '/removeItem';
		}

		function removeItems() {
			console.log("in removeItems");
// 			var itemsToRemove = getCheckedElements();
			var elemArray = document.getElementsByName("checkToRemove");
			var checkedArray = [];

			for(i = 0; i < elemArray.length; i++) {
				if(elemArray[i].checked == true) {
					checkedArray.push(elemArray[i].id);
				}
			}

			console.log("checkedArray: " + checkedArray);
			document.getElementById("itemsToDelete").value = checkedArray;
		}
	</script>
</html>