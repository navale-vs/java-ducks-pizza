/**
 * For cart 
 */
 
function handleDelete(form) {
	removeItems();
	location.href = '/removeItem';
	form.action = '/removeItem';
}

function removeItems() {
//	var itemsToRemove = getCheckedElements();
	var elemArray = document.getElementsByName("checkToRemove");
	var checkedArray = [];

	for(i = 0; i < elemArray.length; i++) {
		if(elemArray[i].checked == true) {
			checkedArray.push(elemArray[i].id);
		}
	}

	document.getElementById("itemsToDelete").value = checkedArray;
}

function enableContinue(total) {
	console.log("total: " + total);
	console.log("total <= 0: " + (total <= 0));
	document.getElementById("continue").disabled = (total <= 0);
}

function loginCustomer(form) {
	form.action = '/loginCustomer';
}