package edu.ucsb.cs156.consentric.testconfig;

import edu.ucsb.cs156.consentric.config.SecurityConfig;
import edu.ucsb.cs156.consentric.services.CurrentUserService;
import edu.ucsb.cs156.consentric.services.GrantedAuthoritiesService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(SecurityConfig.class)
public class TestConfig {

  @Bean
  public CurrentUserService currentUserService() {
    return new MockCurrentUserServiceImpl();
  }

  @Bean
  public GrantedAuthoritiesService grantedAuthoritiesService() {
    return new GrantedAuthoritiesService();
  }
}
