package com.wrox.projsp.ch16;

import java.util.Date;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CounterListenerExample implements HttpSessionListener {

  // Hold the counters for number of vistors
  // and number of active sessions, starting with 0
  private static int counter = 0;
  private static int activeCount = 0;

  /**
   * Methods of the HttpSessionAttributeListener Interface
   * @param evt The session attribute evt
   */
  public void sessionCreated(HttpSessionEvent evt) {
    long time = evt.getSession().getCreationTime();
    System.out.println("A new session was created at " +
                       new Date(time));
    counter++;
    activeCount++;
  } 

  public void sessionDestroyed(HttpSessionEvent evt) {
    activeCount--;
  } 

  /**
   * Method to return an informative String about counter values
   * @return a String description
   */
  public static String getCounterInfo() {
    return "Number of active sessions on server = " + activeCount + "\n" +
           "Number of total vistors to date = " + counter;
  } 
}
