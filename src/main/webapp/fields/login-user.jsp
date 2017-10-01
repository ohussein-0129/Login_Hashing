<%@page import="javax.servlet.annotation.WebServlet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--<style><jsp:include page="/CSS/style.css"></jsp:include></style>-->
<link rel="stylesheet" type="text/css" href="/login/CSS/style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<script type="text/javascript" src="/login/JavaScript/jquery-3.2.1.min.js"></script>
</head>
<body>
<form action="/login/process-login" method="post" id="sfields">
<p><span class="tb_label">Username</span> <input type="text" name="username" class="tb"/></p>
<p><span class="tb_label">Password</span> <input type="password" name="password" class="tb"/></p>
<input type="submit" value="Login"/>
</form>
<%
boolean serror = false;
if((Boolean)session.getAttribute("server-error")!=null && (Boolean)session.getAttribute("server-error")) serror = true;
String message = "";
if(serror){
	if(((String)session.getAttribute("error-type")).equals("username")) message = "Username does not exist";
	else message = "username and/or password is incorrect, please try again";
	session.setAttribute("server-error", null);
	session.setAttribute("error-type", null);
}
%>
<p id="error"><%=message %></p>
<script type="text/javascript">
$("#sfields").on("submit", function(event){
	checkFields(event);
});

function checkFields(event){
	$inputs = $("input");
	$error = $("#error");
	var i;
	for(i=0; i<2; i++){
		if($.trim($inputs[i].value)==""){
			event.preventDefault();
			$error.html("please fill in the fields");
			break;
		}
	}
}
</script>
</body>
</html>