$(document).ready(() => {
	$('.header').height($(window).height());
	
	$("#proj-user-login").hide();
	
	$("#create-btn").hide();
	
	$(".hide-button").on("click", () => { 
		$(".red-rectangle").hide();
	})
					
	$(".show-button").on("click", () => {
		$(".red-rectangle").show();
	})
					
	$(".toggle-button").on("click", () => {
		$(".red-rectangle").toggle();
	})
					
	$(".fadeIn-button").on("click", () => {
		$(".blue-rectangle").fadeIn(3000);
	})
					
	$(".fadeOut-button").on("click", () => {
		$(".blue-rectangle").fadeOut(500);
	})
					
	$(".toggleFade-button").on("click", () => {
		$(".blue-rectangle").fadeToggle("fast");
	})
	
	$("#login-btn").click(function() {
		const username = $("#login-uname").val().trim();
		const userpw = $("#login-pw").val().trim();
		const flag = "1";
		if(username != "" && userpw != "") {
			$.ajax({
				url: 'projectLogin.php',
				type:'post',
				data:{flag:flag,user:username,userpass:userpw},
				success:function(response) {
					let serverMsg = "";
					if(response == 0) {
						serverMsg = "<p id=\"success\" class=\"text-center\">Username and password accepted.</p>";
					} else {
						serverMsg = "<p id=\"failure\" class=\"text-center\">Invalid username or password.</p>";
					}
					$("#login-msg").html(serverMsg);
				}
			});
		}
	})
	
	$("#create-btn").click(function() {
		const username = $("#login-uname").val().trim();
		const userpw = $("#login-pw").val().trim();
		const flag = "2";
		if(username != "" && userpw != "") {
			$.ajax({
				url: 'projectLogin.php',
				type:'post',
				data:{flag:flag,user:username,userpass:userpw},
				success:function(response) {
					let serverMsg = "";
					if(response == 2) {
						serverMsg = "<p id=\"success\" class=\"text-center\">User successfully created. Click the login link below to login.</p>";
					} else if(response == 3) {
						serverMsg = "<p id=\"failure\" class=\"text-center\">User already exists.</p>";
					}
					$("#login-msg").html(serverMsg);
				}
			});
		}
	})
	
	$("#user-create").on("click", function() {
		$("#login-pw").attr("pattern", "(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
		$("#proj-form-head").html("Create Account");
		$("#proj-user-create").hide();
		$("#proj-user-login").show();
		$("#create-btn").show();
		$("#login-btn").hide();
		$("#login-pw").val("");
		$("#login-uname").val("");
		$("#login-pw").attr("title", "A valid password contains at least 8 characters long with at least 1 uppercase character, 1 lowercase character, and 1 number.");
	})
	
	$("#user-login").on("click", function() {
		$("#login-pw").removeAttr("pattern");
		$("#proj-form-head").html("Login");
		$("#proj-user-login").hide();
		$("#proj-user-create").show();
		$("#create-btn").hide();
		$("#login-btn").show();
		$("#login-pw").val("");
		$("#login-uname").val("");
	})

	$("#login-uname").change(function() {
		$("#login-msg").empty();
	})
	
	$("#login-pw").change(function() {
		$("#login-msg").empty();
	})
})

function validateName() {
	var x = document.forms["testName"]["fname"].value;
	if(x =="") {
		alert("This is an alert box! Please ensure that you have filled out the text field named First Name.");
		return false;
	}
}
			
function validateNumber() {
	var x = document.getElementById("numField").value;
	var response = "";
				
	if(isNaN(x) || x < 1 || x > 10) {
		response = "An example of validation text on one line below. The number entered is invalid or is not a number. :(";
	} else {
		response = "An example of validation text on one line below. The number entered is valid. :)";
	}
	document.getElementById("valOutput").innerHTML = response;
}

// Grab the responses from the server for AJAX, display contents of the response from a text file
function loadDoc() {
	const xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if(this.readyState == 1) {
			document.getElementById("r1").innerHTML = "Readystate = 1, server connection established.";
		}
		if(this.readyState == 2) {
			document.getElementById("r2").innerHTML = "Readystate = 2, request received.";
		}
		if(this.readyState == 3) {
			document.getElementById("r3").innerHTML = "Readystate = 3, processing request.";
		}
		if(this.readyState == 4) {
			document.getElementById("r4").innerHTML = "Readystate = 4, request finished and response ready.";
		}
		if(this.readyState == 4 && this.status == 200) {
			document.getElementById("demoDiv").innerHTML = this.responseText;
		}
	}
	xhttp.open("GET", "ajax_info.txt", true);
	xhttp.send();
}
// Submit an email to the database using AJAX
function submitEmail() {
	const xhttp = new XMLHttpRequest();
	const email = document.getElementById("postEmailAddr").value;
				
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			let serverMsg = "";
			if(this.status == 200) {
				serverMsg = "<p>The specified email has been submitted to the server.</p>";
			} else {
				serverMsg = "<p>There was an error submitting the email to the server. Error code: " + this.status + ".</p>";
			}
		document.getElementById("postconfirm").innerHTML = serverMsg;
		}
	}
	const data = `email=${email}`;
	xhttp.open("POST", "ajaxDemo.php");
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(data);
}
// Retrieve a specific email from the database using AJAX
function retrieveEmail() {
	const xhttp = new XMLHttpRequest();
	const email = document.getElementById("getEmailAddr").value;
				
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4) {
			let serverMsg = "";
			if(this.status == 200) {
				serverMsg = this.response;
			} else {
				serverMsg = "<p>There was an error connecting to the server or retrieving the email. Error code: " + this.status + ".</p>";
			}
			document.getElementById("getconfirm").innerHTML = serverMsg;
		}
	}
	xhttp.open("GET", `ajaxDemo.php?email=${email}`);
	xhttp.send();
}
			
function getMessage() {
	const xhttp = new XMLHttpRequest();
				
	xhttp.onreadystatechange = function() {
	if(this.readyState == 4) {
			document.getElementById("encryptedMessage").innerHTML = this.responseText;
			document.getElementById("decryptedMessage").innerHTML = decrypt(this);
		}
	}
	xhttp.open("GET", "encryptedMessage.txt", true);
	xhttp.send();
}
			

function decrypt(xhttp) {
	let msg = xhttp.responseText;
	let dmsg = "";
	for(let i = msg.length - 1; i >= 0; i--) {
		dmsg += msg.charAt(i); 
	}
	return dmsg;
}