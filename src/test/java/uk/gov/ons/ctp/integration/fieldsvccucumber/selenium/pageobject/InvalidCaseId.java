package uk.gov.ons.ctp.integration.fieldsvccucumber.selenium.pageobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class InvalidCaseId extends PageObjectBase {

  public InvalidCaseId(WebDriver driver) {
    super(driver);
    waitForLoading();
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "/html/body/p[1]")
  private WebElement invalidCaseIdMessage;

  public String getInvalidCaseIdText() {
    waitForElement(invalidCaseIdMessage, getClass().getSimpleName() + ".getInvalidCaseIdText");
    return invalidCaseIdMessage.getText();
  }
}
