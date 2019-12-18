package uk.gov.ons.ctp.integration.contcencucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber"},
    features = {"src/test/resources/integrationtests/sso"},
    glue = {"uk.gov.ons.ctp.integration.contcencucumber.cucSteps.sso"},
    dryRun = false)
public class RunCucumberTestSSO {}
