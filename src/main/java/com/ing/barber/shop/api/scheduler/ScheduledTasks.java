package com.ing.barber.shop.api.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {


  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Scheduled(cron = "0 15 8-17 * * MON-FRI")// every 15 minutes 9am -5pm weekdays 0 0/2 * 1/1 * ? *
  @Scheduled(cron = "0 1 11-16 * * SUN-SAT")// every 15 minutes 12pm - 4pm weekend
  @Scheduled(cron = "0 0/2 * 1/1 * ?")
  public void reportCurrentTime() {
    log.error("The time is now {} #######", dateFormat.format(new Date()));
  }
}
