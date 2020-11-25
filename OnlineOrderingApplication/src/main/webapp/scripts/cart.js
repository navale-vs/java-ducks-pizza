/**
 * For cart 
 */
 
function handleDelete() {
	console.log("in handleDelete");
	removeItems();
	console.log("document.getElementById(\"itemsToDelete\").value: " + document.getElementById("itemsToDelete").value);
	location.href = '/removeItem';
}

function removeItems() {
	console.log("in removeItems");
//	var itemsToRemove = getCheckedElements();
	var elemArray = document.getElementsByName("checkToRemove");
	var checkedArray = [];

	for(i = 0; i < elemArray.length; i++) {
		if(elemArray[i].checked == true) {
			var pizza = {id: elemArray[i].id};
			console.log(JSON.stringify(pizza));
			checkedArray.push(JSON.stringify(pizza));
		}
	}

	console.log("checkedArray: " + checkedArray);
	document.getElementById("itemsToDelete").value = "[" + checkedArray + "]";
}

function enableContinue(total) {
	console.log("total: " + total);
	console.log("total <= 0: " + (total <= 0));
	document.getElementById("continue").disabled = (total <= 0);
}

function changeAction(form, newAction) {
	console.log(changeAction);
	form.action = newAction;
}