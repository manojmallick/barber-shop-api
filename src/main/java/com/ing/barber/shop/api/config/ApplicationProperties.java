package com.ing.barber.shop.api.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Application properties.
 */
@ConfigurationProperties("app")
@Component
public class ApplicationProperties {

  private ShopDetails shopDetails;

  /**
   * Gets shop details.
   *
   * @return the shop details
   */
  public ShopDetails getShopDetails() {
    return shopDetails;
  }

  /**
   * Sets shop details.
   *
   * @param shopDetails the shop details
   */
  public void setShopDetails(ShopDetails shopDetails) {
    this.shopDetails = shopDetails;
  }

  /**
   * The type Shop details.
   */
  public static class ShopDetails {

    private int endTime;

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public int getEndTime() {
      return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(int endTime) {
      this.endTime = endTime;
    }
  }
}
