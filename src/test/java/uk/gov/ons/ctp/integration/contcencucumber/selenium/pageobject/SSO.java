package uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject;

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
public class SSO {

  private WebDriver driver;

  public SSO(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

//  @FindBy(css = "#uac")
//  private WebElement uacTextBox;
  
  @FindBy(xpath = "/html/body/div/div[2]/div[1]/h2")
  private WebElement ssoTitle;
  
  public String getSSOTitleText() {
	  return ssoTitle.getText();
  }
  
//  public void clickUacBox() {
//    uacTextBox.click();
//  }
//  
//  public void addTextToUac(String txtToAdd) {
//    uacTextBox.sendKeys(txtToAdd);
//  }
//
//  public void enterUac(String uac) {
//    clickUacBox();
//    addTextToUac(uac);
//  }
}
