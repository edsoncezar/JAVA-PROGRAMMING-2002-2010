import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class FindProd extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    out.println("<html><head></head>");
    out.println("<body><h1>You have called from the "  + req.getParameter("DEPT"));
    out.println(" department!</h1></body></html>");
    out.close();
  }
}
