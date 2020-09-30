package uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.samltest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.PageObjectBase;

/** Page representing the samltest.id information release page. */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SamlTestUserInfo extends PageObjectBase {

  public SamlTestUserInfo(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//*[@name=\'_eventId_proceed\']")
  private WebElement acceptButton;

  public void clickAccept() {
    waitForElement(acceptButton, getClass().getSimpleName() + ".clickAccept");
    acceptButton.click();
  }
}
