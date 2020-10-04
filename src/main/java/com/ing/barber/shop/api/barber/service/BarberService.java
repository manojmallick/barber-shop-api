package com.ing.barber.shop.api.barber.service;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class BarberService {

    private BarberRepository barberRepository;

    public List<Barber> getAllBarbers(){
        return barberRepository.findAll();
    }

}