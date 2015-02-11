<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>SF Food Truck Service</title>
<style>
html,body,#map-canvas {
	height: 80%;
	margin: 0px;
	padding: 0px
}
</style>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
<script>
	var map;
	var locations = new Array();
	<c:forEach items="${foodTrucks}" var="lineItem">
	  var myLatLng = new google.maps.LatLng("<c:out value="${lineItem.location.latitude}" />", 
			  "<c:out value="${lineItem.location.longitude}" />");
	  var applicant =  "<c:out value="${lineItem.applicant}" />" ;
	  locations.push({ name: applicant, loc: myLatLng});
	</c:forEach>

	function initialize() {
		var searchLatitude = "<c:out value="${locatorForm.lattitude}" />";
		var searchLongitude = "<c:out value="${locatorForm.longitude}" />";
		var latLng = new google.maps.LatLng(searchLatitude, searchLongitude);
		var mapOptions = {
			zoom : 15,
			center : latLng
		}
	
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
		
		var infowindow =  new google.maps.InfoWindow({
			content: ''
		});		
		
	    var marker = new google.maps.Marker({
	          map: map,
	          position: latLng
	    });
		bindInfoWindow(marker, map, infowindow, "<c:out value="${locatorForm.address}" />");  
	    
		while(locations.length > 0) {
			var location = locations.pop();
			var marker = new google.maps.Marker({
				map : map,
				position : location.loc,
			});
			
			bindInfoWindow(marker, map, infowindow, location.name);  
		}		
	}
	
	function bindInfoWindow(marker, map, infowindow, name) { 
		
		google.maps.event.addListener(marker, 'mouseover', function() { 
			infowindow.setContent(name); 
			infowindow.open(map, marker); 
		}); 		
		
		google.maps.event.addListener(marker, 'mouseout', function() {
			infowindow.close();
		});		
	} 

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>
	<div id="map-canvas"></div>
	<input type="hidden" path="result" id="result" value="${foodTrucks}" />
	<div id="accordion">
		<c:forEach var="lineItem" items="${foodTrucks}">
			<h3>
				<c:out value="${lineItem.applicant}" />
			</h3>
			<div>
				<p>
				<ul>
					<li>FoodItems: <c:out value="${lineItem.fooditems}" />
					</li>
					<li>Address: <c:out value="${lineItem.address}" />
					</li>
					<li>Block: <c:out value="${lineItem.block}" />
					</li>
					<li>Lot: <c:out value="${lineItem.lot}" />
					</li>
					<li><a href="${lineItem.schedule}">Schedule</a></li>
					<input type="hidden" id="location" value="${lineItem.location}" />
					<input type="hidden" id="latitude"
						value="${lineItem.location.latitude}" />
					<input type="hidden" id="longitude"
						value="${lineItem.location.longitude}" />
				</ul>
				</p>
			</div>
		</c:forEach>
	</div>
</body>
</html>
