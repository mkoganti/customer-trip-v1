package app.littlepay.customer.trip.model;

import lombok.Getter;

@Getter
public enum TripStatus {

  COMPLETED("Completed"),
  CANCELLED("Cancelled"),
  INCOMPLETE("Incomplete");

  public String tripStatus;

  TripStatus(String tripStatus) {
    this.tripStatus = tripStatus;
  }
}
