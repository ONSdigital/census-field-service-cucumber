package uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.google;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject.PageObjectBase;

/** Google Username challenge page */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UsernameSSO extends PageObjectBase {

  public UsernameSSO(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//*[@id=\'headingSubtext\']")
  private WebElement ssoTitle;

  @FindBy(xpath = "//*[@id=\'identifierId\']")
  private WebElement userIdBox;

  @FindBy(xpath = "//*[@id=\'identifierNext\']")
  private WebElement nextButton;

  public String getSSOTitleText() {
    waitForElement(ssoTitle, getClass().getSimpleName() + ".getSSOTitleText");
    return ssoTitle.getText();
  }

  public void clickUserIdBox() {
    waitForElement(userIdBox, getClass().getSimpleName() + ".clickUserIdBox");
    userIdBox.click();
  }

  public void addTextToUserId(String txtToAdd) {
    waitForElement(userIdBox, getClass().getSimpleName() + ".addTextToUserId");
    userIdBox.sendKeys(txtToAdd);
  }

  public void enterUserId(String userId) {
    clickUserIdBox();
    addTextToUserId(userId);
  }

  public void clickNextButton() {
    waitForElement(nextButton, getClass().getSimpleName() + ".clickNextButton");
    nextButton.click();
  }
}
