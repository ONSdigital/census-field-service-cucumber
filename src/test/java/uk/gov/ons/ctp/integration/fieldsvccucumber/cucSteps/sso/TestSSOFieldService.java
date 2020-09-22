package uk.gov.ons.ctp.integration.fieldsvccucumber.cucSteps.sso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.ons.ctp.common.util.Wait;
import uk.gov.ons.ctp.integration.fieldsvccucumber.main.SpringIntegrationTest;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.InvalidCaseId;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.QuestionnaireCompleted;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.google.PasswordSSO;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.google.UsernameSSO;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.samltest.SamlTestLogin;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.samltest.SamlTestUserInfo;

public class TestSSOFieldService extends SpringIntegrationTest {
  private static final Logger log = LoggerFactory.getLogger(TestSSOFieldService.class);
  private WebDriver driver;
  private UsernameSSO userSso = null;
  private PasswordSSO passwordSso = null;
  private QuestionnaireCompleted questionnaireCompleted = null;
  private InvalidCaseId invalidCaseId = null;

  private SamlTestLogin samlTestLogin;
  private SamlTestUserInfo samlTestUserInfo;

  private Wait wait = null;

  @Autowired private TestConfig testConfig;
  @Autowired private WebDriverFactory driverFactory;

  private String accessEqPath = "/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac";
  private String accessEqUrl = null;
  private String completedPath = "/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09";
  private String completedUrl = null;
  private String invalidCaseIdPath = "/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz";
  private String invalidCaseIdUrl = null;

  private boolean useSamlTest = true;

  @Before("@SetUpFieldServiceTests")
  public void setup() throws Exception {
    this.driver = driverFactory.getWebDriver();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.MICROSECONDS);

    useSamlTest = "samltest".equals(testConfig.getIdpType());
    samlTestLogin = new SamlTestLogin(driver);
    samlTestUserInfo = new SamlTestUserInfo(driver);

    userSso = new UsernameSSO(driver);
    passwordSso = new PasswordSSO(driver);
    questionnaireCompleted = new QuestionnaireCompleted(driver);
    invalidCaseId = new InvalidCaseId(driver);
    accessEqUrl = testConfig.getBaseUrl() + accessEqPath;
    log.with(accessEqUrl).info("The value of accessEqUrl");
    completedUrl = testConfig.getBaseUrl() + completedPath;
    log.with(completedUrl).info("The value of completedUrl");
    invalidCaseIdUrl = testConfig.getBaseUrl() + invalidCaseIdPath;
    log.with(invalidCaseIdUrl).info("The value of invalidCaseIdUrl");
    wait = new Wait(driver);
  }

  @After("@TearDown")
  public void deleteDriver() {
    driverFactory.closeWebDriver(driver);
  }

  @Given("I am a field officer and I have access to a device with SSO")
  public void i_am_a_field_officer_and_I_have_access_to_a_device_with_SSO() {
    log.info("Nothing to do here: I am a field officer and I have access to a device with SSO");
    log.info(
        "My identity will be username: {}  password: {}",
        testConfig.getUsername(),
        testConfig.getPassword());
  }

  @Given("I click on the job link in chrome")
  public void i_click_on_the_job_link_in_chrome() {
    log.with(accessEqUrl).debug("The job URL that was clicked on");
    driver.get(accessEqUrl);
  }

  @Given("a field proxy authentication UI is displayed on the screen")
  public void a_field_proxy_authentication_UI_is_displayed_on_the_screen() {
    if (useSamlTest) {
      wait.forLoading(100);
      String headingText = samlTestLogin.getHeadingText();
      assertEquals("SAMLtest.ID".toUpperCase(), headingText.toUpperCase());
      log.info("Using SAML Test");
    } else {
      String titleText = userSso.getSSOTitleText();
      log.with(titleText).debug("The SSO title text found");
      assertEquals("SSO title has incorrect text", "Use your Google Account", titleText);
    }
  }

  @When("I enter my correct SSO credentials and click OK")
  public void i_enter_my_correct_SSO_credentials_and_click_OK() {
    if (useSamlTest) {
      samlTestLogin.enterUserId(testConfig.getUsername());
      samlTestLogin.enterPassword(testConfig.getPassword());
      samlTestLogin.clickSubmitButton();
      wait.forLoading(100);
      samlTestUserInfo.clickAccept();
    } else {
      log.with(testConfig.getUsername()).debug("The user id for the SSO");
      userSso.enterUserId(testConfig.getUsername());
      userSso.clickNextButton();
      passwordSso.enterPassword(testConfig.getPassword());
      log.info("The following password has just been entered: " + testConfig.getPassword());
      passwordSso.clickSignInButton();
      log.info("The sign in button has just been clicked");
    }
  }

  @Then("the EQ launch event is triggered")
  public void the_EQ_launch_event_is_triggered() throws Exception {
    String currentURL = waitForEqLaunch();
    String pageSource = driver.getPageSource();
    String textToFind = "<div class=\"wrapper\">";
    int pageSourceStart = pageSource.indexOf(textToFind);
    if (pageSourceStart > 0) {
      log.info(
          "*******HERE IS THE PAGE SOURCE BEGINNING********: "
              + driver.getPageSource().substring(pageSourceStart));
      log.info("*******HERE IS THE PAGE SOURCE END********");
    }
    log.with(currentURL).info("The current URL to check");
    log.info(
        "We need to assert that it tried to open the EQ page but that page does not exist i.e. that the current URL contains both the word 'session' and the word 'token'");
    assertTrue(currentURL.contains("session") && currentURL.contains("token"));
  }

  private String waitForEqLaunch() throws Exception {
    String url = null;
    for (int i = 0; i < 20; i++) {
      url = driver.getCurrentUrl();
      if (url != null && url.contains(testConfig.getEqHost())) {
        break;
      }
      Thread.sleep(500);
    }
    return url;
  }

  @Given("that the response to a CCS interview job has already been submitted")
  public void that_the_response_to_a_CCS_interview_job_has_already_been_submitted() {
    log.info("change the base URL to be one for an interview that has already been submitted");
    accessEqUrl = completedUrl;
  }

  @Then("the completion message {string} is displayed to me")
  public void the_completion_message_is_displayed_to_me(String completionMessage) {
    String titleText = questionnaireCompleted.getCCSCompletedTitleText();
    assertEquals("CCS Completion title has incorrect text", completionMessage, titleText);
  }

  @When("I click on the job link in chrome in a new window")
  public void i_click_on_the_job_link_in_chrome_in_a_new_window() {

    // Perform the click operation that opens new window
    JavascriptExecutor jse = (JavascriptExecutor) driver;
    jse.executeScript("window.open()");
    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
    driver.switchTo().window(tabs.get(1));
    driver.get(accessEqUrl);
  }

  @Then("I am not presented with the SSO screen to enter my credentials")
  public void i_am_not_presented_with_the_SSO_screen_to_enter_my_credentials() {
    try {
      userSso.getSSOTitleText();
      fail();
    } catch (TimeoutException toe) {
      log.info(
          "We are expecting that the SSO screen will not appear, therefore there should not be any SSO title element found");
    }
  }

  @Given("that the job URL contains an invalid case id")
  public void that_the_job_URL_contains_an_invalid_case_id() {
    log.info("change the base URL to be one that contains an invalid case id");
    accessEqUrl = invalidCaseIdUrl;
  }

  @Then("the invalid case id message {string} is displayed to me")
  public void the_invalid_case_id_message_is_displayed_to_me(String invalidCaseIdMessage) {
    String messageTextFound = invalidCaseId.getInvalidCaseIdText();
    assertEquals("Reason: Bad request - Case ID invalid", invalidCaseIdMessage, messageTextFound);
  }
}
