package com.ing.barber.shop.api.barber.repo;

import com.ing.barber.shop.api.barber.model.Barber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Barber repository.
 */
@Repository
public interface BarberRepository extends MongoRepository<Barber, String> {

}
