package uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject;

import com.godaddy.logging.Logger;
import com.godaddy.logging.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import uk.gov.ons.ctp.common.util.Wait;

public abstract class PageObjectBase {

  protected WebDriver driver;
  private Wait wait;
  protected String classPrefix;
  private static final Logger log = LoggerFactory.getLogger(PageObjectBase.class);

  public PageObjectBase() {}

  public PageObjectBase(WebDriver driver) {
    this.driver = driver;
    wait = new Wait(driver);
    wait.forLoading();
  }

  protected void waitForElement(final WebElement element, final String identifier) {
    log.debug("Waiting for: " + identifier);
    wait.forElementToBeDisplayed(5, element, identifier);
  }

  protected void waitForElement(
      final int timeout, final WebElement element, final String identifier) {
    log.debug("Waiting for: " + identifier);
    wait.forElementToBeDisplayed(timeout, element, identifier);
  }

  protected void waitForLoading() {
    wait.forLoading();
  }
}
