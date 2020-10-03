package com.ing.barber.shop.api.services.repo;

import com.ing.barber.shop.api.customer.model.Customer;
import com.ing.barber.shop.api.services.model.Services;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServicesRepository extends MongoRepository<Services, String> {
}
