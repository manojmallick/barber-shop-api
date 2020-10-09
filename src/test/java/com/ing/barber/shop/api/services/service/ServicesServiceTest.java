package com.ing.barber.shop.api.services.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.services.model.Service;
import com.ing.barber.shop.api.services.repo.ServicesRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServicesServiceTest {

  @Mock private ServicesRepository servicesRepository;

  @InjectMocks private ServicesService servicesService;

  @Test
  public void getAllServices() {
    // mock
    when(servicesRepository.findAll()).thenReturn(Arrays.asList(getService(), getService()));

    List<Service> services = servicesService.getAllServices();
    assertNotNull(services);
  }

  private Service getService() {
    Service service = new Service();
    return service;
  }
}
