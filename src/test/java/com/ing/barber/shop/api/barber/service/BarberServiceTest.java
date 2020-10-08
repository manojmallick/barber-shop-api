package com.ing.barber.shop.api.barber.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.ing.barber.shop.api.barber.model.Barber;
import com.ing.barber.shop.api.barber.repo.BarberRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/** The type Availability service test. */
@RunWith(MockitoJUnitRunner.class)
public class BarberServiceTest {

  @Mock private BarberRepository barberRepository;

  @InjectMocks private BarberService barberService;

  /** Gets all availability by date non existing shop. */
  @Ignore
  @Test
  public void getAllBarbers() {
    // mock
    when(barberRepository.findAll()).thenReturn(Arrays.asList(getBarber(), getBarber()));

    List<Barber> barbers = barberService.getAllBarbers();
    assertNotNull(barbers);
  }

  private Barber getNewBarber(String s) {
    Barber newBarber = new Barber();
    newBarber.setId(s);
    return newBarber;
  }



  private Barber getBarber() {
    Barber mockBarber = getNewBarber("1");
    return mockBarber;
  }
}
