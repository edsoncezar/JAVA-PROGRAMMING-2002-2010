import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RetrieveFile extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    ServletOutputStream out = response.getOutputStream();
    ServletContext context = this.getServletConfig().getServletContext();
 
 
    try {
      // set the content type to the parameter passed.
      response.setContentType(request.getParameter("contentType"));
     
      // read the "file" request parameter.  This should be a file
      // relative to the web application directory.
      String file = request.getParameter("file");
      InputStream istream = context.getResourceAsStream(file);
      BufferedInputStream bis = new BufferedInputStream(istream);
      BufferedOutputStream bos = new BufferedOutputStream(out);
     
      // stream the file back to the client
      byte[] buffer = new byte[4096];
      int size;
      size = bis.read(buffer);
      while (size != -1) {
        bos.write(buffer, 0, size);
        size = bis.read(buffer);
      }
      bis.close();
      bos.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
