package com.ing.barber.shop.api.shop.service;

import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.repo.ShopRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ShopService {

    private ShopRepository shopRepository;

    public List<Shop> getAllShops(){
        return shopRepository.findAll();
    }

}
