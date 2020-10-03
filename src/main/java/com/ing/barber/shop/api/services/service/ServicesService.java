package com.ing.barber.shop.api.services.service;

import com.ing.barber.shop.api.customer.model.Customer;
import com.ing.barber.shop.api.customer.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class ServicesService {

    private CustomerRepository customerRepository;

    @Transactional
    public void createCustomer(Customer customer){
        customerRepository.save(customer);
    }

}
