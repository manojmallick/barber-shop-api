package com.ing.barber.shop.api.shop.repo;

import com.ing.barber.shop.api.shop.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {


}
