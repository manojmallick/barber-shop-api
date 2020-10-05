package com.ing.barber.shop.api.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app")
@Component
public class ApplicationProperties {

  private ShopDetails shopDetails;

  public ShopDetails getShopDetails() {
    return shopDetails;
  }

  public void setShopDetails(ShopDetails shopDetails) {
    this.shopDetails = shopDetails;
  }

  public static class ShopDetails {

    private int endTime;

    public int getEndTime() {
      return endTime;
    }

    public void setEndTime(int endTime) {
      this.endTime = endTime;
    }
  }
}
