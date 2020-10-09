<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>   

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="/css/styles.css"/>
	<title>Get ready for delicious!</title>
</head>
<body onload="onLoad()">
	<form action="/cart">
		<input type="submit" id="viewCart" value="View Cart"/>
	</form>

	<form method="POST" action="/addItem">
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
					<td><input class="toppings" type="checkbox" id="${topping.description}"
						value="${topping.id }" onclick="handleToppingCheck(this.id); disableReset(false);"/>
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
			<td><input id="addToCart" class="menuButton" type="submit"  value="Add to Cart" onclick="buildPizza();" disabled></td>
			<td><input id="reset" class="menuButton" type="button" value="Reset" onclick="resetFields();" disabled></td>
		</tr>
		</table>
	</form>
</body>

<script type="text/javascript"> //will move this all to its own file later
// 	var pizzas = [];
	
// 	function allowGlutenFree(id) {
// 		if((id == 'SIZE_0001') || (id == 'SIZE_0002')) {
// 			document.getElementById('CRST_0004').disabled = false;
// 		} else {
// 			document.getElementById('CRST_0004').checked = false;
// 			document.getElementById('CRST_0004').disabled = true;
// 		}
// 	}

	function onLoad() {
// 		viewCart.value = ((${shoppingCart.size()} > 0) ? "View Cart (" + ${shoppingCart.size()} + ")": "View Cart") 
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

	function getCheckedElementValues(name) {
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
				var topping = {id: toppingsArray[i].value, left: sides[0].checked, right: sides[1].checked};
				selectedToppings.push(topping);
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
		var sizeId = getCheckedElementValues("size")[0];
		var crustId = getCheckedElementValues("crust")[0];
		var sauceId = getCheckedElementValues("sauce")[0];
		var cheeseId = getCheckedElementValues("cheese")[0];
		var selectedToppings = getSelectedToppings();

		if((sizeId == undefined) || (crustId == undefined) || (sauceId == undefined) || (cheeseId == undefined)) {
			return false;
		} else {
			return true;
		}
	}
	
	function buildPizza() {
		if(!validatePizza()) {//shouldn't be necessary because Add to Cart should not be available until settings are correct 
			//specify problems
			return;
		}

		var size = getCheckedElementValues("size")[0];
		var crust = getCheckedElementValues("crust")[0];
		var sauce = getCheckedElementValues("sauce")[0];
		var cheese = getCheckedElementValues("cheese")[0];
		var selectedToppings = getSelectedToppings();

// 		var pizza = {order: 0, size: sizeId, crust: crustId, sauce: sauceId, cheese: cheeseId, toppings: selectedToppings, price: 0.00};
// 		${pizzas}.add(pizza);
// 		pizzas.push(pizza);
// 		viewCart.value = "View Cart (" + ${pizzas}.size + ")";
		var itemReview = "Added a " + size.id + " pizza " + " on " + crust.id + " crust with " + sauce.id + " sauce, " + cheese.id + " cheese.";

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

	function addPizza() {
		alert("Confirm order"); //should show message with settings for the pizza user is adding
	}
</script>
</html>