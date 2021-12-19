package com.wrox.projsp.ch16;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

// This listener is a combination of ServletContext and HttpSession
// listeners

public class SecondListenerExample
          implements ServletContextListener, HttpSessionAttributeListener {

  private String FILENAME;
  private ServletContext ctx;


  public void contextInitialized(ServletContextEvent evt) {
    ctx = evt.getServletContext();
    FILENAME = (String) ctx.getInitParameter("FILENAME_TWO");
    Properties props;
    try {
      ObjectInputStream oin = 
        new ObjectInputStream(new FileInputStream(FILENAME));
      Object obj = oin.readObject();
      props = (Properties) obj;
      oin.close();

    } catch (Exception e) {
      props = new Properties();
      System.err.println("Initialization failed for database file " 
                         + FILENAME + e);
      System.err.println("If this is the fist time the listener example " +
                         "is being run, this exception is normal,it will " +
                         "cause the db to be created when the server is " +
                         "gracefully shutdown");
    } 

    // set the directory as an attribute of the context so that it's
    // available thoughout the Web App.
    ctx.setAttribute("directory_two", props);
  } 


  // Methods of the ServletContextListener interface
  public void contextDestroyed(ServletContextEvent evt) {
    Properties props = (Properties) ctx.getAttribute("directory_two");
    if (props == null) {
      props = new Properties();
    } 
    try {
      ObjectOutputStream os = 
        new ObjectOutputStream(new FileOutputStream(FILENAME));
      os.writeObject(props);
      os.flush();
      os.close();
    } catch (Exception e) {
      System.err.println("Object serialization failed for database file " 
                         + FILENAME + e);
    } 
  } 


  // Methods of the HttpSessionAttributesListener interface

  // A session attribute was added... was it "DO_INSERT"? If so, parse
  // the attribute value and store in the application-scope Properties
  // object
  public void attributeAdded(HttpSessionBindingEvent evt) {
    if (evt.getName().equalsIgnoreCase("DO_INSERT")) {
      StringTokenizer st = new StringTokenizer((String) evt.getValue(), 
                                               "@");
      Properties props = (Properties) ctx.getAttribute("directory_two");
      props.setProperty(st.nextToken(), st.nextToken());
    } 
  } 

  // Same as before
  public void attributeReplaced(HttpSessionBindingEvent evt) {
    if (evt.getName().equalsIgnoreCase("DO_INSERT")) {
      StringTokenizer st = new StringTokenizer((String) evt.getValue(), 
                                               "@");
      Properties props = (Properties) ctx.getAttribute("directory_two");
      props.setProperty(st.nextToken(), st.nextToken());
    } 
  } 

  // No need to deal with attributes being removed
  public void attributeRemoved(HttpSessionBindingEvent evt) {}
}
