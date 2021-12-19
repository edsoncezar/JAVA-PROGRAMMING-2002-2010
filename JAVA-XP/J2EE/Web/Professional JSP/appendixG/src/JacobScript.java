import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.jacob.com.*;
import com.jacob.activeX.*;

public class JacobScript extends javax.servlet.http.HttpServlet {
  public void doGet(HttpServletRequest req, 
                    HttpServletResponse res) throws ServletException {
    PrintWriter out = null;
    try {
      res.setContentType("text/html");
      out = res.getWriter();

      // display a form
      out.println("<h2>This example invokes the COM Object " + 
                  "MSScriptControl.ScriptControl</h2>");
      out.println("<h2>The Servlet allows you to enter a VBScript " +
                  "Expression<br /> " +
                  "and get back the results of the call to COM</h1>");
      out.println("As a suggestion, try passing a simple calculation " + 
                  "such as 35+17");
      out.println("<form method=\"POST\" action=\"/appendixG/servlet/JacobScript\">");
      out.println("<input name=\"expr\" type=\"text\" width=64>");
      out.println("<input type=\"submit\">");
      out.println("</form>");
    } catch (Exception e) {
      e.printStackTrace();
      out.println("<H2>Error:" + e + "</H2>");
    } 
  } 

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                             throws ServletException {
    PrintWriter out = null;

    try {
      res.setContentType("text/html");
      out = res.getWriter();

      // get what they typed in
      String expr = (String) req.getParameter("expr");

      // make sure we have a session
      HttpSession session = req.getSession(true);
      Object sControl = null;
      if (session.isNew()) {

        // initialize the control and store it on the session
        String lang = "VBScript";
        ActiveXComponent sC = 
          new ActiveXComponent("MSScriptControl.ScriptControl");
        sControl = sC.getObject();
        Dispatch.put(sControl, "Language", lang);
        session.putValue("control", sControl);
      } else {
        sControl = session.getValue("control");
      } 
      Variant result = Dispatch.call(sControl, "Eval", expr);

      // display a form
      out.println("<h1>Enter a VBScript Expression</h1>");
      out.println("<form method=\"POST\" action=\"/appendixG/servlet/JacobScript\">");
      out.println("<input name=\"expr\" type=\"text\" value=\"" + expr 
                  + "\" width=64>");
      out.println("<input type=\"submit\">");
      out.println("</form>");
      out.println("<H1>Jacob Response:</H1>");
      out.println("<H2>" + result + "</H2>");
    } catch (Exception e) {
      e.printStackTrace();
      out.println("<H2>Error:" + e + "</H2>");
    } 
  } 
}

