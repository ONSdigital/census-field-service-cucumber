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
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.common.util.Wait;
import uk.gov.ons.ctp.common.util.WebDriverType;
import uk.gov.ons.ctp.common.util.WebDriverUtils;
import uk.gov.ons.ctp.integration.fieldsvccucumber.main.SpringIntegrationTest;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.ConnectionNotPrivate;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.ConnectionNotPrivateAdvanced;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.InvalidCaseId;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.PasswordSSO;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.QuestionnaireCompleted;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.UsernameSSO;

public class TestSSOFieldService extends SpringIntegrationTest {

  private static final Logger log = LoggerFactory.getLogger(TestSSOFieldService.class);
  private WebDriver driver = WebDriverUtils.getWebDriver(WebDriverType.FIREFOX, true, "WARN");
  private UsernameSSO userSso = null;
  private PasswordSSO passwordSso = null;
  private QuestionnaireCompleted questionnaireCompleted = null;
  private InvalidCaseId invalidCaseId = null;
  private Wait wait = null;

  @Value("${config.username}")
  private String userId;

  @Value("${config.password}")
  private String pw;

  @Value("${config.baseurl}")
  private String baseUrl;

  private String accessEqPath = "/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac";
  private String accessEqUrl = null;
  private String completedPath = "/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09";
  private String completedUrl = null;
  private String invalidCaseIdPath = "/launch/3305e937-6fb1-4ce1-9d4c-077f147799zz";
  private String invalidCaseIdUrl = null;

  @Before("@SetUpFieldServiceTests")
  public void setup() throws CTPException, InterruptedException {
    userSso = new UsernameSSO(driver);
    passwordSso = new PasswordSSO(driver);
    questionnaireCompleted = new QuestionnaireCompleted(driver);
    invalidCaseId = new InvalidCaseId(driver);
    accessEqUrl = baseUrl + accessEqPath;
    completedUrl = baseUrl + completedPath;
    invalidCaseIdUrl = baseUrl + invalidCaseIdPath;
    wait = new Wait(driver);
  }

  @After("@TearDown")
  public void deleteDriver() {
    driver.close();
  }

  @After("@TearDownMultiWindows")
  public void deleteDriverRH2() throws InterruptedException {
    closeAnyDriverWindowsCurrentlyOpen();
  }

  private void closeAnyDriverWindowsCurrentlyOpen() throws InterruptedException {
    String mainWindow = driver.getWindowHandle();

    // To handle all new opened windows.
    Set<String> s1 = driver.getWindowHandles();
    Iterator<String> i1 = s1.iterator();

    while (i1.hasNext()) {
      String childWindow = i1.next();

      if (!mainWindow.equalsIgnoreCase(childWindow)) {

        // Switching to Child window
        driver.switchTo().window(childWindow);
        Thread.sleep(5000);

        // Closing the Child Window.
        driver.close();
      }
    }
    // Switching to Parent window i.e Main Window.
    driver.switchTo().window(mainWindow);

    // Closing the Parent Window.
    driver.close();
  }

  @Given("I am a field officer and I have access to a device with SSO")
  public void i_am_a_field_officer_and_I_have_access_to_a_device_with_SSO() {
    log.info("3 Nothing to do here: I am a field officer and I have access to a device with SSO");
  }

  @Given("I click on the job link in chrome")
  public void i_click_on_the_job_link_in_chrome() {
    log.with(accessEqUrl).debug("The job URL that was clicked on");
    driver.get(accessEqUrl);
  }

  @Given("a connection privacy warning may be displayed on the screen")
  public void a_connection_privacy_warning_may_be_displayed_on_the_screen()
      throws InterruptedException {
    if (baseUrl.equals("https://localhost:443")) {
      wait.forLoading(100);
      log.info("We are running locally so we expect a connection privacy warning to appear");
      ConnectionNotPrivate connectionNotPrivate = new ConnectionNotPrivate(driver);
      connectionNotPrivate.clickAdvancedButton();
      //        wait.forLoading(100);
      ConnectionNotPrivateAdvanced connectionNotPrivateAdvanced =
          new ConnectionNotPrivateAdvanced(driver);
      Thread.sleep(5000);
      log.info("About to click on Proceed link");
      connectionNotPrivateAdvanced.clickProceedLink();
      log.info("Just clicked on Proceed link");
    } else {
      log.info(
          "We do not appear to be running in localhost. The current URL is: "
              + driver.getCurrentUrl());
    }
  }

  @Given("a field proxy authentication UI is displayed on the screen")
  public void a_field_proxy_authentication_UI_is_displayed_on_the_screen() {

    try {
      log.info("Wait up to 100 seconds for the SSO username sign in page to appear");
      wait.forLoading(100);
    } catch (Exception e) {
      e.printStackTrace();
    }
    String titleText = userSso.getSSOTitleText();
    log.with(titleText).debug("The SSO title text found");
    assertEquals("SSO title has incorrect text", "Sign in with your Google Account", titleText);
  }

  @When("I enter my correct SSO credentials and click OK")
  public void i_enter_my_correct_SSO_credentials_and_click_OK() {
    log.with(userId).debug("The user id for the SSO");
    userSso.enterUserId(userId);
    userSso.clickNextButton();

    try {
      log.info("Wait up to 100 seconds for the SSO password sign in page to appear");
      wait.forLoading(100);
    } catch (Exception e) {
      e.printStackTrace();
    }

    passwordSso.enterPassword(pw);
    passwordSso.clickSignInButton();
  }

  @Then("the EQ launch event is triggered")
  public void the_EQ_launch_event_is_triggered() {

    try {
      log.info("Sleep for 10 seconds to give the 404 error page time to appear");
      Thread.sleep(10000);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String currentURL = driver.getCurrentUrl();
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

  @Given("that the response to a CCS interview job has already been submitted")
  public void that_the_response_to_a_CCS_interview_job_has_already_been_submitted() {
    log.info("change the base URL to be one for an interview that has already been submitted");
    accessEqUrl = completedUrl;
  }

  @Then("the completion message {string} is displayed to me")
  public void the_completion_message_is_displayed_to_me(String completionMessage) {

    try {
      log.info("Sleep for 10 seconds to give the completion message page time to appear");
      Thread.sleep(10000);
    } catch (Exception e) {
      e.printStackTrace();
    }

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

    try {
      log.info("Sleep for 5 seconds to give the new tab time to appear");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    driver.get(accessEqUrl);
  }

  @Then("I am not presented with the SSO screen to enter my credentials")
  public void i_am_not_presented_with_the_SSO_screen_to_enter_my_credentials() {

    try {
      log.info("Sleep for 5 seconds to give the SSO page time to appear");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      userSso.getSSOTitleText();
      fail();
    } catch (NoSuchElementException e) {
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

    try {
      log.info("Sleep for 5 seconds to give the invalid case page time to appear");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    String messageTextFound = invalidCaseId.getInvalidCaseIdText();
    assertEquals("Reason: Bad request - Case ID invalid", invalidCaseIdMessage, messageTextFound);
  }
}
