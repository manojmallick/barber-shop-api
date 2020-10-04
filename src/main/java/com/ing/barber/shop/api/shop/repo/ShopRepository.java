package com.ing.barber.shop.api.shop.repo;

import com.ing.barber.shop.api.beans.Day;
import com.ing.barber.shop.api.shop.model.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {



}
