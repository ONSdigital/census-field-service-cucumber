package uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordSSO {

  private WebDriver driver;

  public PasswordSSO(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "/html/body/div/div[2]/div[1]/h2")
  private WebElement ssoTitle;

  @FindBy(css = "#Passwd")
  private WebElement passwordBox;

  @FindBy(css = "#signIn")
  private WebElement signInButton;

  public String getSSOTitleText() {
    return ssoTitle.getText();
  }

  public void clickPasswordBox() {
    passwordBox.click();
  }

  public void addTextToPasswordBox(String txtToAdd) {
    passwordBox.sendKeys(txtToAdd);
  }

  public void enterPassword(String password) {
    clickPasswordBox();
    addTextToPasswordBox(password);
  }

  public void clickSignInButton() {
    signInButton.click();
  }
}
