<!DOCTYPE html>
<link rel="stylesheet" href="/css/styles.css"/>
<script type="text/javascript" src="/scripts/menu.js"></script>
	<table class="sidebar" onload="setViewCartLabel(${itemCount});" on>
		<tr>
			<td>
				<a href="/">Home</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="/menu">Start Order</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="/orderstatus">Check Order</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="/history">Our History</a>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" id="viewCart" class="menuButton" value="View Cart" onclick="location.href='/cart'" />
			</td>
		</tr>
	</table>