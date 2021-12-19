package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class index$jsp extends HttpJspBase {


    static {
    }
    public index$jsp( ) {
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

            // HTML // begin [file="/index.jsp";from=(0,0);to=(18,0)]
                out.write("<html>\r\n<head>\r\n<title>\r\nNoticias\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffc0\">\r\n<h1>Controle de Artigos</h1>\r\n<font face='tahoma'><a href=\"./incluir.jsp\">Incluir</a><br>\r\n<font face='tahoma'><a href=\"./consultar.jsp\">Consultar</a><br>\r\n<font face='tahoma'><a href=\"./alterar.jsp\">Alterar</a><br>\r\n<font face='tahoma'><a href=\"./buscar.jsp\">Buscar</a><br>\r\n<font face='tahoma'><a href=\"./excluir.jsp\">Excluir</a><br>\r\n<font face='tahoma'><a href=\"./listar\">Listar</a><br>\r\n<font face='tahoma'><a href=\"./listar.jsp\">Listar JSP</a><br>\r\n<font face='tahoma'><a href=\"./listar2.jsp\">Listar 2 JSP</a>\r\n</body>\r\n</html>\r\n");

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
