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

/** Google Password challenge page */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordSSO extends PageObjectBase {

  public PasswordSSO(WebDriver driver) {
    super(driver);
    waitForLoading();
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//form//input[@type='password']")
  private WebElement passwordBox;

  @FindBy(xpath = "//*[@id=\'passwordNext\']")
  private WebElement signInButton;

  public void clickPasswordBox() {
    waitForElement(10, passwordBox, getClass().getSimpleName() + ".clickPasswordBox");
    passwordBox.click();
  }

  public void addTextToPasswordBox(String txtToAdd) {
    waitForElement(passwordBox, getClass().getSimpleName() + ".addTextToPasswordBox");
    passwordBox.sendKeys(txtToAdd);
  }

  public void enterPassword(String password) {
    clickPasswordBox();
    addTextToPasswordBox(password);
  }

  public void clickSignInButton() {
    waitForElement(signInButton, getClass().getSimpleName() + ".clickSignInButton");
    signInButton.click();
  }
}
