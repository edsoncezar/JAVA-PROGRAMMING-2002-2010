package com.wrox.projsp.ch16;

import java.util.Date;
import javax.servlet.http.*;

public class CounterListenerExample2 implements HttpSessionAttributeListener {

  // Hold the counters for number of vistors
  private static int counter = 0;

  // Increment the counter if we add a "pagehit" attribute...
  public void attributeAdded(HttpSessionBindingEvent se) {
    if(se.getName().equals("pagehit")) {
      counter++;
    }
  }

  // ...or replace it
  public void attributeReplaced(HttpSessionBindingEvent se) {
    if(se.getName().equals("pagehit")) {
      counter++;
    }
  }

  // Not interested in attributes being removed
  public void attributeRemoved(HttpSessionBindingEvent se) {}

  public static String getCounterInfo() {
    return "Number of total vistors to date = " + counter;
  } 
}
