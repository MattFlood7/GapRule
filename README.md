Matt Flood's CampSpot GapRule Coding Exercise
9/29/16

Configuration:
- Install Java 1.8 JDK: http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html
- Install Ant: https://ant.apache.org/bindownload.cgi

Overview:
The implementation is pretty simple. There are a few classes that have been created:
- CampSpotJSONParser.java a static class that is used to parse the JSON file passed and returns the ReservationSearch and
  CampSite list objects found when parsing the JSON file.
- The ReservationSearch class store the start and end dates found in the JSON.
- The Reservation class is used to store camp site reservations.
- The CampSite class is used to store the id, name, and list of reservations found in the JSON.

Most of the action happens in main where once the jar is built you can pass a gap rule argument that is parsed in main.
Once that gets taken care of the JSON gets parsed and store in the corresponding search and campsites variables. After that we
begin looping through the different camp sites and their corresponding reservations. First we validate that there is no overlap
against the search date range and the reservations found. Once we approve that we loop through the reservations again and make sure
the gap rule is verified against the search start or end date depending on when the existing reservation occurs. If the camp site
meets the gap rule criteria with the search passed we add it to a list of open camp sites that are returned. At the end we loop
through the camp sites and print their names out to the console.

Building the Project:
- To build the java docs run the following command at the root directory of the project: ant javadocs
- To compile the code run the following command at the root directory of the project: ant build
- To compile the code and build the jar file run the following command at the root directory of the project: ant createjar
- To clean all of the built properties of the project (GapRule.jar, bin, and javadocs) run the following command at the
  root directory of the project: ant cleanall

Running the Project:
- Compile the code and build the jar file run: ant createjar
- To run the project execute the following command where the jar file was created: java -jar GapRule.jar
  - NOTE: This wi'l run with a default gap rule of 0 days
- To run the project with a defined gap rule execute the following command where the jar file was created: java -jar GapRule.jar gapRule=1

WARNING: When running the project it is assumed that you will keep the jar file where it is built.
It is necessary to stay where it is because it needs to rely on the assets directory and json file to be present to function.
