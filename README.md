# census-field-service-cucumber
Cucumber acceptance tests for the Census Integration teams Field Service

Before you can run all the cucumber features in this project you need to set it up, locally, to point at the relevant runtime environment (either LOCAL, DEV_LOCAL, or DEV). The following steps need to be done:

# The Census RH Service uses the RUNTIME_ENV environment variable to determine the credentials and URLs to use in the scenarios. 

In the census-field-service-cucumber repo set it to either LOCAL or DEV_LOCAL or DEV:

```
export RUNTIME_ENV=LOCAL

export RUNTIME_ENV=DEV_LOCAL

export RUNTIME_ENV=DEV
```
NB. When running locally it will need to use either LOCAL or DEV_LOCAL settings, which will use URLs for localhost or the dev environment respectively. When running within GCP it will need to either be left as null or use DEV settings, so that it uses the correct settings for running inside the census-fwmt-gateway-dev, which are the default settings.
# RabbitMQ must be running on port 35672.

Go to the census-rh-service repo and enter this command:

```
docker-compose up -d
```
# If using LOCAL or DEV_LOCAL settings then the VM Arguments need to be set up in Eclipse.

In Eclipse go to top menu bar and choose Run --> Run Configurations… In the box choose the Arguments tab and enter the following under VM arguments:

-Dspring.profiles.active=local
-Dsso.idpId=C00n4re6c
-Dsso.metadataCertificate=MIIDdDCCAlygAwIBAgIGAWtqUSKjMA0GCSqGSIb3DQEBCwUAMHsxFDASBgNVBAoTC0dvb2dsZSBJbmMuMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MQ8wDQYDVQQDEwZHb29nbGUxGDAWBgNVBAsTD0dvb2dsZSBGb3IgV29yazELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWEwHhcNMTkwNjE4MTEyMDMwWhcNMjQwNjE2MTEyMDMwWjB7MRQwEgYDVQQKEwtHb29nbGUgSW5jLjEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEPMA0GA1UEAxMGR29vZ2xlMRgwFgYDVQQLEw9Hb29nbGUgRm9yIFdvcmsxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzhyJny8pS/Q0JqigjV75HCxs3aJookMSK3OLZrFJaM7VB0Uom9kLadoePIeyCTvFFttITGLi22B6vMImOTZ87rG0NKpnR17WDlHKdgmaBy3U8UwGHJqvAtg3Sz3ACajpd3LYnTaDE1isaYMq/j6WZLI/YcDevo6ZCzeeELQBRuqdq4T7A/TDuDOrd3pDP2L1hEUnP1j2egPHxR3v9VmHHAY4IQ0suAxfLPtxiPxmeWX14pdhxMwp8VsvmUglZHDgsm8QPin0wLLFWoLNbCUBwEV56wAHvYCaYjVx/i+9rXJaxQyQrd8W04oLvmdE1vLEHb3adZBskg1w5c1K8PJ03QIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQABc9/BITM9I89antDHfYBV2tQKDEIZ+9OjhadX0FeixS1A3uGWtAJRSnZDOFUPjaWKkG4zuWs1M1urD0mqdOkZSAuln10v6mHWKw4t9YROFVtOLKwA+JSyhVlIqMYVLOSRuco1ZUlVlxE/6M/PcXiMmJ6qCBmllejCZODEOYJNWhoL4v2934wYT7G2DYgKUp/64ZAHL9ZH+2INukbMu2xKk/25kTbaBX3V6KUMLEnWJIS2pY95DLSLJCI6FEjdUWWizIHE2NqiEckxjemzflSxRn4W0PrLnxV1hWfthMkc7Nc2bCM2omipUHvSYXa5WULGNJORS2xUFdUAXMjZXYRe


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
