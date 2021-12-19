package com.wrox.projsp.ch15.travelbase;

import java.io.*;

public class DealsFinder {
  queryBean myQuery;

  public DealsFinder() {
    }
  public void init() {
    try {
      myQuery = new queryBean();
      myQuery.makeConnection();
    } catch (Exception e) {
      }
  }
  public String locateDealsXML() {
    String retVal = "";
    try {
      myQuery.getDeals("" + 0 ); 
      while (myQuery.getNextTrip())  {
        retVal += "<trip number=\"" + myQuery.getColumn("tripno") + "\" region=\"" 
             + myQuery.getColumn("region")
             + "\"><startdate>" + myQuery.getColumn("startdate")
             + "</startdate><duration>" + myQuery.getColumn("duration") 
             + "</duration><location>"+ myQuery.getColumn("location") 
             + "</location><price commission=\"" + myQuery.getColumn("commission")
             + "\">" + myQuery.getColumn("price") + "</price></trip>\n";
      }
    } catch (Exception ex) {
        retVal = "";
      }
    return retVal;
  }
}
