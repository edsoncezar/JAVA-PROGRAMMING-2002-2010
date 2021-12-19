import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DebugServlet extends HttpServlet {
  Debug debugger ;

  public void service(HttpServletRequest req, HttpServletResponse res)
              throws ServletException, IOException {

    String option = req.getParameter("option");
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    out.println("<html><head><title>Debug servlet</title></head><body>");
    out.println("<h1>Debug servlet</h1>");
    if ("socket".equals(option)) {
      if (debugger == null) {
        debugger = new Debug(9999);
      }
      debugger.startServer();
    }
    if ("closesocket".equals(option)) {
      if (debugger != null) {
        debugger.stopServer();
      }
    }
    String testValue = req.getParameter("test");
    if (testValue != null) {
      System.out.println("Debug test: " +testValue);
    }

    if ((debugger != null) && (debugger.isActive())) {
      out.print("<a href=\"telnet://");
      out.print(req.getServerName());
      out.println(":9999\" target=\"_blank\"> Connect to the debugger </a><p>");

      out.print("<a href=");
      out.print(req.getRequestURI());
      out.println("?option=closesocket> Shut down debugger </a ><p>");
    } else {
      out.print("<a href=\"");
      out.print(req.getRequestURI());
      out.println("?option=socket\"> Start remote debugger </A><p>");
    }
    out.println("<form method=\"post\" >");
    out.println("Test <input type=\"text\" name=\"test\">");
    out.println("<input type=\"submit\">");
    out.println("</form> </body> </html>");
  }
}
