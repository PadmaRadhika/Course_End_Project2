<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Participant</title>
<link rel="stylesheet" href="style.css" />
</head>
<body class="container">
	<%@ include file="navbar.html"%>
	<div class="tabs">
		<form action="ParticipantServlet" name="populateParticipantForm" method="get">			
			<h2>Manage Participants</h2>
			<input type="hidden" name="type" value="populateParticipant">
			<label for="namelist" class="control-label">Select Participant: </label>
			 <select id="namelist" name="namelist" onchange="this.form.submit()" >
				<option value="">-Select a Participant-</option>
				<c:forEach items="${participants}" var="participant">
					<option value="${participant.pid}" <c:if test="${participant.pid eq selectedParticipantObject.pid}">selected="selected"</c:if>
					>${participant.name}</option>
				</c:forEach>
			</select><br></form>
			<form action="ParticipantServlet" name="updateParticipantForm" method="post">
			<input type="hidden" name="type" value="updateParticipant">
			<input type="hidden" name="pid" value="${selectedParticipantObject.pid}">
			<label for="name" class="control-label">Full Name: </label>&emsp;&emsp;&emsp; 
			<input type="text" id="name" name="name" placeholder="John Watson" value="${selectedParticipantObject.name}" pattern="[A-Za-z0-9]+" required><br>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<i>(letters and numbers only, no punctuation or special characters)</i><br>
			<label for="phone">Phone: </label>&emsp;&emsp;&emsp;&emsp;&emsp; 
			<input type="text" id="phone" name="phone" placeholder="310-945-3758" value="${selectedParticipantObject.phone}" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"><br>
			<label for="email">Email: </label>&emsp;&emsp;&emsp;&emsp;&emsp; 
			<input type="text" id="email" name="email" placeholder="john@example.com" value="${selectedParticipantObject.email}" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$"><br>
			<input type="hidden" name="selectedParticipantBid" value="${selectedParticipantObject.bid}">
			<label for="batchname" class="control-label">Batch Name: </label>&emsp;&emsp; 
			<select id="bid" name="bid" required>
				<option value="">-Select a Batch-</option>
				<c:forEach items="${batches}" var="batchItem">
					<option value="${batchItem.bid}" <c:if test="${batchItem.bid eq selectedParticipantObject.bid}">selected="selected"</c:if>
					>${batchItem.batchName}</option>
				</c:forEach>
			</select><br><br> 
			<input type="submit" value="Update Participant">
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