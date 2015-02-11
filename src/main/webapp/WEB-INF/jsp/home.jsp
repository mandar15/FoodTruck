<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">
<title>Food Truck Finder</title>
<style>
html,body,#map-canvas {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#panel {
	position: absolute;
	top: 5px;
	left: 50%;
	margin-left: -180px;
	z-index: 5;
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
}
</style>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
<script>
	var geocoder;
	var map;
	function initialize() {
		geocoder = new google.maps.Geocoder();
		var latlng = new google.maps.LatLng(37.46, -122.25);
		var mapOptions = {
			zoom : 8,
			center : latlng
		}
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
	}

	function codeAddress() {
		var address = document.getElementById('address').value + " "
				+ document.getElementById('region').value;
		document.getElementById('address').value = address;
		geocoder
				.geocode(
						{
							'address' : address
						},
						function(results, status) {
							if (status == google.maps.GeocoderStatus.OK) {
								document.getElementById('lattitude').value = results[0].geometry.location
										.lat();
								document.getElementById('longitude').value = results[0].geometry.location
										.lng();
								document.getElementById('locatorForm').submit();
							} else {
								alert('Sorry, there was a problem while processing your request. Please try again later');
							}
						});
	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>
	<form:form method="POST" id="locatorForm" commandName="locatorForm">
		<div id="panel">
			<center style="color: #0000ff;">
				<c:out value="${welcome}" />
			</center>
			Address:
			<form:input path="address" id="address" value="" />
			<form:errors path="address" cssStyle="color: #ff0000;" />
			<form:select path="region">
				<form:options items="${regions}" />
			</form:select>
			<form:hidden path="lattitude" id="lattitude" value="" />
			<form:hidden path="longitude" id="longitude" value="" />
			<input type="button" name="button" value="Find Food"
				onclick="codeAddress()" />
			<c:if test="${not empty msg}">
				<br />
				<center style="color: #ff0000;">
					<c:out value="${msg}" />
				</center>
			</c:if>

		</div>
	</form:form>

	<div id="map-canvas"></div>
</body>
</html>