<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="/css/styles.css"/>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Customer Add/Lookup</title>
	</head>
	<script type="text/javascript" src="/scripts/scripts.js"></script>
	<body onload="includeHtml(); showNewCustomerForm()">
		<div w3-include-html="header.html"></div>
		<div w3-include-html="sidebar.html"></div>
<!-- 		<div class="editableContent"> -->
		<table class="editableContent">
			<tr>
				<td>
					<input type="radio" id="newCustomer" onclick="showNewCustomerForm()" name="customerLookup" checked="true" />
						<label for="newCustomer">New Customer</label>
				</td>
				<td>
					<input type="radio" id="existingCustomer" onclick="showReturningCustomerForm()" name="customerLookup" />
						<label for="existingCustomer">Returning Customer</label>
				</td>
			</tr>
		</table>
<!-- 		</div> -->
		<form id="addCustomer" id="newCustomerForm" action="/addCustomer" method="POST" >
			<table id="customerTable" class="editableContent">
				<tr>
					<td><label for="firstName_newCustomer" class="customerLabel">First Name:</label></td>
					<td><input type="text" id="firstName_newCustomer" name="firstName_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="lastName_newCustomer" class="customerLabel">Last Name:</label></td>
					<td><input type="text" id="lastName_newCustomer" name="lastName_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="address1_newCustomer" class="customerLabel">Address 1:</label></td>
					<td><input type="text" id="address1_newCustomer" name="address1_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="address2_newCustomer" class="customerLabel">Address 2:</label></td>
					<td><input type="text" id="address2_newCustomer" name="address2_newCustomer" /></td>
				</tr>
				<tr>
					<td>
						<label for="city_newCustomer" class="customerLabel">City:</label></td><td>
						<input type="text" id="city_newCustomer" name="city_newCustomer" />
					</td>
				</tr>
				<tr>
					<td><label for="state_newCustomer" class="customerLabel">State:</label></td>
					<td><label id="state_newCustomer" class="customerLabel">${IOWA.description}</label></td>
<!-- 					<td><input type="select" id="state_newCustomer" name="state_newCustomer" /></td> -->
				</tr>
				<tr>
					<td><label for="zip_newCustomer" class="customerLabel">Zip Code:</label></td>
					<td><input type="text" id="zip_newCustomer" name="zip_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="phone_newCustomer" class="customerLabel">Phone:</label></td>
					<td><input type="text" id="phone_newCustomer" name="phone_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="email_newCustomer" class="customerLabel">E-mail:</label></td>
					<td><input type="text" id="email_newCustomer" name="email_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="password_newCustomer" class="customerLabel">Password:</label></td>
					<td><input type="text" id="password_newCustomer" name="password_newCustomer" /></td>
				</tr>
				<tr>
					<td><label for="confirmPassword_newCustomer" class="customerLabel">Confirm Password:</label></td>
					<td><input type="text" id="confirmPassword_newCustomer" name="confirmPassword_newCustomer" /></td>
				</tr>
				<tr>
					<td><input type="submit" class="cartButton" id="submit_newCustomer" /></td>
				</tr>
			</table>
		</form>
		<form id="returningCustomer" id="returningCustomerForm" action="/lookupCustomer" method="POST">
			<table id="returningCustomerTable" class="editableContent">
				<tr>
					<td><label for="email_returningCustomer" class="customerLabel">E-Mail:</label></td>
					<td><input type="text" id="phone_returningCustomer" /></td>
				</tr>
				<tr>
					<td><label for="password_returningCustomer" class="customerLabel">Password:</label></td>
					<td><input type="password" id="password_returningCustomer"></td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="submit_returningCustomer" class="cartButton" value="Login">
					</td>
				</tr>
			</table>
		</form>
		<div w3-include-html="footer.html"></div>
	</body>

	<script type="text/javascript"> //will move this all to its own file later
		function showNewCustomerForm() {
			document.getElementById("addCustomer").style.display = "initial";
// 			document.getElementById("showCustomer").style.display = "none";
			document.getElementById("returningCustomer").style.display = "none";
		}

		function showExistingCustomerForm() {
			document.getElementById("addCustomer").style.display = "none";
// 			document.getElementById("showCustomer").style.display = "initial";
			document.getElementById("returningCustomer").style.display = "none";
		}

		function showReturningCustomerForm() {
			document.getElementById("addCustomer").style.display = "none";
// 			document.getElementById("showCustomer").style.display = "none";
			document.getElementById("returningCustomer").style.display = "initial";
		}
	</script>
</html>