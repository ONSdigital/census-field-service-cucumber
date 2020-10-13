package uk.gov.ons.ctp.integration.fieldsvccucumber.main;

import io.cucumber.java.Before;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.WebDriverFactory;
import uk.gov.ons.ctp.integration.fieldsvccucumber.steps.TestConfig;

@ContextConfiguration(
    classes = {SpringIntegrationTest.class, WebDriverFactory.class, TestConfig.class},
    loader = SpringBootContextLoader.class,
    initializers = ConfigFileApplicationContextInitializer.class)
@EnableConfigurationProperties
@WebAppConfiguration
@SpringBootTest
public class SpringIntegrationTest {

  @Before(order = 0)
  public void init() {}
}
