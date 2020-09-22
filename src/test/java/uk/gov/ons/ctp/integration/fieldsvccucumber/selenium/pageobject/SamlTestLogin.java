package uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Page representing the samltest.id login page. */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SamlTestLogin extends PageObjectBase {

  public SamlTestLogin(WebDriver driver) {
    super(driver);
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//h1")
  private WebElement heading;

  @FindBy(xpath = "//*[@name=\'j_username\']")
  private WebElement userIdBox;

  @FindBy(xpath = "//*[@name=\'j_password\']")
  private WebElement passwordBox;

  @FindBy(xpath = "//*[@name=\'_eventId_proceed\']")
  private WebElement submitButton;

  @FindBy(xpath = "//*[@class=\'preloader\']")
  private WebElement preloaderDiv;

  public String getHeadingText() {
    waitForElement(heading, getClass().getSimpleName() + ".getHeadingText");
    return heading.getText();
  }

  private void clickUserIdBox() {
    waitForElement(userIdBox, getClass().getSimpleName() + ".clickUserIdBox");
    userIdBox.click();
  }

  private void addTextToUserId(String txtToAdd) {
    waitForElement(userIdBox, getClass().getSimpleName() + ".addTextToUserId");
    userIdBox.sendKeys(txtToAdd);
  }

  public void enterUserId(String userId) {
    WebDriverWait wait = new WebDriverWait(driver, 10);
    wait.until(ExpectedConditions.invisibilityOf(preloaderDiv));

    clickUserIdBox();
    addTextToUserId(userId);
  }

  private void clickPasswordBox() {
    waitForElement(10, passwordBox, getClass().getSimpleName() + ".clickPasswordBox");
    passwordBox.click();
  }

  private void addTextToPasswordBox(String txtToAdd) {
    waitForElement(passwordBox, getClass().getSimpleName() + ".addTextToPasswordBox");
    passwordBox.sendKeys(txtToAdd);
  }

  public void enterPassword(String password) {
    clickPasswordBox();
    addTextToPasswordBox(password);
  }

  public void clickSubmitButton() {
    waitForElement(submitButton, getClass().getSimpleName() + ".clickSubmitButton");
    submitButton.click();
  }
}
