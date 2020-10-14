package uk.gov.ons.ctp.integration.fieldsvccucumber.glue;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber"},
    features = {"src/test/resources/features"},
    glue = {
      "uk.gov.ons.ctp.integration.fieldsvccucumber.steps",
      "uk.gov.ons.ctp.integration.fieldsvccucumber.main"
    })
public class RunCucumberTestSSO {}
