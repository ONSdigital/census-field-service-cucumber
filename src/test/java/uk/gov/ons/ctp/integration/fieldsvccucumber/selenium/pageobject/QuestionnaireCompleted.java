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
public class QuestionnaireCompleted extends PageObjectBase {

  private WebDriver driver;

  public QuestionnaireCompleted(WebDriver driver) {
    super(driver);
    waitForLoading();
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "/html/body/h1[1]")
  private WebElement ccsCompletedTitle;

  public String getCCSCompletedTitleText() {
    waitForElement(ccsCompletedTitle, getClass().getSimpleName() + ".getCCSCompletedTitleText");
    return ccsCompletedTitle.getText();
  }
}
