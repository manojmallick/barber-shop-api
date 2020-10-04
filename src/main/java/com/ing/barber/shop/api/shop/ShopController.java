package com.ing.barber.shop.api.shop;

import com.ing.barber.shop.api.shop.model.Shop;
import com.ing.barber.shop.api.shop.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/shops")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ShopController {

    private ShopService shopService;

    @GetMapping
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

}
