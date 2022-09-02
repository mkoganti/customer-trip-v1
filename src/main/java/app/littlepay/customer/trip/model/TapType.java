package app.littlepay.customer.trip.model;

import lombok.Getter;

@Getter
public enum TapType {

  ON("ON"),
  OFF("OFF");

  public String tapType;

  TapType(String tapType) {
    this.tapType = tapType;
  }
}
