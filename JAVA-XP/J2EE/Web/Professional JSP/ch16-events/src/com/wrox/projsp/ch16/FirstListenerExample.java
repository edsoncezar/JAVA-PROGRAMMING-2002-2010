package com.wrox.projsp.ch16;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

// This listener is a simple ServletContext listener
public class FirstListenerExample implements ServletContextListener {

  private String FILENAME;

  public void contextInitialized(ServletContextEvent evt) {
    ServletContext ctx = evt.getServletContext();
    FILENAME = (String) ctx.getInitParameter("FILENAME");
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
                         + FILENAME + " " + e);
      System.err.println("If this is the fist time the listener " +
                         "example is being run, this exception " +
                         "is normal, it will cause the db to be created " +
                         "when the server is gracefully shutdown");
    } 

    // set the directory as an attribute of the context so that its
    // available thoughout the Web App.
    ctx.setAttribute("directory", props);
  } 


  // Methods of the ServletContextLisener interface
  public void contextDestroyed(ServletContextEvent evt) {
    Properties props = 
      (Properties) evt.getServletContext().getAttribute("directory");
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
                         + FILENAME + " " + e);
    } 
  } 
}
