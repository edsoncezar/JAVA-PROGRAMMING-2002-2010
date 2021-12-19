import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class mostrafacil_0002ejsp_jsp extends HttpJspBase {

    // begin [file="/mostrafacil.jsp";from=(8,0);to=(8,62)]
    // end

    static {
    }
    public mostrafacil_0002ejsp_jsp( ) {
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

            // HTML // begin [file="/mostrafacil.jsp";from=(0,0);to=(8,0)]
                out.write("<html>\r\n<head>\r\n<title>\r\nmostrafacil\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1> Mostra Fácil a Situação</h1>\r\n");

            // end
            // begin [file="/mostrafacil.jsp";from=(8,0);to=(8,62)]
                controle.Avaliacao av = null;
                boolean _jspx_specialav  = false;
                 synchronized (pageContext) {
                    av= (controle.Avaliacao)
                    pageContext.getAttribute("av",PageContext.PAGE_SCOPE);
                    if ( av == null ) {
                        _jspx_specialav = true;
                        try {
                            av = (controle.Avaliacao) java.beans.Beans.instantiate(this.getClass().getClassLoader(), "controle.Avaliacao");
                        } catch (ClassNotFoundException exc) {
                             throw new InstantiationException(exc.getMessage());
                        } catch (Exception exc) {
                             throw new ServletException (" Cannot create bean of class "+"controle.Avaliacao", exc);
                        }
                        pageContext.setAttribute("av", av, PageContext.PAGE_SCOPE);
                    }
                 } 
                if(_jspx_specialav == true) {
            // end
            // begin [file="/mostrafacil.jsp";from=(8,0);to=(8,62)]
                }
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(8,62);to=(9,0)]
                out.write("\r\n");

            // end
            // begin [file="/mostrafacil.jsp";from=(9,0);to=(9,57)]
                JspRuntimeLibrary.introspecthelper(pageContext.findAttribute("av"), "nota", request.getParameter("nota"), request, "nota", false);
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(9,57);to=(11,0)]
                out.write("\r\nNota: \r\n");

            // end
            // begin [file="/mostrafacil.jsp";from=(11,0);to=(11,45)]
                out.print(JspRuntimeLibrary.toString((((controle.Avaliacao)pageContext.findAttribute("av")).getNota())));
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(11,45);to=(14,0)]
                out.write("\r\n<br>\r\nSituação:\r\n");

            // end
            // begin [file="/mostrafacil.jsp";from=(14,0);to=(14,49)]
                out.print(JspRuntimeLibrary.toString((((controle.Avaliacao)pageContext.findAttribute("av")).getSituacao())));
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(14,49);to=(19,0)]
                out.write("\r\n<br>\r\n<p>&nbsp;</p>\r\n</body>\r\n</html>\r\n");

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
