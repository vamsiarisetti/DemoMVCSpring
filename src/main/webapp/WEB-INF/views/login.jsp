<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
#divErrorMsg {
	padding-top: 19px;
	color: red;
}

#divMain {
	width: 500px;
	padding: 65px;
	padding-top: 20px;
	padding-left: 50px;
}

#divUsrMain {
	width: 300px;
}

#divUsr {
	float: left;
	padding-right: 30px;
	font-family: cursive;
}

#divPsdMain {
	width: 300px;
}

#divPsd {
	float: left;
	padding-left: 3px;
	padding-right: 31px;
	padding-top: 10px;
	padding-bottom: 25px;
	font-family: cursive;
}

#divSbmt {
	width: 100px;
	padding-right: 62px;
	padding-top: 85px;
}

#divfrgtPwd {
	padding-top: 20px;
	padding-right: 275px;
}

a {
	color: #337ab7;
	text-decoration: none;
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
	/* width: 500px; */
	width: 500px;
	height: 310px;
	/* margin: 0px auto; */
	margin: 63px auto;
}

h3 {
	font-family: cursive;
	color: #204D88;
	padding-top: 23px;
}
</style>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body onload="doRedirect();">
	<%
		System.out.println("PATH >> " + request.getContextPath());

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		/* System.out.println("UserName>>>>>>>>>>"+session.getAttribute("UserName"));
		if (session.getAttribute("UserName") == null) {
			response.sendRedirect("/login");
		} */

	%>
	<form action="<%=request.getContextPath()%>/login" method="post">
		<div style="padding: 20px;">
			<a href="<%=request.getContextPath()%>/register"
				style="float: right;">New User Registration</a>
		</div>
		<div id="main" align="center">

			<h3>Login Here</h3>

			<div id="divErrorMsg">${LoginErrMsg}</div>
			<div id="divMain">
				<div id="divUsrMain">
					<div id="divUsr">Username :</div>
					<div style="float: left">
						<input type="text" placeholder="please enter username" name="userNm" />
					</div>
				</div>
				<div id="divPsdMain">
					<div id="divPsd">Password :</div>
					<div style="float: left; padding-top: 10px;">
						<input type="password" placeholder="please enter password" name="userPwd" />
					</div>
				</div>
				<div id="divSbmt">
					<button type="submit" class="btn btn-primary">Login</button>
				</div>
				<div id="divfrgtPwd">
					<a href="<%=request.getContextPath()%>/RdrResetpwd">Reset Password</a>
				</div>
			</div>
		</div>

	</form>
</body>
</html>