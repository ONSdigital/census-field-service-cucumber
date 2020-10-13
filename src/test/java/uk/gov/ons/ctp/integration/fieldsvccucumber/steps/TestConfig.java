package uk.gov.ons.ctp.integration.fieldsvccucumber.steps;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("config")
@Component
public class TestConfig {
  private String idpType;
  private String username;
  private String password;
  private String baseUrl;
  private String eqHost;
  private int launchTimeout;
}
