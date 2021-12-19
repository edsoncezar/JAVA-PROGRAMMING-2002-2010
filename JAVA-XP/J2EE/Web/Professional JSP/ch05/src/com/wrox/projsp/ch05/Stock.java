package com.wrox.projsp.ch05;

import java.io.Serializable;

public class Stock implements Serializable {

  Stock() {}

  private String name = null;
  private String tickerSymbol = null;
  private double tradingPrice = 0.0;
  private double sharesOwned = 0.0;

  public void setName(String n) {
    name = n;
  } 
  public String getName() {
    return name;
  } 

  public void setTickerSymbol(String s) {
    tickerSymbol = s;
  } 
  public String getTickerSymbol() {
    return tickerSymbol;
  } 

  public void setTradingPrice(double p) {
    tradingPrice = p;
  } 
  public double getTradingPrice() {
    return tradingPrice;
  } 

  public void setSharesOwned(double s) {
    sharesOwned = s;
  } 
  public double getSharesOwned() {
    return sharesOwned;
  } 
}
