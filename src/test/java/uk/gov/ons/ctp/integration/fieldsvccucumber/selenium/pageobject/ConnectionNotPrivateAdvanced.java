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
public class ConnectionNotPrivateAdvanced {

  private WebDriver driver;

  public ConnectionNotPrivateAdvanced(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//*[@id=\'proceed-link\']")
  private WebElement proceedLink;

  public void clickProceedLink() {
    proceedLink.click();
  }
}
