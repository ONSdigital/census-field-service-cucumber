package uk.gov.ons.ctp.integration.contcencucumber.cucSteps.sso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.integration.contcencucumber.cucSteps.TestEndpoints;
import uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject.QuestionnaireCompleted;
import uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject.SSO;

public class TestSSOFieldLauncherService extends TestEndpoints {

	private static final Logger log = LoggerFactory.getLogger(TestSSOFieldLauncherService.class);
	private WebDriver driver = null;
	private String baseUrl;
	private SSO sso = null;
//	private String userId = "cucumber_test@test.field.census.gov.uk";
//	private String pw = "Furniture1fireworks9fruit";
	private String userId = "pb@test.field.census.gov.uk";
	private String pw = "Robotron11";
	private String completedDevUrl = "https://dev-fieldservice.fwmt-gateway.census-gcp.onsdigital.uk/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09";
	private String completedUrl = "https://localhost:8443/launch/03f58cb5-9af4-4d40-9d60-c124c5bddf09";
	private QuestionnaireCompleted questionnaireCompleted;
	
    @Before("@SetUpFieldServiceTests")
	public void setup() throws CTPException {
		setupOSWebdriver();
		setupDriverURL();
		sso = new SSO(driver);
		questionnaireCompleted = new QuestionnaireCompleted(driver);
	}
    
    @After("@TearDown")
	public void deleteDriver() {
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
			log.info("Sleep for 5 seconds to give it time to attempt to load EQ");
			Thread.sleep(5000);
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
    
    private void setupDriverURL() {
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(false);
		options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
		driver = new FirefoxDriver(options);
		String rhuiBaseUrl = System.getenv("RH_DEV_BASE_URL");
		baseUrl = rhuiBaseUrl != null ? rhuiBaseUrl : "https://localhost:8443/launch/3305e937-6fb1-4ce1-9d4c-077f147789ac";
		log.with(baseUrl).info("By default the base URL will point to the dev environment");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

}
