package com.ing.barber.shop.api.services.repo;

import com.ing.barber.shop.api.services.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServicesRepository extends MongoRepository<Service, String> {

}
