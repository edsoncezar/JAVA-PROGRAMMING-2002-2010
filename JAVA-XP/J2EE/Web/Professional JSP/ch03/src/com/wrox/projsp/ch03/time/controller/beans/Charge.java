package com.wrox.projsp.ch03.time.controller.beans;

public class Charge implements java.io.Serializable {

  private String name;
  private String project;
  private String hours;
  private String date;

  public Charge() {}

  public String getName() {
    return name;
  } 
  public void setName(String n) {
    name = n;
  } 

  public String getProject() {
    return project;
  } 
  public void setProject(String proj) {
    project = proj;
  } 

  public String getHours() {
    return hours;
  } 
  public void setHours(String h) {
    hours = h;
  } 

  public String getDate() {
    return date;
  } 
  public void setDate(String d) {
    date = d;
  } 

}
