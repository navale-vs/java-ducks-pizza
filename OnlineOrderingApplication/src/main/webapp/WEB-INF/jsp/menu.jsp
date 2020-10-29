<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<head>
	<link rel="stylesheet" href="/css/styles.css"/>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Get ready for delicious!</title>
</head>
<body onload="setViewCartLabel(${itemCount});">
	<form method="POST" action="/additem">
		<input type="hidden" id="toppingsSelected" name="toppingsSelected" value="" />
		<table id="options">
		<tr>
			<th>Sizes: </th>
			<core:forEach var="size" items="${sizes}">
				<td>
					<input type="radio" id="${size.description}" name="size" value="${size.id}"
						onclick="disableReset(false);enableAddToCart();" />
						<label for="${size.description}">${size.description}</label>
				</td>
			</core:forEach>
		</tr>
		<tr>
			<th>Crusts</th>
			<core:forEach var="crust" items="${crusts}">
				<td>
					<input type="radio" id="${crust.description}" name="crust" value="${crust.id}"
						onclick="disableReset(false);enableAddToCart();" />
						<label for="${crust.description}">${crust.description}</label>
				</td>
			</core:forEach>
		</tr>
		<tr>
			<th>Sauces: </th>
			<core:forEach var="sauce" items="${sauces}">
				<td>
					<input type="radio" id="${sauce.description}" name="sauce" value="${sauce.id}"
						onclick="disableReset(false);enableAddToCart();" />
						<label for="${sauce.description}">${sauce.description}</label>
				</td>
			</core:forEach>
		</tr>
		<tr>
			<th>Cheeses:</th>
			<core:forEach var="cheese" items="${cheeses}">
				<td>
					<input type="radio" id="${cheese.description}" name="cheese" value="${cheese.id}"
						onclick="disableReset(false);enableAddToCart();" />
						<label for="${cheese.description}">${cheese.description}</label>
				</td>
			</core:forEach>
		</tr>
		<tr>
			<th>Toppings:</th>
			<core:forEach var="topping" items="${toppings}">
				<tr>
					<td><input class="toppings" type="checkbox" id="${topping.description}" name="${topping.id}"
						value="${topping.id}" onclick="handleToppingCheck(this.id); disableReset(false);"/>
						<label for="${topping.description}">${topping.description}</label></td>
					<td><input class="leftSide" type="checkbox" id="${topping.description}Left" name="${topping.description}"
						onclick="handleSideClick(this.name);" />
						<label for="${topping.description}Left">Left</label></td>
					<td><input class="rightSide" type="checkbox" id="${topping.description}Right" name="${topping.description}"
						onclick="handleSideClick(this.name);" />
						<label for="${topping.description}Right">Right</label></td>
				</tr>
			</core:forEach>		
		</tr>
		<tr>
			<td><input id="addToCart" class="menuButton" type="submit" value="Add to Cart" onclick="buildPizza();" disabled></td>
			<td><input id="reset" class="menuButton" type="button" value="Reset" onclick="resetFields();" disabled></td>
			<td><input id="viewCart" class="menuButton" type="button" value="View Cart" onclick="location.href = '/cart'" /></td>
		</tr>
		</table>
	</form>
</body>

<script type="text/javascript"> //will move this all to its own file later
// 	function allowGlutenFree(id) {
// 		if((id == 'SIZE_0001') || (id == 'SIZE_0002')) {
// 			document.getElementById('CRST_0004').disabled = false;
// 		} else {
// 			document.getElementById('CRST_0004').checked = false;
// 			document.getElementById('CRST_0004').disabled = true;
// 		}
// 	}

	function setViewCartLabel(itemCount) {
		console.log("itemCount: " + itemCount);
		document.getElementById("viewCart").value = ((itemCount) ? "View Cart (" + itemCount + ")" : "View Cart");
	} 

	function resetFields() {
		resetRadioButtonsByName("size");
		resetRadioButtonsByName("crust");
		resetRadioButtonsByName("sauce");
		resetRadioButtonsByName("cheese");
		resetToppings();
		disableReset("disable");
		enableAddToCart();
	}

	function getCheckedElements(name) {
		var elemArray = document.getElementsByName(name);
		var checkedArray = [];

		for(i = 0; i < elemArray.length; i++) {
			if(elemArray[i].checked == true) {
				checkedArray.push(elemArray[i]);
			}
		}

		return checkedArray;
	}

	function getSelectedToppings() {
		var toppingsArray = document.getElementsByClassName("toppings");
		var selectedToppings = [];

		for(let i = 0; i < toppingsArray.length; i++) {
			if(toppingsArray[i].checked) {
				let sides = document.getElementsByName(toppingsArray[i].id);
				var topping = {toppingsId: toppingsArray[i].value, left: sides[0].checked, right: sides[1].checked};
				console.log(JSON.stringify(topping));
				console.log(topping.toppingsId + " " + topping.left + " " + topping.right);
				selectedToppings.push(JSON.stringify(topping));
			}
		}

		return selectedToppings;
	}

	function getSelectedToppingsForDisplay() {
		var toppingsArray = document.getElementsByClassName("toppings");
		var selectedToppings = "";

		for(let i = 0; i < toppingsArray.length; i++) {
			if(toppingsArray[i].checked) {
				let sides = document.getElementsByName(toppingsArray[i].id);
				selectedToppings += toppingsArray[i].id;

				if(sides[0].checked && !sides[1].checked) {
					selectedToppings += ": Left Side Only";
				} else if(!sides[0].checked && sides[1].checked) {
					selectedToppings += ": Right Side Only";
				}

				selectedToppings += "\n";
			}
		}

		return selectedToppings;
	}

	function resetRadioButtonsByName(name) {
		var elementArray = document.getElementsByName(name);

		for(let i = 0; i < elementArray.length; i++) {
			elementArray[i].checked = false;
		}
	}

	function resetToppings() {
		var elementArray = document.getElementsByClassName("toppings");

		for(let i = 0; i < elementArray.length; i++) {
			if(elementArray[i].checked) {
				elementArray[i].click();
			}
		}
	}

	function enableAddToCart() {
		document.getElementById("addToCart").disabled = !validatePizza();
	}

	function disableReset(disable) {
		document.getElementById("reset").disabled = disable;
	}

	function validatePizza() {
		var sizeId = getCheckedElements("size")[0];
		var crustId = getCheckedElements("crust")[0];
		var sauceId = getCheckedElements("sauce")[0];
		var cheeseId = getCheckedElements("cheese")[0];

		if((sizeId == undefined) || (crustId == undefined) || (sauceId == undefined) || (cheeseId == undefined)) {
			return false;
		} else {
			return true;
		}
	}

	function buildPizza() {
		var size = getCheckedElements("size")[0];
		var crust = getCheckedElements("crust")[0];
		var sauce = getCheckedElements("sauce")[0];
		var cheese = getCheckedElements("cheese")[0];
		document.getElementById("toppingsSelected").value = "[" + getSelectedToppings() + "]";

		console.log("toppingsSelected: " + document.getElementById("toppingsSelected"));

		var itemReview = "Added a " + size.id + " pizza on " + crust.id + " crust with " + sauce.id + " sauce, " +
			cheese.id + " cheese\n" + getSelectedToppingsForDisplay();

		alert(itemReview);
	}

	function handleToppingCheck(id) {
		var isChked = document.getElementById(id).checked;
		var toppingLeft = document.getElementsByName(id)[0];
		var toppingRight = document.getElementsByName(id)[1];

		toppingLeft.checked = isChked;
		toppingRight.checked = isChked;
	}

	function handleSideClick(name) {
		var sides = document.getElementsByName(name);

		if((sides.length == 2) && (sides[0].checked == false) && (sides[1].checked == false)) {
			document.getElementById(name).checked = false;
		} else {
			document.getElementById(name).checked = true;
		}
	}
</script>
</html>