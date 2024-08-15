<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"%>   
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Update Batch</title>
	<link rel="stylesheet" href="style.css" />
</head>
<body class="container">
	<%@ include file="navbar.html" %>
	<div class="tabs">
		<form action="BatchServlet" name = "populateBatchForm" method="get">
		<h2>Manage Batches</h2>			
		<input type="hidden" name="type" value="populateBatch">
		<label for="batchlist" class="control-label">Select Batch: </label>&emsp;&emsp;&emsp;&emsp;&ensp;
		<select id="batchlist" name="batchlist"  onchange="this.form.submit()">
			<option value="">-Select a batch-</option>
			<c:forEach items="${batches}" var="batch">
				<option value="${batch.bid}"
					<c:if test="${batch.bid eq selectedBatchObject.bid}">selected="selected"</c:if>
                    >
				${batch.batchName}</option>				
			</c:forEach>	
			</select><br>
			</form>
			<form action="BatchServlet" name="updateBatchForm" method="post">			
			<input type="hidden" name="type" value="updateBatch">
			<input type="hidden" name="bid" value="${selectedBatchObject.bid }">			
			<label for="name" class="control-label">Batch Name: </label>&emsp;&emsp;&emsp;&emsp;&ensp;			
			<input type="text" id="name" name="name" placeholder="Zumba batch" value="${selectedBatchObject.batchName }" pattern="[A-Za-z0-9]+" required><br>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<i>(letters and numbers only, no punctuation or special characters)</i><br>			
			<label for="time">Batch Time: </label>&emsp;&emsp;&emsp;&emsp;&emsp;			
			<select id="time" name="time">
				<option value="">-Select Batch Time-</option>
				<option value="8.00AM" ${selectedBatchObject.batchTime == '8.00AM' ? 'selected' : ''}>8.00AM</option>
				<option value="9.00AM" ${selectedBatchObject.batchTime == '9.00AM' ? 'selected' : ''}>9.00AM</option>
				<option value="10.00AM" ${selectedBatchObject.batchTime == '10.00AM' ? 'selected' : ''}>10.00AM</option>
				<option value="5.00PM" ${selectedBatchObject.batchTime == '5.00PM' ? 'selected' : ''}>5.00PM</option>
				<option value="6.00PM" ${selectedBatchObject.batchTime == '6.00PM' ? 'selected' : ''}>6.00PM</option>
				<option value="7.00PM" ${selectedBatchObject.batchTime == '7.00PM' ? 'selected' : ''}>7.00PM</option>						
			</select><br>			
			<label for="capacity">Capacity: </label>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="text" id="capacity" name="capacity" placeholder="10" value = "${selectedBatchObject.capacity }" pattern="[0-9]{1}"><br>			
			<label for="instructor">Instructor Name: </label>&emsp;&emsp;&emsp;
			<input type="text" id="instructor" name="instructor" placeholder="John Doe" value="${selectedBatchObject.instructorName }" pattern="[A-Za-z0-9]+"><br><br>
			<input type="submit" value="Update Batch">
			<c:if test="${not empty errorMessage}">
				<p class="error">${errorMessage}</p>
			</c:if>	
			<c:if test="${not empty successMessage}">
				<p class="success">${successMessage}</p>
			</c:if>
		</form>
		
	</div>
	
</body>
</html>