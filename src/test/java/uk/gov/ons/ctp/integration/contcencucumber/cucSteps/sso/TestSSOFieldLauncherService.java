package uk.gov.ons.ctp.integration.contcencucumber.cucSteps.sso;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.integration.contcencucumber.cucSteps.TestEndpoints;

public class TestSSOFieldLauncherService extends TestEndpoints {

	private static final Logger log = LoggerFactory.getLogger(TestSSOFieldLauncherService.class);
	private WebDriver driver = null;
	private String baseUrl;

    @Before("@SetUpFieldServiceTests")
	public void setup() throws CTPException {
		setupOSWebdriver();
		setupDriverURL();
	}
    
    @Given("I am a field officer and I have access to a device with SSO")
    public void i_am_a_field_officer_and_I_have_access_to_a_device_with_SSO() {
        log.info("Nothing to do here: I am a field officer and I have access to a device with SSO");
    }

    @When("I click on the job \\{URL}⁠ in the chrome browser")
    public void i_click_on_the_job_⁠_in_the_chrome_browser() {
    	driver.get(baseUrl);
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
