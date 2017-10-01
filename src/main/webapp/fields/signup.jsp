<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new account</title>
<link rel="stylesheet" type="text/css" href="/login/CSS/style.css"/>
<script type="text/javascript" src="/login/JavaScript/jquery-3.2.1.min.js"></script>
</head>
<body>
<form action="/login/process-signup" method="post" id="sfields">
<p><span class="tb_label">Name</span> <input type="text" name="firstname" class="tb"/></p>
<p><span class="tb_label">Surname</span> <input type="text" name="surname" class="tb"/></p>
<p><span class="tb_label">Username</span> <input type="text" name="username" class="tb"/></p>
<p><span class="tb_label">Password</span> <input type="password" name="password" class="tb"/></p>
<input type="submit" value="Create Account"/>
</form>
<%
boolean serror = false;
if((Boolean)session.getAttribute("server-error")!=null && (Boolean)session.getAttribute("server-error")) serror = true;
String message = "";
if(serror){
	message = "The username exists, please pick another one";
	session.setAttribute("server-error", null);
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
	for(i=0; i<4; i++){
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