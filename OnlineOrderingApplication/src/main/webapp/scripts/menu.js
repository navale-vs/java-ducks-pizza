/**
 * For menu 
 */

function setViewCartLabel(itemCount) {
	console.log("itemCount: " + itemCount);
	document.getElementById("viewCart").value = ((itemCount) ? "View Cart (" + itemCount + ")" : "View Cart");
}