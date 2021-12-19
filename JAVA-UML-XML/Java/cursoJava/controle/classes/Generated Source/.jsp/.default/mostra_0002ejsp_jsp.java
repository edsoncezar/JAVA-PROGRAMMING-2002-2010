import controle.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class mostra_0002ejsp_jsp extends HttpJspBase {


    static {
    }
    public mostra_0002ejsp_jsp( ) {
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

            // HTML // begin [file="/mostra.jsp";from=(0,29);to=(11,0)]
                out.write("\r\n<html>\r\n<head>\r\n<title>\r\nmostra\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1>\r\nMostra Situacao\r\n</h1>\r\n");

            // end
            // begin [file="/mostra.jsp";from=(11,2);to=(14,0)]
                
                Avaliacao av=new Avaliacao();
                av.setNota(request.getParameter("nota"));
            // end
            // HTML // begin [file="/mostra.jsp";from=(14,2);to=(16,6)]
                out.write("\r\n<h1>\r\nNota: ");

            // end
            // begin [file="/mostra.jsp";from=(16,9);to=(16,22)]
                out.print(av.getNota() );
            // end
            // HTML // begin [file="/mostra.jsp";from=(16,24);to=(17,16)]
                out.write("<br>\r\nSituacão Final: ");

            // end
            // begin [file="/mostra.jsp";from=(17,19);to=(17,35)]
                out.print(av.getSituacao());
            // end
            // HTML // begin [file="/mostra.jsp";from=(17,37);to=(21,0)]
                out.write(" <br>\r\n</h1>\r\n</body>\r\n</html>\r\n");

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
