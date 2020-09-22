package uk.gov.ons.ctp.integration.fieldsvccucumber.cucSteps.sso;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("config")
public class TestConfig {
  private String idpType;
  private String username;
  private String password;
  private String baseUrl;
  private String eqHost;
}
