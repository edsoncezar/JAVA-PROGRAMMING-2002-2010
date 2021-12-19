import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.*;
import java.awt.image.*;

import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.dom.DocumentImpl;
import org.xml.sax.InputSource;

import org.apache.batik.util.awt.svg.SVGGraphics2D;

public class g2d_2_svg extends HttpServlet {

  private static final int WIDTH = 480;
  private static final int HEIGHT = 400;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletOutputStream out = response.getOutputStream();
    ServletContext context = this.getServletConfig().getServletContext();
 
 
    try {
      response.setContentType("image/svg+xml");

      // read and parse the file specified by xml parameter
      InputStream xmlStream = context.getResourceAsStream(request.getParameter("xml"));
      DOMParser parser = new DOMParser();
      parser.parse(new InputSource(xmlStream));

      Document doc = parser.getDocument();
      Element root = doc.getDocumentElement();

      // create a proper size Frame object
      HistoryFrame dummy = new HistoryFrame(root);
      dummy.setSize(new Dimension(WIDTH, HEIGHT));
      BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

      // create the SVGGraphics2D object, remember to set the size!
      Document svgDoc = new DocumentImpl();
      SVGGraphics2D g2 = new SVGGraphics2D(svgDoc);
      g2.setSVGCanvasSize(dummy.getSize());

      // draw the graph onto the image
      dummy.paint(g2);

      Writer writer = new java.io.StringWriter();
      g2.stream(writer, false);
      out.print(writer.toString());
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
