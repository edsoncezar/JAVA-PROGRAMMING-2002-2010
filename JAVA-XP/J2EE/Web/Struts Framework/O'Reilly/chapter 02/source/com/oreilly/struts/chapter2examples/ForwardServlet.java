package com.oreilly.struts.chapter2examples;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

/**
 * Perform a forward to the page "redirect.jsp"
 */
public class ForwardServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
    throws ServletException,IOException {
    forward(request, response);
  }
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response)
    throws ServletException,IOException {
    forward(request, response);
  }

  /**
   * Set a few URL parameters and objects for the request to see what happens
   * to them during a redirect.
   */
  protected void forward(HttpServletRequest req,
                         HttpServletResponse resp)
    throws ServletException,IOException {

    log("A request arrived for " + req.getServletPath());
    String redirectStr = "/result.jsp?username=foo&password=bar";
    req.setAttribute("firstName", "John");
    req.setAttribute("lastName", "Doe");
    log("forwarding to " + redirectStr);
    RequestDispatcher dispatcher = req.getRequestDispatcher(redirectStr);
    dispatcher.forward(req, resp);
  }
}
