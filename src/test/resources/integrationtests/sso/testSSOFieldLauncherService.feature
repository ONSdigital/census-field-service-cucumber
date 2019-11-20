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

  @SetUpFieldServiceTests @TearDown
  Scenario: Authentication of SSO
    Given I am a field officer and I have access to a device with SSO
    And I click on the job URL⁠ in the chrome browser
    And a field proxy authentication UI is displayed on the screen
    When I enter my correct SSO credentials and click OK
    Then the EQ launch event is triggered

  @SetUpFieldServiceTests @TearDown
  Scenario: Display message to the field officer when the response has already submitted
    Given that the response to a CCS interview job has already been submitted
    And I click on the job URL⁠ in the chrome browser
    When I enter my correct SSO credentials and click OK
    Then the completion message "The CCS Questionnaire has been completed." is displayed to me

  @SetUpFieldServiceTests @TearDownMultiWindows
  Scenario: Field Officer is already authenticated
    Given I have already entered my credentials for SSO
    When I click on the job URL⁠ in the chrome browser in a new window
    Then I am not presented with the SSO screen to enter my credentials
    And the EQ launch event is triggered

  @SetUpFieldServiceTests @TearDown
  Scenario: Display message to the field officer when the case id is invalid
    Given that the job URL contains an invalid case id
    And I click on the job URL⁠ in the chrome browser
    When I enter my correct SSO credentials and click OK
    Then the invalid case id message "Reason: Bad request - Case ID invalid" is displayed to me
