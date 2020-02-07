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
public class UsernameSSO {

  private WebDriver driver;

  public UsernameSSO(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "/html/body/div/div[2]/div[1]/h2")
  private WebElement ssoTitle;

  @FindBy(css = "#Email")
  private WebElement userIdBox;

  @FindBy(css = "#next")
  private WebElement nextButton;

  public String getSSOTitleText() {
    return ssoTitle.getText();
  }

  public void clickUserIdBox() {
    userIdBox.click();
  }

  public void addTextToUserId(String txtToAdd) {
    userIdBox.sendKeys(txtToAdd);
  }

  public void enterUserId(String userId) {
    clickUserIdBox();
    addTextToUserId(userId);
  }

  public void clickNextButton() {
    nextButton.click();
  }
}
