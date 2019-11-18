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

  #Scenario Outline: I want to verify that address search by postcode works
    #Given I have a valid Postcode <postcode>
    #When I Search Addresses By Postcode
    #Then A list of addresses for my postcode is returned
#
    #Examples: 
      #| postcode  |
      #| "EX4 1EH" |
      #| "EX41EH"  |

	@SetUpFieldServiceTests
  Scenario: Authentication of SSO
    Given I am a field officer and I have access to a device with SSO
    And I click on the job {URL}⁠ in the chrome browser
    And a field proxy authentication UI is displayed on the screen
    When I enter my correct SSO credentials and click OK
    Then the EQ launch event is triggered
    
  #@SetUpFieldServiceTests
  #Scenario: Display message to the field officer when the response has already submitted
#		Given that the response to a CCS interview job has already been submitted
#		When I click on the job url in my device
#		Then the completion message "The CCS Questionnaire has been completed." is displayed to me
#		
#	@SetUpFieldServiceTests
#	Scenario: Field Officer is already authenticated
#		Given I have already entered my credentials for SSO
#		When I click on the CCS link on my TotoMobile app
#		Then I am not presented with the SSO screen to enter my credentials
#		And the EQ launch event is triggered
		
		