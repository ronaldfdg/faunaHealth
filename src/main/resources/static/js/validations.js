/**
 * @author Ronald Dolores - 01/05/2020
 */

function validateText(parameter) {
	var patron = /^[a-zA-Z\s]*$/;
	if(parameter.search(patron))
		return false
	else
		return true
}

function checkNumbers(event) {
	event = (event) ? event : window.event
	var charCode = (event.which) ? event.which : event.keyCode
	if(charCode > 31 & (charCode < 48 || charCode > 57)) {
		alert('Solo se aceptan valores numericos en este campo')
		return false
	} else 
		return true
}

/* Validate clients form */
function validateFormClient(form) {
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
	
	alert('Guardando datos...')
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