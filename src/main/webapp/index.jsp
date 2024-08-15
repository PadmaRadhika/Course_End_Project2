	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funfit home page</title>
<link rel="stylesheet" href="style.css" />
</head>
<body class="container" >
	
		<form action="LandingServlet" method="get" name="landingpage">
			<input type="hidden" name = "type" value="landingPage">
			<h1 style="color: black">Gym Management Application</h1>
			<br><br><br>
			<h2> Welcome to Zumba Funfit Gym</h2><br><br>
			<h2> Click here to go to admin dashboard</h2>
			<input type="submit" value="Admin Dashboard">
		</form>
	</body>
</html>