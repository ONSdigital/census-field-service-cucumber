package uk.gov.ons.ctp.integration.contcencucumber.cucSteps.sso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Value;

import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.integration.contcencucumber.main.SpringIntegrationTest;
import uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject.InvalidCaseId;
import uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject.QuestionnaireCompleted;
import uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject.SSO;

public class TestSSOFieldLauncherService extends SpringIntegrationTest {

	private static final Logger log = LoggerFactory.getLogger(TestSSOFieldLauncherService.class);
	private WebDriver driver = null;
	private String baseUrl = null;
	private SSO sso = null;
	private String userId = null;
	private String pw = null;
	private String completedUrl = null;
	private QuestionnaireCompleted questionnaireCompleted = null;
	private String invalidCaseIdUrl = null;
	private InvalidCaseId invalidCaseId = null;
	private String runtimeEnvironment = null;
	
	@Value("${local-settings.username}")
	private String localUserId;
	@Value("${local-settings.password}")
	private String localPassword;
	@Value("${local-settings.base-url}")
    protected String localBaseUrl;
	@Value("${local-settings.completed-url}")
    protected String localCompletedUrl;
	@Value("${local-settings.invalid-case-id-url}")
    protected String localInvalidCaseIdUrl;
	
	@Value("${dev-local-settings.username}")
	private String devLocalUserId;
	@Value("${dev-local-settings.password}")
	private String devLocalPassword;
	@Value("${dev-local-settings.base-url}")
    protected String devLocalBaseUrl;
	@Value("${dev-local-settings.completed-url}")
    protected String devLocalCompletedUrl;
	@Value("${dev-local-settings.invalid-case-id-url}")
    protected String devLocalInvalidCaseIdUrl;
	
	@Value("${dev-settings.username}")
	private String devUserId;
	@Value("${dev-settings.password}")
	private String devPassword;
	@Value("${dev-settings.base-url}")
    protected String devBaseUrl;
	@Value("${dev-settings.completed-url}")
    protected String devCompletedUrl;
	@Value("${dev-settings.invalid-case-id-url}")
    protected String devInvalidCaseIdUrl;
	
    @Before("@SetUpFieldServiceTests")
	public void setup() throws CTPException {
    	runtimeEnvironment = System.getenv("RUNTIME_ENV");
		setupOSWebdriver();
		setupDriverAndURLs();
		sso = new SSO(driver);
		questionnaireCompleted = new QuestionnaireCompleted(driver);
		invalidCaseId = new InvalidCaseId(driver);
	}
    
    @After("@TearDown")
	public void deleteDriver() {
		driver.close();
	}
    
    @After("@TearDownMultiWindows")
	public void deleteDriverRH2() throws InterruptedException {
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
        log.debug("Nothing to do here: I am a field officer and I have access to a device with SSO");
    }
    
    @Given("I click on the job URL⁠ in the chrome browser")
    public void i_click_on_the_job_URL⁠_in_the_chrome_browser() {
    	log.with(baseUrl).debug("The job URL that was clicked on");
    	driver.get(baseUrl);
    }
    
    @Given("a field proxy authentication UI is displayed on the screen")
    public void a_field_proxy_authentication_UI_is_displayed_on_the_screen() {
    	
    	try {
			log.info("Sleep for 5 seconds to give the SSO page time to appear");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 
    	String titleText = sso.getSSOTitleText();
    	log.with(titleText).debug("The SSO title text found");
    	assertEquals("SSO title has incorrect text", "Sign in with your Google Account", titleText);
    }
    
    @When("I enter my correct SSO credentials and click OK")
    public void i_enter_my_correct_SSO_credentials_and_click_OK() {
    	log.with(userId).debug("The user id for the SSO");
        sso.enterUserId(userId);
        sso.clickNextButton();
        sso.enterPassword(pw);
        sso.clickSignInButton();
    }
    
    @Then("the EQ launch event is triggered")
    public void the_EQ_launch_event_is_triggered() {
    	
    	try {
			log.info("Sleep for 10 seconds to give it time to attempt to load EQ (this can take quite a long time in DEV)");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	String currentURL = driver.getCurrentUrl();	
    	
		log.with(currentURL).info("The current URL to check");
		log.info(
				"We need to assert that it tried to open the EQ page but that page does not exist i.e. that the current URL contains the following text: //session/%3Ftoken");
		String devTextToFind = "/session/?token";
		String localTextToFind = "/session?token";
		assertTrue(currentURL.contains(devTextToFind) || currentURL.contains(localTextToFind));
    }
    
    @Given("that the response to a CCS interview job has already been submitted")
    public void that_the_response_to_a_CCS_interview_job_has_already_been_submitted() {
        log.info("change the base URL to be one for an interview that has already been submitted");
        baseUrl = completedUrl;
    }
    
    @Then("the completion message {string} is displayed to me")
    public void the_completion_message_is_displayed_to_me(String completionMessage) {
    	
    	String titleText = questionnaireCompleted.getCCSCompletedTitleText();
    	assertEquals("CCS Completion title has incorrect text", completionMessage, titleText);
    }

    @Given("I have already entered my credentials for SSO")
    public void i_have_already_entered_my_credentials_for_SSO() {
    	log.with(baseUrl).debug("The job URL that was clicked on");
    	driver.get(baseUrl);
    	
    	try {
			log.info("Sleep for 5 seconds to give the SSO page time to appear");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	log.with(userId).debug("Now entering my credentials for the SSO");
        sso.enterUserId(userId);
        sso.clickNextButton();
        sso.enterPassword(pw);
        sso.clickSignInButton();
    }
    
    @When("I click on the job URL⁠ in the chrome browser in a new window")
    public void i_click_on_the_job_URL⁠_in_the_chrome_browser_in_a_new_window() {

    	// Perform the click operation that opens new window
    	JavascriptExecutor jse = (JavascriptExecutor)driver;
    	jse.executeScript("window.open()");
    	ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
    	driver.switchTo().window(tabs.get(1));
    	
    	try {
			log.info("Sleep for 5 seconds to give the new tab time to appear");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
        driver.get(baseUrl);
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
			sso.getSSOTitleText();
			fail();
		} catch (NoSuchElementException e) {
			log.info("We are expecting that the SSO screen will not appear, therefore there should not be any SSO title element found");
		}
		
    }
    
    @Given("that the job URL contains an invalid case id")
    public void that_the_job_URL_contains_an_invalid_case_id() {
    	
    	log.info("change the base URL to be one that contains an invalid case id");
        baseUrl = invalidCaseIdUrl;
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
    
    private void setupOSWebdriver() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("mac")) {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver/geckodriver.macos");
		} else if (os.contains("linux")) {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver/geckodriver.linux");
		} else {
			System.err.println("Unsupported platform - gecko driver not available for platform [" + os + "]");
			System.exit(1);
		}
	}
    
    private void setupDriverAndURLs() {
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		String os = System.getProperty("os.name").toLowerCase();
		/**
		 * This if statement was added because the latest stable version of firefox gets installed as
		 *"/usr/bin/firefox-esr" and then a symbolic link for it, named firefox, is created in the same 
		 *location - see the Dockerfile.
		 */
		if (os.contains("linux")) {
   		  options.setBinary("/usr/bin/firefox"); 
		}
		options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
		driver = new FirefoxDriver(options);
		
		if (runtimeEnvironment == null) {
			runtimeEnvironment = "DEV";
		}
		
		if (runtimeEnvironment.equals("LOCAL")) {
			userId = localUserId;
			pw = localPassword;
			baseUrl = localBaseUrl;
			completedUrl = localCompletedUrl;
			invalidCaseIdUrl = localInvalidCaseIdUrl;
			log.with(userId).with(pw).with(baseUrl).with(completedUrl).with(invalidCaseIdUrl)
			.debug("The runtime URLs are pointing to the LOCAL environment");
		} else if (runtimeEnvironment.equals("DEV_LOCAL")){
			userId = devLocalUserId;
			pw = devLocalPassword;
			baseUrl = devLocalBaseUrl;
			completedUrl = devLocalCompletedUrl;
			invalidCaseIdUrl = devLocalInvalidCaseIdUrl;
			log.with(userId).with(pw).with(baseUrl).with(completedUrl).with(invalidCaseIdUrl)
			.debug("The runtime URLs are pointing to the DEV environment locally");
		} else {
			userId = devUserId;
			pw = devPassword;
			baseUrl = devBaseUrl;
			completedUrl = devCompletedUrl;
			invalidCaseIdUrl = devInvalidCaseIdUrl;
			log.with(userId).with(pw).with(baseUrl).with(completedUrl).with(invalidCaseIdUrl)
			.debug("The runtime URLs are pointing to the DEV environment from within GCP");
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

}
