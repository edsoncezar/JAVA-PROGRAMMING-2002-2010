package com.wrox.projsp.appG.carbeans;

// this line (above) is really important, it defines the
// directory that the bean exists in

public class CarCost {

  // our car cost bean
  String car;
  double cost;

  // set the car type
  public void setCar(String carName) {
    this.car = carName;
  } 

  // get back the car type
  public String getCar() {
    return (this.car);
  } 

  // set the car cost
  public void setCost(double carCost) {
    this.cost = carCost;
  } 

  // get back the car cost
  public double getCost() {
    return (this.cost);
  } 
}
