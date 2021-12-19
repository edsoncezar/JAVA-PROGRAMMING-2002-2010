package org.apache.jsp;

import calculo.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class mostra$jsp extends HttpJspBase {


    static {
    }
    public mostra$jsp( ) {
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

            // HTML // begin [file="/mostra.jsp";from=(0,28);to=(11,0)]
                out.write("\r\n<html>\r\n<head>\r\n<title>\r\nmostra\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1>\r\nMostra Idade\r\n</h1>\r\n");

            // end
            // begin [file="/mostra.jsp";from=(11,2);to=(14,0)]
                
                Anos a=new Anos();
                a.setIdade(request.getParameter("idade"));
            // end
            // HTML // begin [file="/mostra.jsp";from=(14,2);to=(15,7)]
                out.write("\r\nIdade: ");

            // end
            // begin [file="/mostra.jsp";from=(15,10);to=(15,23)]
                out.print(a.getIdade() );
            // end
            // HTML // begin [file="/mostra.jsp";from=(15,25);to=(16,9)]
                out.write("<br>\r\nPosição: ");

            // end
            // begin [file="/mostra.jsp";from=(16,12);to=(16,33)]
                out.print(a.getPosicionamento());
            // end
            // HTML // begin [file="/mostra.jsp";from=(16,35);to=(17,14)]
                out.write(" <br>\r\nDias Vividos: ");

            // end
            // begin [file="/mostra.jsp";from=(17,17);to=(17,28)]
                out.print(a.getDias());
            // end
            // HTML // begin [file="/mostra.jsp";from=(17,30);to=(21,0)]
                out.write("\r\n\r\n</body>\r\n</html>\r\n");

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
