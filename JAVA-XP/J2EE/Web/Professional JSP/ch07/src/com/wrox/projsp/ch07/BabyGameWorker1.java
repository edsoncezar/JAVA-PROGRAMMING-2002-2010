package com.wrox.projsp.ch07;

import java.io.*;
import java.util.Properties;

public class BabyGameWorker1 {

  private Properties p = new Properties();

  public String getGuesser() {
    return p.getProperty("guesser");
  }

  public void setGuesser(String aString) {
    p.setProperty("guesser", aString);
  }

  public String getGender() {
    return p.getProperty("gender");
  }

  public void setGender(String aString) {
    p.setProperty("gender", aString);
  }

  public String getPounds() {
    return p.getProperty("pounds");
  }

  public void setPounds(String aString) {
    p.setProperty("pounds", aString);
  }

  public String getOunces() {
    return p.getProperty("ounces");
  }

  public void setOunces(String aString) {
    p.setProperty("ounces", aString);
  }

  public String getMonth() {
    return p.getProperty("month");
  }

  public void setMonth(String aString) {
    p.setProperty("month", aString);
  }

  public String getDay() {
    return p.getProperty("day");
  }

  public void setDay(String aString) {
    p.setProperty("day", aString);
  }

  public String getLength() {
    return p.getProperty("length");
  }

  public void setLength(String aString) {
    p.setProperty("length", aString);
  }

  public String getFile() {
    return p.getProperty("file");
  }

  public void setFile(String aString) {
    p.setProperty("file", aString);
  }

  public Properties getProperties() {
    return p;
  }

  public void store() throws IOException {
    FileOutputStream outer = new FileOutputStream((String)p.get("guesser"));
    p.store(outer, "Baby Game -- "+p.get("guesser")+"'s guesses");
    outer.flush();
    outer.close();
  }

  public boolean validate() {
    return (getGuesser() != null && getGender() != null &&
            getPounds()  != null && getOunces() != null &&
            getMonth()   != null && getDay()    != null &&
            getLength()  != null);
  }

}

