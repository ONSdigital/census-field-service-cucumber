# census-field-service-cucumber
Cucumber acceptance tests for the Census Integration teams Field Service

The Cucumber acceptance tests, for the Field Service, have been designed to be run in one of three possible ways, which I have called 'LOCAL', 'DEV_LOCAL', or 'DEV'. These are defined as follows:

LOCAL - This mean running the Field Service acceptance tests in your local environment and using the following URLs for the tests:

```
base-url: https://localhost:8443/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac
completed-url: https://localhost:8443/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09
invalid-case-id-url: https://localhost:8443/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz
```
DEV_LOCAL - This mean running the Field Service acceptance tests in the census-fwmt-gateway-dev environment but from outside GCP i.e. using the following URLs for the tests:

```
base-url: https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac
completed-url: https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09
invalid-case-id-url: https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz
```
DEV - This mean running the Field Service acceptance tests in the census-fwmt-gateway-dev environment from inside GCP i.e. using the following URLs for the tests:

```
base-url: https://35.244.221.21/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac
completed-url: https://35.244.221.21/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09
invalid-case-id-url: https://35.244.221.21/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz
```
Before you can run all the cucumber features in this project you need to set it up to point at the relevant runtime environment (either LOCAL, DEV_LOCAL, or DEV). The following steps need to be done:

# The Census Field Service Cucumber tests use the RUNTIME_ENV environment variable to determine the credentials and URLs to use in the scenarios. 

In the census-field-service-cucumber repo set it to either LOCAL or DEV_LOCAL or DEV:

```
export RUNTIME_ENV=LOCAL

export RUNTIME_ENV=DEV_LOCAL

export RUNTIME_ENV=DEV
```
NB. When running locally it will need to use either LOCAL or DEV_LOCAL settings, which will use URLs for localhost or the dev environment respectively. When running within GCP it will need to either be left as null or use DEV settings, so that it uses the correct settings for running inside the census-fwmt-gateway-dev, which are the default settings.
# When using a LOCAL runtime environment RabbitMQ must be running on port 35672.

Go to the census-rh-service repo and enter this command:

```
docker-compose up -d
```
# If using LOCAL or DEV_LOCAL settings then the VM Arguments need to be set up in Eclipse.

In Eclipse go to top menu bar and choose Run --> Run Configurations… In the box choose the Arguments tab and enter the following under VM arguments:

-Dspring.profiles.active=local
-Dsso.idpId=C00n4re6c
-Dsso.metadataCertificate=<content of certificate e.g. MIIDd...>


# If using LOCAL or DEV_LOCAL settings then the Mock Case API Service must also be running. 

Open the census-mock-case-api-service repo in Eclipse. Right click on the following file and choose 'Run As' --> 'Java Application':

```
uk.gov.ons.ctp.integration.mockcaseapiservice.MockCaseApiServiceApplication.java
```
# If using LOCAL or DEV_LOCAL settings then the Census Field Service must also be running. 

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
