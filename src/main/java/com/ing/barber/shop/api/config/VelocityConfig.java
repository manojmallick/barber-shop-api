package com.ing.barber.shop.api.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VelocityConfig {

  @Bean
  public VelocityEngine getVelocityEngine() {
    VelocityEngine ve = new VelocityEngine();
    ve.setProperty("resource.loader", "class");
    ve.setProperty(
        "class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    return ve;
  }
}
