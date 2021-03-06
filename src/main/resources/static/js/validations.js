/**
 * @author Ronald Dolores - 01/05/2020
 */

function validateText(parameter) {
	var patron = /^[a-zA-Z\s\u00E0-\u00FC]*$/;
	if(parameter.search(patron))
		return false
	else
		return true
}

function checkNumbers(event) {
	event = (event) ? event : window.event
	var charCode = (event.which) ? event.which : event.keyCode
	if(charCode > 31 & (charCode < 46 || charCode > 57)) {
		alert('Solo se aceptan valores numericos en este campo')
		return false
	} else 
		return true
}

function validateFormClient(form){
	var name = document.getElementById("name")
	if(!validateText(name.value)) {
		alert('No pueden ingresar valores numericos en el campo "Nombre"')
		document.getElementById("name").focus()
		return false
	}
	
	var primaryLastName = document.getElementById("primaryLastName")
	if(!validateText(primaryLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Paterno"')
		document.getElementById("primaryLastName").focus()
		return false
	}
	
	var secondLastName = document.getElementById("secondLastName")
	if(!validateText(secondLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Materno"')
		document.getElementById("secondLastName").focus()
		return false
	}
	
	return true
}

/* Validate clients and patients form */
function validateFormClientAndPatient(form) {
	var name = document.getElementById("name")
	if(!validateText(name.value)) {
		alert('No pueden ingresar valores numericos en el campo "Nombre"')
		document.getElementById("name").focus()
		return false
	}
	
	var primaryLastName = document.getElementById("primaryLastName")
	if(!validateText(primaryLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Paterno"')
		document.getElementById("primaryLastName").focus()
		return false
	}
	
	var secondLastName = document.getElementById("secondLastName")
	if(!validateText(secondLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Materno"')
		document.getElementById("secondLastName").focus()
		return false
	}
	
	var nickname = document.getElementById("nickname")
	if(!validateText(nickname.value)) {
		alert('No se aceptan valores numericos en el campo "Alias"')
		document.getElementById("nickname").focus()
		return false
	}
	
	var colorHair = document.getElementById("colorHair")
	if(!validateText(colorHair.value)) {
		alert('No se aceptan valores numericos en el campo "Color de pelo"')
		document.getElementById("colorHair").focus()
		return false
	}
	
	return true
	
}

function validateFormProvider(form) {
	var businessName = document.getElementById("businessName")
	if(!validateText(businessName.value)) {
		alert('No se pueden ingresar valores numericos en el campo "Razón Social"')
		document.getElementById("businessName").focus()
		return false
	}
	
	return true
}

function validateFormUser(form) {
	
	var name = document.getElementById("name")
	if(!validateText(name.value)) {
		alert('No pueden ingresar valores numericos en el campo "Nombre"')
		document.getElementById("name").focus()
		return false
	}
	
	var primaryLastName = document.getElementById("primaryLastName")
	if(!validateText(primaryLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Paterno"')
		document.getElementById("primaryLastName").focus()
		return false
	}
	
	var secondLastName = document.getElementById("secondLastName")
	if(!validateText(secondLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Materno"')
		document.getElementById("secondLastName").focus()
		return false
	}
	
	return true
}

function validateSearchBy(form) {
	
	var name = document.getElementById("name")
	if(!validateText(name.value)) {
		alert('No pueden ingresar valores numericos en el campo "Nombre"')
		document.getElementById("name").focus()
		return false
	}
	
	var primaryLastName = document.getElementById("primaryLastName")
	if(!validateText(primaryLastName.value)) {
		alert('No pueden ingresar valores numericos en el campo "Apellido Paterno"')
		document.getElementById("primaryLastName").focus()
		return false
	}
	
	return true;
}

function validateSearchByPatients(form) {
	var nickname = document.getElementById("nickname")
	if(!validateText(nickname.value)) {
		alert('No se pueden ingresar valores numericos en el campo "Alias"')
		document.getElementById("nickname").focus()
		return false
	}
	
	var primaryLastName = document.getElementById("primaryLastName")
	if(!validateText(primaryLastName.value)) {
		alert('No se pueden ingresar valores numericos en el campo "Apellido"')
		document.getElementById("primaryLastName").focus()
		return false
	}
	
	return true
}

function validateSearchByOperation(form) {
	var nickname = document.getElementById("patientNickname")
	if(!validateText(nickname.value)) {
		alert('No se pueden ingresar valores numericos en el campo "Paciente"')
		document.getElementById("patientNickname").focus()
		return false
	}
	
	var primaryLastName = document.getElementById("patientLastName")
	if(!validateText(primaryLastName.value)) {
		alert('No se pueden ingresar valores numericos en el campo "Apellido"')
		document.getElementById("patientLastName").focus()
		return false
	}
	
	return true
}

function addItem(){
	var table = document.getElementById("itemsSale");
	var item = document.getElementById("item");
	var clone = item.cloneNode(true);
	
		var cloneInputs = clone.getElementsByTagName("input");
		
		for(var i = 0; i<cloneInputs.length; i++){
			cloneInputs[i].value = '';
		}
		
		table.appendChild(clone);
}

function deleteRow(btn){
	var row = btn.parentNode.parentNode;
	row.parentNode.removeChild(row);
}




