vaadingridsortindicators
========================

Derived demo from Vaadin Template for wrong grid sort entries after calling clearSortOrder() on grid.

Demonstrate the problem in vaadin GH issue #9074 - Calling clearSortOrder() in Grid does not clear sort indicators.


Workflow
========

To compile the entire project, run "mvn install".

To run the application, run "mvn jetty:run" and open http://localhost:8080/ .

To produce a deployable production mode WAR:
- change productionMode to true in the servlet class configuration (nested in the UI class)
- run "mvn clean package"
- test the war file with "mvn jetty:run-war"
