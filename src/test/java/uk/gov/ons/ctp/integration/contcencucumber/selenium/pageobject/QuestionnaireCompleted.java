package uk.gov.ons.ctp.integration.contcencucumber.selenium.pageobject;

import lombok.AllArgsConstructor;
import org.openqa.selenium.support.How;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionnaireCompleted {

  private WebDriver driver;

  public QuestionnaireCompleted(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  @FindBy(xpath = "/html/body/h1[1]")
  private WebElement ccsCompletedTitle;
  
  public String getCCSCompletedTitleText() {
	  return ccsCompletedTitle.getText();
  }

}
