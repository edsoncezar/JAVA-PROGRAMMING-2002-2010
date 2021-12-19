package com.wrox.projsp.ch06.event;

import com.wrox.projsp.ch06.Debug;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class EventHandlerBase {

  protected abstract String getURL();

  public void process (ServletContext sc, 
                       HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

    Debug.log (this, "process", "Using default process");
  }

  public void forward (HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

    Debug.log (this, "forward", "Using default forward");
    _dispatch (request, response);
  }

  protected void _dispatch (HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

    Debug.log (this, "_dispatch", "redirecting to " + getURL());

    RequestDispatcher rd  = request.getRequestDispatcher(getURL());
    if (rd == null) {
      Debug.log (this, "_dispatch", "rd = null!");
    }
    rd.forward (request, response);
  }
}
