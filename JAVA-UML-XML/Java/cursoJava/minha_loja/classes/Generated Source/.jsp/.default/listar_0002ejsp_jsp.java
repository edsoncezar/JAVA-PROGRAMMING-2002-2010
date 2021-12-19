import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.jasper.runtime.*;


public class listar_0002ejsp_jsp extends HttpJspBase {


    static {
    }
    public listar_0002ejsp_jsp( ) {
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

            // HTML // begin [file="/listar.jsp";from=(0,29);to=(11,0)]
                out.write("\r\n<html>\r\n<head>\r\n<title>\r\nlistar\r\n</title>\r\n</head>\r\n<body bgcolor=\"#ffffff\">\r\n<h1>\r\nListar JSP\r\n</h1>\r\n");

            // end
            // begin [file="/listar.jsp";from=(11,2);to=(49,0)]
                
                
                    try {
                      Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                    }
                    catch (Exception E) {
                      out.println("Driver nao carregado!");
                    }
                
                    try {
                      Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/loja");
                      PreparedStatement sql = conexao.prepareStatement("select * from produtos order by codigo");
                      ResultSet  resultado = sql.executeQuery();
                      out.println("<table border=1>");
                      out.println("<tr>");
                      out.println("<td>Codigo</td>");
                      out.println("<td>Descricao</td>");
                      out.println("<td>Prateleira</td>");
                      out.println("<td>Preco</td>");
                      out.println("<td>Fabricante</td>");
                      out.println("</tr>");
                      while (resultado.next()){
                        out.println("<tr>");
                        out.println("<td>"+resultado.getString("codigo")+"</td>");
                        out.println("<td>"+ resultado.getString("descricao")+"</td>");
                        out.println("<td>"+ resultado.getString("prateleira")+"</td>");
                        out.println("<td>"+ resultado.getString("preco")+"</td>");
                	out.println("<td>"+ resultado.getString("fabricante")+"</td>");
                        out.println("</tr>");
                      }
                      out.println("</table>");
                
                
                      resultado.close();
                    } catch (SQLException e) {
                      out.println(e);
                    }
                
            // end
            // HTML // begin [file="/listar.jsp";from=(49,2);to=(51,7)]
                out.write("\r\n</body>\r\n</html>");

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
