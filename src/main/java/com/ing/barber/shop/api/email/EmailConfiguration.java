package com.ing.barber.shop.api.email;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

  @Value("${mail.smtp.port}")
  private Integer port;

  @Value("${mail.smtp.auth.enable}")
  private String isAuthEnabled;

  @Value("${mail.smtp.starttls.enable}")
  private String isTLSEnabled;

  @Value("${mail.smtp.username}")
  private String username;

  @Value("${mail.smtp.passowrd}")
  private String password;

  @Value("${mail.host}")
  private String host;

  @Value("${mail.protocol}")
  private String protocol;

  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    Properties mailProperties = new Properties();
    mailProperties.setProperty("mail.smtp.auth", isAuthEnabled);
    mailProperties.setProperty("mail.smtp.starttls.enable", isTLSEnabled);
    mailSender.setJavaMailProperties(mailProperties);
    mailSender.setHost(host);
    mailSender.setUsername(username);
    mailSender.setPassword(password);
    mailSender.setPort(port);
    mailSender.setProtocol(protocol);
    return mailSender;
  }
}
