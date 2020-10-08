<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>   


<core:forEach var="topping" items="${toppings}">
	<tr>
		<td><input type="checkbox" id="${topping.description}" name="${topping.description}" value="${topping.id }" />${topping.description}</td>
		<td><input type="checkbox" />Left</td><td><input type="checkbox" />Right</td>
	</tr>
</core:forEach>