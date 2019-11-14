#Author: andrew.johnys@ext.ons.gov.uk
#Keywords Summary : CC CENSUS FIELD SERVICE
#Feature: Test SSO to EQ using Field Launcher Service
#Scenario: Get a list of addresses by valid postcode
## (Comments)
Feature: Test SSO to EQ using Field Launcher Service
  I want to verify that I can access the EQ using SSO for the Field Launcher Service 

  Scenario Outline: I want to verify that address search by postcode works
    Given I have a valid Postcode <postcode>
    When I Search Addresses By Postcode
    Then A list of addresses for my postcode is returned

    Examples:
    | postcode  |
    | "EX4 1EH" |
    | "EX41EH"  |

  Scenario Outline: I want to verify that address search by invalid postcode works
    Given I have an invalid Postcode <postcode>
    When I Search Addresses By Invalid Postcode
    Then An empty list of addresses for my postcode is returned

    Examples:
      | postcode  |
      | "ZZ99 9ZZ" |
      | "XXX SSS" |

      