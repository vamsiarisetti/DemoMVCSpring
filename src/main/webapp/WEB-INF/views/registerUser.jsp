<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New User Registration</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- Dependencies for Dialog Box -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


<style>
/* input[type=text], input[type=password], select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
} */

input[type=submit] {
    background-color: darkslateblue;
    color: white;
    padding: 11px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;

    height: 36px;
    width: 100px;
    margin: 65px 40px;
    position: relative;
    top: 50%;
    left: 50%;
}

input[type=submit]:hover {
    /* background-color: #45a049; */
    background-color: #6c5cce;
}

#main {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 4px;
	width: 570px;
	/* height: 310px; */
	padding-left: 26px;
	margin: 63px auto;
}

#divMain {
    border-radius: 20px;
    background-color: #f2f2f2;
    width: 450px;
    height: 490px;
    margin: 1px auto;
    padding-top: 30px;
}
#divFstNmMain {
    padding-left: 55px;
    padding-top: 10px;
    padding-bottom: 5px;
    width: 444px;
    float: left;
}
#divLstnmMain {
    padding-left: 59px;
    padding-top: 10px;
    padding-bottom: 10px;
    width: 444px;
    float: left;
}
#divUsrNmMain {
    float: left;
    padding-left: 63px;
    padding-top: 4px;
    padding-bottom: 10px;
    width: 444px;
}
#divCtyMain {
    padding-left: 100px;
    padding-top: 134px;
    padding-bottom: 10px;
}
#divRePwdMain {
    padding-left: 13px;
    padding-top: 10px;
    padding-bottom: 10px;
}
#divEmailMain {
    padding-left: 87px;
    padding-top: 10px;
}
#divPwdMain {
	padding-left: 65px;
    padding-top: 10px;
    padding-bottom: 10px;
}
#divFstNm, #divLstNm, #divUsrNm, #divCty, #divPwd, #divRePwd, #divEmail {
	float: left;
    padding-right: 30px;
    font-family: cursive;
}
#divSbmt {
	width: 0px;
}

h3 {
	font-family: cursive;
	color: #204D88;
	padding-top: 23px;
}
h5 {
	text-align: center;
	color: red;
}
a {
	float: right;
	color: #337ab7;
	text-decoration: none;
}
#dialog-message {
    padding: 0px;
    padding-bottom: 0px;
    width: 0px;
    height: 0px;
}
</style>
<script type="text/javascript">

var isUserExists = false;
function isUserExistsFn() {
	var username = $('#idUsrNm').val();
	$.ajax({
		type: 'GET',
		dataType: "json",
		url: "http://localhost:8080/Spring4MVCHelloWorldNoXMLDemo/UserExists/"+username,
		success: function(data) {
			//console.log('success',data);
			isUserExists = data;
			if(data == true) {
				// dialog Box
				getDialog("User already exists", "User");
				return false;
			}
			return data;
		}
	});
}
function fnSubmit() {
	if (isUserExists == true) {
		return false;
	}
	return true;
}
//Function for Dialog box with OK Button and dynamic text
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
</script>
</head>
<body onsubmit="return fnSubmit();">
	<a href="<%= request.getContextPath()%>/">Login</a>
	<div id="main">
	<form action="<%= request.getContextPath() %>/RegisterNewUser" method="post">
		<div id="dialog-message"></div>
		<h3 align="center">New User Registration</h3>
		<h5>${RegErrors}</h5>
		<div id="divMain" align="center">
			<div id="divFstNmMain">
				<div id="divFstNm">First Name *</div>
				<div style="float: left; padding-left: 50px;"><input type="text" name="FstNm"></div>
			</div>
			<div id="divLstnmMain">
				<div id="divLstNm">Last Name *</div>
				<div style="padding-left: 112px;"><input type="text" name="LstNm"></div>
			</div>
			<div id="divUsrNmMain">
				<div id="divUsrNm">Username *</div>
				<div style="padding-left: 110px;"><input type="text" name="UserNm" id="idUsrNm" onchange="isUserExistsFn()"></div>
			</div>
			<div id="divCtyMain">
				<div id="divCty">City *</div>
				<div><input type="text" name="Cty"></div>
			</div>
			<div id="divPwdMain">
				<div id="divPwd">Password *</div>
				<div style="padding-left: 38px"><input type="password" name="Passwd"></div>
			</div>
			<div id="divRePwdMain">
				<div id="divRePwd">Retype Password *</div>
				<div><input type="password" name="RePasswd"></div>
			</div>
			<div id="divEmailMain">
				<div id="divEmail">Email *</div>
				<div><input type="text" name="email"></div>
			</div>
			<div>
				<div></div>
				<div><br/></div>
			</div>
			<div id="divSbmt">
				<input type="submit" value="submit">
			</div>
		</div>
	</form>
	</div>
</body>
</html>