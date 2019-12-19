# census-field-service-cucumber
Cucumber acceptance tests for the Census Integration teams Field Service

The Cucumber acceptance tests, for the Field Service, can be run locally in one of two possible ways, which are defined as follows:

1. Run the Field Service acceptance tests locally using the following default URLs, which are set in the application.yml config section, for the tests:

```
base-url: https://localhost:8443/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac
completed-url: https://localhost:8443/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09
invalid-case-id-url: https://localhost:8443/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz
```
2. Run the Field Service acceptance tests in the census-fwmt-gateway-dev environment but from outside GCP. It means running locally but using the following URLs, which are set in the application-dev.yml config section, for the tests:

```
base-url: https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac
completed-url: https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09
invalid-case-id-url: https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz
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
Or, to allow the settings in application-dev.yml to override those same ones in application.yml, enter the following command:

```
mvn verify -Dspring.profiles.active=dev
```