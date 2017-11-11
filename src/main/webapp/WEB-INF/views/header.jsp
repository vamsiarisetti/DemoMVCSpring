<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
#divHdr {
	/* background-color : #003399;
	background-color : #6c5cce; */
	background-color: #558C89;
}
#navBar {
	/* padding-left: 500px; */
}
a {
	text-decoration: none;
	color: white;
}
#divUserNm {
    padding-left: 80%;
    color: white;
}
#spanHome {
	padding-left: 20px;
}

#spanLogout {
    padding-left: 14px;
}


</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Header</title>
</head>
<body>
	<div id="divHdr"><h3>This is Header</h3>
		<div id="navBar">
			<div id="divUserNm">
				Hi, <%out.println(session.getAttribute("UserName"));%>
			
			<span id="spanHome">
				<a href="<%= request.getContextPath()%>/home">Home</a>
			</span>
			<span id="spanLogout">
				<a href="<%= request.getContextPath()%>/logout">Logout</a>
			</span>
			</div>
		</div>
	</div>
</body>
</html>