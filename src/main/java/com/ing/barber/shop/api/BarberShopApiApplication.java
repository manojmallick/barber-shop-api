package com.ing.barber.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BarberShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberShopApiApplication.class, args);
	}

}
