package com.ing.barber.shop.api.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** The type Swagger config. */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /** The constant DEFAULT_CONTACT. */
  public static final Contact DEFAULT_CONTACT =
      new Contact("Manoj Mallick", "https://github.com/manojmallick", "mmallick1990@gmail.com");

  /** The constant DEFAULT_API_INFO. */
  public static final ApiInfo DEFAULT_API_INFO =
      new ApiInfo(
          "Edwin's Barber API",
          "Edwin's Barber Shop",
          "1.0.0",
          "urn:tos",
          DEFAULT_CONTACT,
          "Apache 2.0",
          "http://www.apache.org/licenses/LICENSE-2.0");

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
      new HashSet<String>(Arrays.asList("application/json", "application/xml"));

  /**
   * Api docket.
   *
   * @return the docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(DEFAULT_API_INFO)
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
  }
}
