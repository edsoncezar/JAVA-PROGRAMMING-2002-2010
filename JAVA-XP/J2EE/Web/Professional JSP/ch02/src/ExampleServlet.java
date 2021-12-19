import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ExampleServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {

    PrintWriter pw = resp.getWriter();
    pw.print("<html><body>Hello world! The time is ");
    pw.print(new java.util.Date());
    pw.println("</body></html>");
    pw.close();
  }

}

