package org.apache.jsp;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class listar2$jsp extends HttpJspBase {


    static {
    }
    public listar2$jsp( ) {
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

            // HTML // begin [file="/listar2.jsp";from=(0,29);to=(10,0)]
                out.write("\r\n<html>\r\n<head>\r\n<title>\r\nlistar\r\n</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"></head>\r\n<body bgcolor=\"#66FFCC\">\r\n<h1 align=\"center\"> Listar </h1>\r\n\r\n");

            // end
            // begin [file="/listar2.jsp";from=(10,2);to=(23,0)]
                
                    //====================== LISTAR =========================
                    try {
                          Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                    }
                    catch (Exception E) {
                       out.println("Driver nao carregado!");
                    }
                
                    try {
                        Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/noticias");
                        PreparedStatement sql = conexao.prepareStatement("select * from artigos order by assunto ");
                        ResultSet  resultado = sql.executeQuery();
            // end
            // HTML // begin [file="/listar2.jsp";from=(23,2);to=(32,0)]
                out.write("\r\n\r\n       <table border=1 cellpadding=\"1\" cellspacing=\"0\" bordercolor=\"#000000\">\r\n       <tr>\r\n          <td>codigo</td>\r\n          <td>assunto</td>\r\n          <td>conteudo</td>\r\n          <td>prioridade</td>\r\n        </tr>\r\n");

            // end
            // begin [file="/listar2.jsp";from=(32,2);to=(32,34)]
                      while (resultado.next()){ 
            // end
            // HTML // begin [file="/listar2.jsp";from=(32,36);to=(34,16)]
                out.write("\r\n          <tr>\r\n            <td>");

            // end
            // begin [file="/listar2.jsp";from=(34,19);to=(34,48)]
                out.print(resultado.getString("codigo"));
            // end
            // HTML // begin [file="/listar2.jsp";from=(34,50);to=(35,16)]
                out.write("</td>\r\n            <td>");

            // end
            // begin [file="/listar2.jsp";from=(35,19);to=(35,49)]
                out.print(resultado.getString("assunto"));
            // end
            // HTML // begin [file="/listar2.jsp";from=(35,51);to=(36,16)]
                out.write("</td>\r\n            <td>");

            // end
            // begin [file="/listar2.jsp";from=(36,19);to=(36,50)]
                out.print(resultado.getString("conteudo"));
            // end
            // HTML // begin [file="/listar2.jsp";from=(36,52);to=(37,16)]
                out.write("</td>\r\n            <td>");

            // end
            // begin [file="/listar2.jsp";from=(37,19);to=(37,52)]
                out.print(resultado.getString("prioridade"));
            // end
            // HTML // begin [file="/listar2.jsp";from=(37,54);to=(39,0)]
                out.write("</td>\r\n         </tr>\r\n");

            // end
            // begin [file="/listar2.jsp";from=(39,2);to=(39,11)]
                      }  
            // end
            // HTML // begin [file="/listar2.jsp";from=(39,13);to=(41,0)]
                out.write("\r\n        </table>\r\n");

            // end
            // begin [file="/listar2.jsp";from=(41,2);to=(48,0)]
                
                        resultado.close();
                    } catch (SQLException e) {
                        out.println(e);
                    }
                    //=========================FIM=============================
                
            // end
            // HTML // begin [file="/listar2.jsp";from=(48,2);to=(52,0)]
                out.write("\r\n</body>\r\n</html>\r\n\r\n");

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
