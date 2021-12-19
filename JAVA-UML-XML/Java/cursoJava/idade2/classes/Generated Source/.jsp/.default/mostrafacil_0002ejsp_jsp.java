import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class mostrafacil_0002ejsp_jsp extends HttpJspBase {

    // begin [file="/mostrafacil.jsp";from=(10,0);to=(10,55)]
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

            // HTML // begin [file="/mostrafacil.jsp";from=(0,0);to=(10,0)]
                out.write("<html>\r\n<head>\r\n<title>\r\nmostrafacil\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1>\r\nMostra fácil a idade\r\n</h1>\r\n");

            // end
            // begin [file="/mostrafacil.jsp";from=(10,0);to=(10,55)]
                calculo.Anos a = null;
                boolean _jspx_speciala  = false;
                 synchronized (pageContext) {
                    a= (calculo.Anos)
                    pageContext.getAttribute("a",PageContext.PAGE_SCOPE);
                    if ( a == null ) {
                        _jspx_speciala = true;
                        try {
                            a = (calculo.Anos) java.beans.Beans.instantiate(this.getClass().getClassLoader(), "calculo.Anos");
                        } catch (ClassNotFoundException exc) {
                             throw new InstantiationException(exc.getMessage());
                        } catch (Exception exc) {
                             throw new ServletException (" Cannot create bean of class "+"calculo.Anos", exc);
                        }
                        pageContext.setAttribute("a", a, PageContext.PAGE_SCOPE);
                    }
                 } 
                if(_jspx_speciala == true) {
            // end
            // begin [file="/mostrafacil.jsp";from=(10,0);to=(10,55)]
                }
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(10,55);to=(11,0)]
                out.write("\r\n");

            // end
            // begin [file="/mostrafacil.jsp";from=(11,0);to=(11,58)]
                JspRuntimeLibrary.introspecthelper(pageContext.findAttribute("a"), "idade", request.getParameter("idade"), request, "idade", false);
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(11,58);to=(13,7)]
                out.write("\r\n\r\nIdade: ");

            // end
            // begin [file="/mostrafacil.jsp";from=(13,7);to=(13,52)]
                out.print(JspRuntimeLibrary.toString((((calculo.Anos)pageContext.findAttribute("a")).getIdade())));
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(13,52);to=(14,9)]
                out.write("<br>\r\nPosição: ");

            // end
            // begin [file="/mostrafacil.jsp";from=(14,9);to=(14,63)]
                out.print(JspRuntimeLibrary.toString((((calculo.Anos)pageContext.findAttribute("a")).getPosicionamento())));
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(14,63);to=(15,14)]
                out.write("<br>\r\nDias Vividos: ");

            // end
            // begin [file="/mostrafacil.jsp";from=(15,14);to=(15,58)]
                out.print(JspRuntimeLibrary.toString((((calculo.Anos)pageContext.findAttribute("a")).getDias())));
            // end
            // HTML // begin [file="/mostrafacil.jsp";from=(15,58);to=(18,0)]
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
