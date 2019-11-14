#Author: andrew.johnys@ext.ons.gov.uk
#Keywords Summary : CCS CENSUS COVERAGE SURVEY
#Feature: Test SSO to EQ using Field Launcher Service
#Scenario: Authentication of SSO
#Scenario: Display message to the field officer when the response has already submitted
#Scenario: Display a message to field officer for a Save and Resume journey
#
#The following scenario is also covered by the scenario 'Authentication of SSO'
#Scenario: Display SSO Authentication UI
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
      | postcode   |
      | "ZZ99 9ZZ" |
      | "XXX SSS"  |

  Scenario: Authentication of SSO
    Given I am a field officer and I have access to a device with SSO
    When I click on the job {URL}⁠ in the chrome browser
    Then a field proxy authentication UI is displayed on the screen
    Given I enter my correct SSO credentials and click OK
    When the EQ launch event is triggered
    Then I can view the CCS EQ Survey questions

  Scenario: Display message to the field officer when the response has already submitted
		Given that the response to a CCS interview job has already been submitted
		When I click on the job url in my device
		Then a {message}⁠ is displayed to me
		And an option to go back to main menu
		
	Scenario: Display a message to field officer for a Save and Resume journey
		Given I am conducting the CCS Interview
		And for some reason I have to stop my CCS Interview before completion
		When I click on the Save and Continue button on EQ CCS Survey
		Then I am presented with a {message}⁠ and link to launch saved EQ
		
		