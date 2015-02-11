Food Truck Service	
================

Problem Statement
-------------------------
Create a web app that tells the user what types of food trucks might be found near a specific location on a map.

Solution
-----------
This git repository contains a web app prototype to address the Food Truck finder problem. When the customer visits the [web app](https://sleepy-sea-8576.herokuapp.com/home), they are asked to enter an address and select the city in which they would like to find a food truck. Currently only one city (San Francisco) is supported. Once the customer enter the address and click the “Find Food Truck” button, the web app will make a couple of REST calls in order to find results for user query.  When the web app is not able fetch any results for the user query or if some REST call fails, the web app will display an user-friendly error message. 

The prototype has been built using Java and [Spring MVC]( http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) framework. The front end comprises of two jsp pages – the first one contains a form for user query and the second one displays the result using JQuery [accordion]( http://jqueryui.com/accordion/) widget. The front end is also integrated with Google Maps to render map on the UI and Geocoding service to get geographical coordinates for the user’s address input.

Data set
-----------
The following data set was used for the problem:
[SF Food Trucks](https://data.sfgov.org/Permitting/Mobile-Food-Facility-Permit/rqzj-sfat) 

Design
---------

Frontend
------------
The frontend is fairly straightforward containing two JSP pages. First page contains the form to take user’s address input. When the user submits the address, this page talks to the Google’s Geocoding service via the Maps Javascript library to get the latitude and longitude corresponding to the address in the user query. After getting the geographical coordinates, the web app makes a REST call to the Backend with these parameters. Later, on receiving the response from the Backend, the web app will render the results using jQuery Accordion widget and plots the food truck's location on Google Map. 

Design decision was made to integrate Google’s Geocoding service in the frontend because of the query limit imposed by this service. If this service were to be called from the backend, the query limit would have been shared amongst all the Food Truck finder users limiting the number of users this web app would have served.

Backend:
-----------
The backend contains one controller named the MainController, which responds to all the user queries. On getting a HTTP POST find food truck request from the frontend, this controller will first extract the region parameter from the request. It will then use the spring injected RegionalFoodTruckFactory instance to get the RegionalFoodTruck instance corresponding to the region. The Controller will then ask the RegionalFoodTruck instance to create a REST URL, that will return nearby food trucks, by passing the query parameters from the HTTP request to it. For the SF dataset, these query parameters are latitude and longitude. The SFFoodTruck instance creates a [SODA REST URL](http://dev.socrata.com/consumers/getting-started.html) to search for data points in the circular radius of 500m with the given latitude and longitude as the center. On getting the REST URL, Controller makes a REST call to get the results in a JSON format. This JSON output is then deserialized using Jackson JSON parser and then RegionalFoodTruck instance does some filtering on the results. In the end, Controller would return the result to the UI for rendering. 

The backend code has been made very extensible by leveraging dependency injection feature of Spring framework and Factory Design pattern. Tomorrow, if the application needs to support another region, all we will have to do is - make a few changes in the spring config file to add the new region bean and create the corresponding Java class for it, which will do the URL construction and Data filtering tasks corresponding to the given data set.
 
Future work
----------------
1. UI Improvements – UI is plain and simple. We can make use of some good UI frameworks (for e.g. backbone) to improve the UI.
2. Unit Tests, Integration, E2E Tests – Currently all the testing was done manually. More sophisticated testing needs to be done.
3. Adding support for new regions.
4. Adding new features (for e.g. – User defined query search range, Get Directions, Auto-complete search, Filtering on the results etc.)

Running the code
---------------------
1. First git checkout the code in Eclipse
2. Make sure you have the [maven plugin](http://eclipse.org/m2e/) installed in your Eclipse 
3. Add a Run Configuration for maven build with Goal as “tomcat:run” and start the server. You should now be able to see the application on “localhost:8080/FoodTruck/home”
4. Alternatively, you can execute the application form the command line using the instructions [here]( https://devcenter.heroku.com/articles/java-webapp-runner#run-your-application). The application can be reached at “localhost:8080/home”

