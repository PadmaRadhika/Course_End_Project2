<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="f" uri="jakarta.tags.fmt"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="style.css" />
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>-->
<script type="text/javascript" src="scripts/Chart.js"> </script>
</head>
<body class="container">
<form>			
			<%@include file="navbar.html" %>			
			<div class="tabs" >
			<h2 style="color: black">Admin Dashboard</h2>
			<c:if test = "${not empty participants && not empty batches}">
			<canvas id="myChart" class="canvas1" ></canvas>
			<div class="canvasalign">
			<canvas id="myChart1" class="canvas2"  ></canvas>
			<canvas id="myChart2" class="canvas3"  ></canvas>
			</div>			
			</c:if>
			<c:if test="${empty participants && empty batches }">
			<br><br><h3>There are no Zumba batches and participants</h3>
			</c:if>
			</div>
			
<script>

const barColors1 = ["blue","blue","blue","lightgreen", "lightgreen","lightgreen"];
const barColors2 = ["blue","lightgreen"];

let pMap = new Map();
let bMap = new Map();
let map = new Map();
let pBids = new Array();
let bBids = new Array();
//Getting Participants and Batches information from session
//and storing them into maps to get batch timings and no of participants per batch timing
let batchWiseMap = new Map();
<c:forEach items="${batches}" var="batch">
bMap.set("${batch.bid}", "${batch.batchTime}");
batchWiseMap.set("${batch.batchName}", "${batch.batchTime}");
</c:forEach>
<c:forEach items="${participants}" var="participant">
pMap.set("${participant.pid}", "${participant.bid}");
</c:forEach>

let total = 0;
for(let [key, value] of bMap){
	total = 0;	
	  for(let [key1, value1] of pMap){
	    if(key == value1)
	      total++;	      
	  }
	  map.set(value, total);
	  
}
let bNamesMap = new Map();
for(let [key, value] of map){	
	  for(let [key1, value1] of batchWiseMap){
	    if(key == value1)
	    	bNamesMap.set(key1, value);	      
	  }	  
	  
}
let xVal = new Array();
let yVal = new Array();
let result = new Map();
let graphXvalues = new Array("8.00AM", "9.00AM", "10.00AM", "5.00PM", "6.00PM", "7.00PM");

for(let i=0;i<graphXvalues.length;i++){	
  if(map.get(graphXvalues[i]) == undefined){
    result.set(graphXvalues[i],0);
  }
  else{
    result.set(graphXvalues[i], map.get(graphXvalues[i]));
  }
}
xVal = Array.from(result.keys());
yVal = Array.from(result.values());
let morningCnt = 0;
let eveningCnt = 0;

for(let [key, value] of result){	
	if (key.includes("AM") && value > 0){
		morningCnt = morningCnt+value;		
	}
	else if(key.includes("PM") && value > 0){
		eveningCnt = eveningCnt+value;		
	}
	else
		continue;
}
let pieChartXValues = ["Morning", "Evening"];
let pieChartYValues = [morningCnt, eveningCnt];
//Batchwise X and Y values
let batchXValues = new Array();
let batchYValues = new Array();
batchXValues = Array.from(bNamesMap.keys());
batchYValues = Array.from(bNamesMap.values());

new Chart("myChart", {
  type: "bar",
  data: {
    labels: xVal,
    datasets: [{
      backgroundColor: barColors1,
      data: yVal
    }]
  },
  options: {
    legend: {display: false},
    title: {
      display: true,
      text: "Time Wise Participants Count"
    }
  }
});

new Chart("myChart2", {
	  type: "bar",
	  data: {
	    labels: batchXValues,
	    datasets: [{
	      backgroundColor: barColors1 ,
	      data: batchYValues
	    }]
	  },
	  options: {
	    legend: {display: false},
	    title: {
	      display: true,
	      text: "Batch Wise Participants Count"
	    }
	  }
	});

	
new Chart("myChart1", {
	  type: "pie",
	  data: {
	    labels: pieChartXValues,
	    datasets: [{
	      backgroundColor: barColors2,
	      data: pieChartYValues
	    }]
	  },
	  options: {
	    legend: {display: true},
	    title: {
	      display: true,
	      text: "AM / PM Participants Count"
	    }
	  }
	});
</script>
			
</form>
</body>
</html>