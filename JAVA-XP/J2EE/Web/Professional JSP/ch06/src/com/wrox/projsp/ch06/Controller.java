package com.wrox.projsp.ch06;

import com.wrox.projsp.ch06.Constants;
import com.wrox.projsp.ch06.event.EventHandlerBase;
import com.wrox.projsp.ch06.event.UnknownEventHandler;

import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {

  protected HashMap events = new HashMap();

  public void init () throws ServletException {

    Debug.init();
    
    // get the event values and save them into events
    ResourceBundle bundle = ResourceBundle.getBundle ("Event");

    Enumeration e = bundle.getKeys();
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      String value = bundle.getString(key);
      try {
        EventHandlerBase event = (EventHandlerBase) Class.forName(value).newInstance();
        events.put (key, event);
        Debug.log (this, "init", "event:" + key + ", handler: " + event.getClass().getName());
      } catch (Exception exc) {
        Debug.log (this, "init", "event:" + key + ", NO HANDLER FOUND! " + value);
      }
    }

    Enumeration servletParams = this.getInitParameterNames();
    while (servletParams.hasMoreElements()) {
      String sName = (String) servletParams.nextElement();
      String sValue = this.getInitParameter(sName);
      Debug.log (this, "init", "servlet param name: " + sName + 
                 ", value:" + sValue);
    }

    ServletContext sc = getServletContext();
    Enumeration webappParams = sc.getInitParameterNames();
    while (webappParams.hasMoreElements()) {
      String wName = (String) webappParams.nextElement();
      String wValue = sc.getInitParameter(wName);
      Debug.log (this, "init", "webapp param name: " + wName + 
                 ", value:" + wValue);
    }
  }

  public void doGet (
                     HttpServletRequest request, 
                     HttpServletResponse response) 
    throws ServletException, IOException 
  {
    doPost (request, response);
  }

  public void doPost (
                      HttpServletRequest request, 
                      HttpServletResponse response) 
    throws ServletException, IOException 
  {

    Debug.log (this, "doPost", "new request");

    String event = validateEvent (request);

    EventHandlerBase handler = getEventHandler (event);

    try {
      handler.process (getServletContext(), request, response);
    } catch (Exception e) {
      request.setAttribute("error", e);
      handler = getEventHandler(Constants.ERROR_EVENT);
    }

    handler.forward (request, response);

  }

  protected String validateEvent (HttpServletRequest request) {
    String e = request.getParameter(Constants.EVENT);
    if (e == null || !events.containsKey(e)) {
      e = Constants.UNKNOWN_EVENT;
    }
    Debug.log (this, "validateEvent", "event=" + e);
    return e;
  }

  protected EventHandlerBase getEventHandler (String e) {
    EventHandlerBase h;
    try {
      h = (EventHandlerBase) events.get(e);
    } catch (Exception exc) {
      h = (EventHandlerBase) events.get(Constants.UNKNOWN_EVENT);
    }
    Debug.log (this, "getEventHandler", "handler=" + h.getClass().getName());
     return h;
  }

}
