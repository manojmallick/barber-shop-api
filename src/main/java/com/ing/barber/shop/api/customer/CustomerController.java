package com.ing.barber.shop.api.customer;

import com.ing.barber.shop.api.customer.model.Customer;
import com.ing.barber.shop.api.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private CustomerService customerService;
    @PostMapping
    public void createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
    }
}
