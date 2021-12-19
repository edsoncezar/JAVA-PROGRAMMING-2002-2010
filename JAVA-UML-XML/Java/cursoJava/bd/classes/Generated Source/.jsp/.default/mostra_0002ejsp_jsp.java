import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class mostra_0002ejsp_jsp extends HttpJspBase {

    // begin [file="/mostra.jsp";from=(10,0);to=(10,55)]
    // end

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

            // HTML // begin [file="/mostra.jsp";from=(0,0);to=(10,0)]
                out.write("<html>\r\n<head>\r\n<title>\r\nmostra\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1>\r\nInforme o código<br>\r\n</h1>\r\n");

            // end
            // begin [file="/mostra.jsp";from=(10,0);to=(10,55)]
                bd.Consulta id = null;
                boolean _jspx_specialid  = false;
                 synchronized (pageContext) {
                    id= (bd.Consulta)
                    pageContext.getAttribute("id",PageContext.PAGE_SCOPE);
                    if ( id == null ) {
                        _jspx_specialid = true;
                        try {
                            id = (bd.Consulta) java.beans.Beans.instantiate(this.getClass().getClassLoader(), "bd.Consulta");
                        } catch (ClassNotFoundException exc) {
                             throw new InstantiationException(exc.getMessage());
                        } catch (Exception exc) {
                             throw new ServletException (" Cannot create bean of class "+"bd.Consulta", exc);
                        }
                        pageContext.setAttribute("id", id, PageContext.PAGE_SCOPE);
                    }
                 } 
                if(_jspx_specialid == true) {
            // end
            // begin [file="/mostra.jsp";from=(10,0);to=(10,55)]
                }
            // end
            // HTML // begin [file="/mostra.jsp";from=(10,55);to=(11,0)]
                out.write("\r\n");

            // end
            // begin [file="/mostra.jsp";from=(11,0);to=(11,61)]
                JspRuntimeLibrary.introspecthelper(pageContext.findAttribute("id"), "codigo", request.getParameter("codigo"), request, "codigo", false);
            // end
            // HTML // begin [file="/mostra.jsp";from=(11,61);to=(12,8)]
                out.write("\r\nCódigo: ");

            // end
            // begin [file="/mostra.jsp";from=(12,8);to=(12,55)]
                out.print(JspRuntimeLibrary.toString((((bd.Consulta)pageContext.findAttribute("id")).getCodigo())));
            // end
            // HTML // begin [file="/mostra.jsp";from=(12,55);to=(13,9)]
                out.write("<br>\r\nAssunto: ");

            // end
            // begin [file="/mostra.jsp";from=(13,9);to=(13,57)]
                out.print(JspRuntimeLibrary.toString((((bd.Consulta)pageContext.findAttribute("id")).getAssunto())));
            // end
            // HTML // begin [file="/mostra.jsp";from=(13,57);to=(14,10)]
                out.write("<br>\r\nConteúdo: ");

            // end
            // begin [file="/mostra.jsp";from=(14,10);to=(14,59)]
                out.print(JspRuntimeLibrary.toString((((bd.Consulta)pageContext.findAttribute("id")).getConteudo())));
            // end
            // HTML // begin [file="/mostra.jsp";from=(14,59);to=(15,12)]
                out.write("<br>\r\nPrioridade: ");

            // end
            // begin [file="/mostra.jsp";from=(15,12);to=(15,63)]
                out.print(JspRuntimeLibrary.toString((((bd.Consulta)pageContext.findAttribute("id")).getPrioridade())));
            // end
            // HTML // begin [file="/mostra.jsp";from=(15,63);to=(19,0)]
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
