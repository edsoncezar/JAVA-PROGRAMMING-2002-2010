import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class jsp1_0002ejsp_jsp extends HttpJspBase {


    static {
    }
    public jsp1_0002ejsp_jsp( ) {
    }

    private static boolean _jspx_inited = false;

    public final void _jspx_init() throws org.apache.jasper.runtime.JspException {
    }

    public void _jspService(HttpServletRequest request, HttpServletResponse  response)
        throws java.io.IOException, ServletException {

        JspFactory _jspxFactory = null;
        PageContext pageContext = null;
        HttpSession session = null;
        ServletContext application = null;
        ServletConfig config = null;
        JspWriter out = null;
        Object page = this;
        String  _value = null;
        try {

            if (_jspx_inited == false) {
                synchronized (this) {
                    if (_jspx_inited == false) {
                        _jspx_init();
                        _jspx_inited = true;
                    }
                }
            }
            _jspxFactory = JspFactory.getDefaultFactory();
            response.setContentType("text/html;ISO-8859-1");
            pageContext = _jspxFactory.getPageContext(this, request, response,
            			"", true, 8192, true);

            application = pageContext.getServletContext();
            config = pageContext.getServletConfig();
            session = pageContext.getSession();
            out = pageContext.getOut();

            // HTML // begin [file="/jsp1.jsp";from=(0,30);to=(9,0)]
                out.write("\r\n<html>\r\n<head>\r\n<title>\r\njsp1\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1>JSP Dinamico</h1>\r\n");

            // end
            // begin [file="/jsp1.jsp";from=(9,2);to=(15,0)]
                
                Date d=new Date();
                out.println(d);
                int x=0;
                x++;
                out.println(x);
            // end
            // HTML // begin [file="/jsp1.jsp";from=(15,2);to=(16,0)]
                out.write("\r\n");

            // end
            // begin [file="/jsp1.jsp";from=(16,3);to=(16,4)]
                out.print(x);
            // end
            // HTML // begin [file="/jsp1.jsp";from=(16,6);to=(19,0)]
                out.write("\r\n</body>\r\n</html>\r\n");

            // end

        } catch (Throwable t) {
            if (out != null && out.getBufferSize() != 0)
                out.clearBuffer();
            if (pageContext != null) pageContext.handlePageException(t);
        } finally {
            if (_jspxFactory != null) _jspxFactory.releasePageContext(pageContext);
        }
    }
}
