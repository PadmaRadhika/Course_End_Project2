<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add Participant</title>
	<link rel="stylesheet" href="style.css" />
</head>
<body class="container">
<%@ include file="navbar.html" %>
	<div class="tabs">
		<form action="ParticipantServlet" method="post">
		<h2>Add New Participant</h2>
			<input type="hidden" name="type" value="addParticipant">		
			<label for="name" class="control-label">Full Name: </label>&emsp;&ensp;
			<input type="text" id="name" name="name" placeholder="John Watson" pattern="[A-Za-z0-9]+" required><br>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<i>(letters and numbers only, no punctuation or special characters)</i><br>
			<label for="phone">Phone: </label>&emsp;&emsp;&emsp;
			<input type="text" id="phone" name="phone" placeholder="310-945-3758" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"><br>
			<label for="email">Email: </label>&emsp;&emsp;&emsp;&ensp;
			<input type="text" id="email" name="email" placeholder="john@example.com" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"><br>
			<label for="batchname" class="control-label">Batch Name: </label>&ensp;
			<select id="bid" name="bid">
				<option value="">-Select a batch-</option>
			<c:forEach items="${batches}" var="batchItem">
				<option value="${batchItem.bid}">${batchItem.batchName}</option>				
			</c:forEach>
			</select>			
			<input type="submit" value="Submit">
		</form>
		<c:if test="${not empty errorMessage}">
				<p class="error">${errorMessage}</p>
		</c:if>	
		<c:if test="${not empty successMessage}">
				<p class="success">${successMessage}</p>
		</c:if>
	</div>
	
</body>
</html>