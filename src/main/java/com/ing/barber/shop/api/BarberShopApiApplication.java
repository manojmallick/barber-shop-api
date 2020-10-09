package com.ing.barber.shop.api;

import com.ing.barber.shop.api.beans.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/** The type Barber shop api application. (application starting point) */
@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
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
