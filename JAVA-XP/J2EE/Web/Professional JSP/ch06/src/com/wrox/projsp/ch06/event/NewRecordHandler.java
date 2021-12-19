package com.wrox.projsp.ch06.event;

import com.wrox.projsp.ch06.Debug;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewRecordHandler extends EventHandlerBase {

  private ResourceBundle bundle = ResourceBundle.getBundle("URL");

  protected String getURL () {
    return bundle.getString("NEW_RECORD");
  }

  public void process (ServletContext sc,
                       HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {
    String project = request.getParameter("project");
    if (project.equals("")) {
      throw new IOException("Project must not be empty!");
    }
    String hours = request.getParameter("hours");
    try {
      Integer.parseInt(hours);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      throw new IOException("Hours must be an Integer! hours = " + hours);
    }
    Debug.log (this, "process", "");
  }
}
