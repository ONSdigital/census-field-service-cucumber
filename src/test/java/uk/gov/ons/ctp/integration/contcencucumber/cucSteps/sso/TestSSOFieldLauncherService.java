package uk.gov.ons.ctp.integration.contcencucumber.cucSteps.sso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.gov.ons.ctp.common.error.CTPException;
import uk.gov.ons.ctp.integration.contactcentresvc.representation.AddressQueryResponseDTO;
import uk.gov.ons.ctp.integration.contactcentresvc.representation.AddressUpdateRequestDTO;
import uk.gov.ons.ctp.integration.contcencucumber.cucSteps.TestEndpoints;

public class TestSSOFieldLauncherService extends TestEndpoints {

	private static final Logger log = LoggerFactory.getLogger(TestSSOFieldLauncherService.class);
	private WebDriver driver = null;
	private String baseUrl;
    private AddressQueryResponseDTO addressQueryResponseDTO;
    private AddressUpdateRequestDTO addressUpdateRequestDTO;
    private String postcode = "";
    private String addressSearchString = "";
    private String uprn;

    @Before("@SetUpFieldServiceTests")
	public void setup() throws CTPException {
		setupOSWebdriver();
		setupDriverURL();
	}
    
    @Given("I have a valid Postcode {string}")
    public void i_have_a_valid_Postcode(final String postcode) {
        this.postcode = postcode;
    }

    @When("I Search Addresses By Postcode")
    public void i_Search_Addresses_By_Postcode() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(ccBaseUrl)
                .port(ccBasePort)
                .pathSegment("addresses")
                .pathSegment("postcode")
                .queryParam("postcode", postcode);
        addressQueryResponseDTO = getRestTemplate().getForObject(builder.build().encode().toUri(), AddressQueryResponseDTO.class);
    }

    @Then("A list of addresses for my postcode is returned")
    public void a_list_of_addresses_for_my_postcode_is_returned() {
        assertNotNull("Address Query Response must not be null", addressQueryResponseDTO);
        assertNotEquals("Address list size must not be zero", 0, addressQueryResponseDTO.getAddresses().size());
    }

    @Given("I have an invalid Postcode {string}")
    public void i_have_an_invalid_Postcode(String postcode) {
        this.postcode = postcode;
    }

    @When("I Search Addresses By Invalid Postcode")
    public void i_Search_Addresses_By_Invalid_Postcode() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(ccBaseUrl)
                .port(ccBasePort)
                .pathSegment("addresses")
                .pathSegment("postcode")
                .queryParam("postcode", postcode);
            try {
                addressQueryResponseDTO = getRestTemplate().getForObject(builder.build().encode().toUri(), AddressQueryResponseDTO.class);
            }
            catch (HttpClientErrorException hcee) {
              assertNull(" Invalid format Address Query Response must be null", addressQueryResponseDTO);
            }
    }

    @Then("An empty list of addresses for my postcode is returned")
    public void an_empty_list_of_addresses_for_my_postcode_is_returned() {
        if (addressQueryResponseDTO != null) {
            assertNotNull("Address Query Response must not be null", addressQueryResponseDTO);
            assertEquals("Address list size must be zero", 0, addressQueryResponseDTO.getAddresses().size());
        }
    }

    @Given("I have a valid address {string}")
    public void i_have_a_valid_address(String addressSearchString) {
        this.addressSearchString = addressSearchString;
    }

    @When("I Search Addresses By Address Search")
    public void i_Search_Addresses_By_Address_Search() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(ccBaseUrl)
                .port(ccBasePort)
                .pathSegment("addresses")
                .queryParam("input", addressSearchString);
        addressQueryResponseDTO = getRestTemplate().getForObject(builder.build().encode().toUri(), AddressQueryResponseDTO.class);
    }

    @Then("A list of addresses for my search is returned")
    public void a_list_of_addresses_for_my_search_is_returned() {
        assertNotNull("Address Query Response must not be null", addressQueryResponseDTO);
        assertNotEquals("Address list size must not be zero", 0, addressQueryResponseDTO.getAddresses().size());
    }

    @Given("I have an invalid address {string}")
    public void i_have_an_invalid_address(String addressSearchString) {
        this.addressSearchString = addressSearchString;
    }

    @When("I Search invalid Addresses By Address Search")
    public void i_Search_invalid_Addresses_By_Address_Search() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(ccBaseUrl)
                .port(ccBasePort)
                .pathSegment("addresses")
                .queryParam("input", addressSearchString);
        try {
            addressQueryResponseDTO = getRestTemplate().getForObject(builder.build().encode().toUri(), AddressQueryResponseDTO.class);
        }
        catch (HttpClientErrorException hcee) {
            assertNull(" Invalid format Address Query Response must be null", addressQueryResponseDTO);
        }
    }

    @Then("An empty list of addresses for my search is returned")
    public void an_empty_list_of_addresses_for_my_search_is_returned() {
        if (addressQueryResponseDTO != null) {
            assertNotNull("Address Query Response must not be null", addressQueryResponseDTO);
            assertEquals("Address list size must be zero", 0, addressQueryResponseDTO.getAddresses().size());
        }
    }

    @Given("I have a new uprn and address {string} {string}")
    public void i_have_a_new_uprn_and_address(String uprn, String address) {
        this.uprn = uprn;
        addressUpdateRequestDTO = populateAddress(address);
    }

    @When("I post the new address")
    public void i_post_the_new_address() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(ccBaseUrl)
                .port(ccBasePort)
                .pathSegment("addresses")
                .pathSegment(uprn);
        getRestTemplate().postForObject(builder.build().encode().toUri(), addressUpdateRequestDTO, String.class);
    }

    @Then("The new address is posted successfully")
    public void the_new_address_is_posted_successfully() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
    
    @Given("I am a field officer and I have access to a device with SSO")
    public void i_am_a_field_officer_and_I_have_access_to_a_device_with_SSO() {
        log.info("Nothing to do here: I am a field officer and I have access to a device with SSO");
    }

    @When("I click on the job \\{URL}⁠ in the chrome browser")
    public void i_click_on_the_job_⁠_in_the_chrome_browser() {
    	driver.get(baseUrl);
    }
    
    private AddressUpdateRequestDTO populateAddress(final String address) {
        final String[] addressParamater = address.split(",");
        return new AddressUpdateRequestDTO(
                addressParamater [0], // address line 1
                addressParamater [1], // address line 2
                addressParamater [2], // address line 3
                addressParamater [3], // town name
                addressParamater [4], // region
                addressParamater [5], // postcode
                AddressUpdateRequestDTO.Category.valueOf(addressParamater[6]), // category
                AddressUpdateRequestDTO.Type.valueOf(addressParamater[7]), // type
                addressParamater [8], // title
                addressParamater [9], // forename
                addressParamater [10], // surname
                new Date()); // date time
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
