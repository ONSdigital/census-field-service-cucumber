#Author: eleanor.cook@ons.gov.uk
#Keywords Summary : FIELD SERVICE
#Feature: Smoke Tests for Field and Mock Case Api Services
#Scenario: I want to check that I can connect to the field service
#Scenario: I want to check that I can connect to the mock case api service
## (Comments)
Feature: Smoke Tests for Field and Mock Case Api Services
  I want to verify that the field and mock case api services are running

  @smoke
  Scenario: I want to check that I can connect to the field service
    Given I am about to do a smoke test by going to a field service endpoint
    Then I do the smoke test and receive a response of OK from the field service

  @smoke
  Scenario: I want to check that I can connect to the mock case api service
    Given I am about to do a smoke test by going to a mock case api endpoint
    Then I do the smoke test and receive a response of OK from the mock case api service
   
