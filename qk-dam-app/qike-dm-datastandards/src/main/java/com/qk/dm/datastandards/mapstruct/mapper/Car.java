package com.qk.dm.datastandards.mapstruct.mapper;

public class Car {

  private String make;
  private int numberOfSeats;

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public int getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public Car(String make, int numberOfSeats) {
    this.make = make;
    this.numberOfSeats = numberOfSeats;
  }
}
