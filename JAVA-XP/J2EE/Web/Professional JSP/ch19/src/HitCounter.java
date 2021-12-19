import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HitCounter extends HttpServlet {
  int hits = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {

    PrintWriter out = res.getWriter();
    res.setContentType("text/html");
    hits++;

    out.println("<html><head><title>Run command</title></head>");
    out.println("<body><h1>Number of hits: ");
    out.println(hits);
    out.println("</h1></body></html>");
  }
}
