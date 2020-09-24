# census-field-service-cucumber

Cucumber acceptance tests for the Census Integration team's Field Service.

The Field Service is designed to use the SAML authentication and authorisation to protect
it primary endpoint, and the SAML IDP (Identity Provider) of choice is Google GSuite. 

For automated Cucumber tests in the GCP environment, Google GSuite IDP will present Captcha pages
which makes the cucumber tests extremely difficult or impossible to write. For this reason, this 
project can be configured to run against an alternative IDP provider that does not present a Captcha challenge.

## Prerequisites

* RabbitMQ must be running on port 35672. For running locally, see [RH Service README](https://github.com/ONSdigital/census-rh-service) for 
  a description of how to get RabbitMQ running locally using `docker-compose`.
* The [Mock Case API Service](https://github.com/ONSdigital/census-mock-case-api-service)  must be running.
* The [Field Service](https://github.com/ONSdigital/census-field-service) must be running. See the README to ensure the selected profile,
  idpId and metadataCertificate are configured, and test it with the "LaunchEQ" endpoint to see if it works. If the dummy EQ URL is set
  to www.google.com, then this will result in a 404 error page from google, with the URL having session and token parameters.

## Running locally against the Alterate IDP

No special configuration is needed, however it may be wise to ensure any application properties are not overriden, in particular
the configured username and password. The run maven in the normal way:

```
unset CONFIG_USERNAME
unset CONFIG_PASSWORD
mvn clean install
```

## Running locally against the Google GSuite IDP

You will need to configure the password that matches the configured username in **application-google.yml** and run with
the **google** profile:

```
export CONFIG_PASSWORD='<password>'
mvn clean install -Dspring.profiles.active=google
```

## Running in the GCP DEV or TEST environment

The Field Service will be running in the environments, configured for the alternate IPD so that Captchas are not a problem.

A concourse pipeline will run the cucumber tests either triggered or automatically in the normal way.

It is important that the the **fs-cucumber-identity** has the correct username/password. For more information
see section "Using an alternate IDP" in the README at [Field Service](https://github.com/ONSdigital/census-field-service).

