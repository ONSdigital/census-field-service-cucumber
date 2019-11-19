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
public class CensusQuestionnaire {

  private WebDriver driver;

  public CensusQuestionnaire(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }
  
  @FindBy(xpath = "/html/body/div/div/form/header/div[2]/div/div/div[1]/img")
  private WebElement censusLogo;
  
  public void clickCensusLogo() {
    censusLogo.click();
  }

}
