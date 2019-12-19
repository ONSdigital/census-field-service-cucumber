# census-field-service-cucumber
Cucumber acceptance tests for the Census Integration teams Field Service

The Cucumber acceptance tests, for the Field Service, use the following default value for the base-url, which is set in the application.yml:

```
base-url: https://localhost:8443
```
Before you can run all the cucumber features in this project the following steps need to be done:

# When using a LOCAL runtime environment RabbitMQ must be running on port 35672.

Go to the census-rh-service repo and enter this command:

```
docker-compose up -d
```
# The Mock Case API Service must also be running. 

Open the census-mock-case-api-service repo in Eclipse. Right click on the following file and choose 'Run As' --> 'Java Application':

```
uk.gov.ons.ctp.integration.mockcaseapiservice.MockCaseApiServiceApplication.java
```
# The VM Arguments need to have been set up for running the Census Field Service in Eclipse.

In Eclipse go to top menu bar and choose Run --> Run Configurations… In the box choose the Arguments tab and enter the following under VM arguments:

-Dspring.profiles.active=local
-Dsso.idpId=C00n4re6c
-Dsso.metadataCertificate=<content of certificate e.g. MIIDd...>

# The Census Field Service must be running. 

Open the census-field-service repo in Eclipse. Right click on the following file and choose 'Run As' --> 'Java Application':

```
uk.gov.ons.ctp.integration.censusfieldsvc.CensusFieldSvcApplication.java
```

# Finally, run the cucumber features in this project.

Navigate to the census-field-service-cucumber repo, in a terminal, and enter either of the following commands:

```
mvn test

mvn clean install
```
