<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- Dependencies for Dialog Box -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
#divErrorMsg {
	padding-top: 19px;
	color: red;
}

#divMain {
	width: 500px;
	/* padding: 65px; */
	padding-top: 20px;
	padding-left: 18px;
	padding-right: 105px;
}

#divUsrMain {
    float: left;
	width: 500px;
	padding-left: 55px;
}

#divUsr {
	float: left;
	padding-right: 30px;
	font-family: cursive;
}

#divPsdMain {
	width: 500px;
	float: left;
    padding-left: 56px;
}
#divPsdMainReset {
	width: 500px;
	float: left;
}

#divPsd {
	float: left;
	padding-left: 3px;
	padding-right: 31px;
	padding-top: 10px;
	padding-bottom: 15px;
	font-family: cursive;
}
#divPsdReset {
	float: left;
	padding-left: 9px;
	padding-right: 31px;
	/* padding-top: 10px; */
	padding-bottom: 25px;
	font-family: cursive;
}
#divSbmt {
	width: 100px;
	padding-right: 62px;
	padding-top: 85px;
}

input[type=submit] {
	color: #fff;
	background-color: #204d74;
	border-color: #2e6da4;
	border-radius: 4px;
	display: inline-block;
	padding: 6px 12px;
}

#main {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 4px;
	width: 500px;
	height: 310px;
	padding-left: 26px;
	margin: 63px auto;
}

h3 {
	font-family: cursive;
	color: #204D88;
	padding-top: 23px;
}
</style>
<script type="text/javascript">
var chkPsdPrev = false;
function isUserExistsFn() {
	var username = $('#idUsrNm').val();
	//alert("UserName >>"+username);
	$.ajax({
		type: 'GET',
		dataType: "json",
		url: "http://localhost:8080/Spring4MVCHelloWorldNoXMLDemo/UserExists/"+username,
		success: function(data) {
			//alert(">>>>"+data);
			isUserExists = data;
			if(data != true) {
				// dialog Box
				getDialog("User doesnot exists", "User");
				return false;
			}
			return data;
		}
	});
}
function fnChkPwdPrev() {
	var password = $('#idPwd').val();
	var userNm = $('#idUsrNm').val();
	//alert("password >>"+password+"::userNm::"+userNm);
	$.ajax({
		type: 'GET',
		datatype: 'json',
		url: "http://localhost:8080/Spring4MVCHelloWorldNoXMLDemo/chkPwdisPrev/"+userNm+"/"+password,
		success: function(data) {
			//alert(">>>>"+data);
			chkPsdPrev = data;
			if(data == true) {
				// dialog Box
				getDialog("Password is same as previous password", "Password");
				return false;
			}
			return data;
		}
	});
}
function fnChkPwdRePwd() {
	var password = $('#idPwd').val();
	var resetPassword = $('#idRePwd').val();
	if (password != resetPassword) {
		//alert("password : "+password+":: RESPASSWD ::"+resetPassword);
		// dialog Box
		getDialog("Password and Reset Password are not identical","Reset Password");
		return false;
	}
	return true;
}

// Function for Dialog box with OK Button and dynamic text
function getDialog(msg,titletxt) {
	$( "#dialog-message").text(msg).dialog({
	      modal: true,
	      title: titletxt,
	      buttons: {
	        Ok: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
}
function fnSubmit() {
	var userNm = $('#idUsrNm').val();
	var password = $('#idPwd').val();
	var resetPassword = $('#idRePwd').val();

	if (userNm == '' || userNm == 'undefined') {
		console.log("USerName :: "+userNm);
		// dialog Box
		getDialog("Username cannot be Empty", "UserName");
		return false;
	} if (password == '' || password == 'undefined') {
		console.log(":: Password ::"+password);
		// dialog Box
		getDialog("Password cannot be Empty", "Password");
		return false;
	} if (resetPassword == '' || resetPassword == 'undefined') {
		console.log(":: reset Password ::"+resetPassword);
		// dialog Box
		getDialog("Reset Password cannot be Empty", "Reset Password");
		return false;
	}
}
</script>
</head>
<body onsubmit="return fnSubmit();">
	<%
		System.out.println("PATH >> " + request.getContextPath());
	%>
	<form action="<%=request.getContextPath()%>/ResetPassword" method="post">
		<div id="main" align="center">

			<h3>Reset Password</h3>

			<div id="dialog-message"></div>

			<div id="divErrorMsg">${LoginErrMsg}</div>
			<div id="divMain">
				<div id="divUsrMain">
					<div id="divUsr">Username :</div>
					<div style="float: left">
						<input type="text" placeholder="please enter username" name="userNm" id="idUsrNm" onchange="return isUserExistsFn();"/>
					</div>
				</div>
				<div id="divPsdMain">
					<div id="divPsd">Password :</div>
					<div style="float: left; padding-top: 10px;">
						<input type="password" placeholder="please enter password" name="userPwd" id="idPwd" onchange="return fnChkPwdPrev();" />
					</div>
				</div>
				<div id="divPsdMainReset">
					<div id="divPsdReset">Retype Password :</div>
					<div style="float: left; padding-top: 0px;">
						<input type="password" placeholder="please Re-enter password" name="userPwdReset" id="idRePwd" onchange="return fnChkPwdRePwd();"/>
					</div>
				</div>
				<div id="divSbmt">
					<button type="submit" class="btn btn-primary">Reset Password</button>
				</div>
			</div>
		</div>

	</form>
</body>
</html>