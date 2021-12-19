package com.wrox.projsp.ch06.event;

import com.wrox.projsp.ch06.Debug;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutEventHandler extends EventHandlerBase {
  
  private ResourceBundle bundle = ResourceBundle.getBundle("URL");

  protected String getURL () {
    return bundle.getString("LOGOUT");
  }

  public void forward (
                       HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

    Debug.log (this, "forward", "invalidating session");

    HttpSession session = request.getSession();
    session.invalidate();
    super._dispatch (request, response);
  }
}
