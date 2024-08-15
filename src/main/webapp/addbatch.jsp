<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add Batch</title>
	<link rel="stylesheet" href="style.css" />
</head>
<body class="container">
<%@ include file="navbar.html" %>
	<div class="tabs">
		<form action="BatchServlet" method="post">		
		<h2>Add New Batch</h2>
			<input type="hidden" name="type" value="addBatch">			
			<label for="name" class="control-label">Batch Name: </label>&emsp;&emsp;&emsp;&emsp;
			<input type="text" id="name" name="name" placeholder="Zumba batch" pattern="[A-Za-z0-9]+" required><br>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<i>(letters and numbers only, no punctuation or special characters)</i><br>
			<label for="time">Batch Time: </label>&emsp;&emsp;&emsp;&emsp;&ensp;
			<select id="time" name="time">
				<option value="">-Select Batch Time-</option>
				<option value="8.00AM">8.00AM</option>
				<option value="9.00AM">9.00AM</option>
				<option value="10.00AM">10.00AM</option>
				<option value="5.00PM">5.00PM</option>
				<option value="6.00PM">6.00PM</option>
				<option value="7.00PM">7.00PM</option>
				<!-- Add more options as needed -->
			</select><br>
			<label for="capacity">Capacity: </label>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="text" id="capacity" name="capacity" placeholder="10" pattern="[0-9]{1}"><br>			
			<label for="instructor">Instructor Name: </label>&emsp;&emsp;&ensp;&nbsp;
			<input type="text" id="instructor" name="instructor" placeholder="John Doe" pattern="[A-Za-z0-9]+"><br><br>
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