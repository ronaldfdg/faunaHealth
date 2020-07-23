 /*
 * @author - Ronald Dolores
 * @date - 14/07/2020
 */
$(document).ready(function(){
	
	var upper = new RegExp("^(?=.*[A-Z])");
	var special = new RegExp("^(?=.*[!@#$%&*.])");
	var numbers = new RegExp("^(?=.*[0-9])");
	var lower = new RegExp("^(?=.*[a-z])");
	var length = new RegExp("^(?=.{6,})");
	
	var safePassword = false;
	var equalPassword = false;
	
	var regExp = [upper, special, numbers, lower, length];
	var elementos = [$("#upper"),$("#special"),$("#numbers"),$("#lower"),$("#length")];
	
	$("#password").keyup(function(){
		var password = $("#password").val();
		var check = 0;
		
		for(var i = 0; i < regExp.length; i++){
			if(regExp[i].test(password)){
				elementos[i].hide();
				check++;
			} else {
				elementos[i].show();
			}
		}
		
		if(check >= 0 && check <= 4){
			$("#password").removeClass('is-valid').addClass('is-invalid');
			$("#security").text("Contraseña insegura").removeClass('valid-feedback').addClass('invalid-feedback')
			safePassword = false;
		} else if(check == 5){
			$("#password").removeClass('is-invalid').addClass('is-valid');
			$("#security").text("Contraseña segura").removeClass('invalid-feedback').addClass('valid-feedback')
			safePassword = true;
			if(equalPassword){
				$("#confirmpassword").val('');
				$("#confirmpassword").removeClass("is-valid");
				$("#confirmpassword").addClass("is-invalid");
				$('#validation').text("Contraseñas distintas!")
				$('#validation').removeClass("valid-feedback")
				$('#validation').addClass("invalid-feedback")
				equalPassword = false;
			}
		}
		
		if(password == ""){
			$("#confirmpassword").val('');
			$("#validation").text('');
			if($("#validation").hasClass("valid-feedback")){
				$("#validation").removeClass("valid-feedback");
			}
			
			if($("#confirmpassword").hasClass("is-valid")){
				$("#confirmpassword").removeClass("is-valid");
			}
			
			safePassword = false;
		}
		
	});
	
	$('#confirmpassword').keyup(function(){
		var password = $('#password').val();
		var confirmpassword = $('#confirmpassword').val();
		
		if(password == confirmpassword){
			$('#validation').text("Contraseñas coinciden!")
			$('#validation').removeClass("invalid-feedback")
			$('#validation').addClass("valid-feedback")
			$('#confirmpassword').removeClass(' is-invalid')
			$('#confirmpassword').addClass(' is-valid')
			equalPassword = true;
		} else {
			$('#validation').text("Contraseñas distintas!")
			$('#validation').removeClass("valid-feedback")
			$('#validation').addClass("invalid-feedback")
			$('#confirmpassword').removeClass(' is-valid')
			$('#confirmpassword').addClass(' is-invalid')
			equalPassword = false;
		}
		
		if(password == ""){
			if($('#confirmpassword').hasClass("is-valid")){
				$('#confirmpassword').removeClass("is-valid")
				$('#confirmpassword').addClass("is-invalid")
			}
			equalPassword = false;
		}
		
		if(confirmpassword == ""){
			$('#validation').text("Este campo no puede estar vacío!")
			$('#validation').removeClass("valid-feedback")
			$('#validation').addClass("invalid-feedback")
			equalPassword = false;
		}
		
	});
	
	$("#formUser").submit(function(event){
		if(safePassword && equalPassword){
			$("#formUser").submit();
		} else if(!safePassword){
			alert("La contraseña no es segura")
			event.preventDefault();
		} else if(!equalPassword){
			alert("La confirmación de contraseña no coincide")
			event.preventDefault();
		}
	})
});
