package com.ing.barber.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The type Barber shop api application. (application starting point)
 */
@SpringBootApplication
@EnableMongoRepositories
public class BarberShopApiApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(BarberShopApiApplication.class, args);
  }

}
