package paulojeronimo.servlet;

import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Paulo Jerônimo
 * @todo Implementar novidades nesta classe!
 *
 * @version 1.0
 * @web.servlet name="BasicServlet" 
 * 				display-name="Basic Servlet" 
 * 				load-on-startup="1"
 * 
 * @web.servlet-init-param 	name="hi" 
 * 							value="${basic.servlet.hi}"
 * 
 * @web.servlet-init-param 	name="bye" 
 * 							value="${basic.servlet.bye}"
 *
 * @web.resource-ref description="JDBC resource"
 *                   name="${datasource.jndi-name}"
 *                   type="javax.sql.DataSource"
 *                   auth="Container"
 * 
 * @web.servlet-mapping url-pattern="/Basic/*"
 * @web.servlet-mapping url-pattern="*.Basic"
 * @web.servlet-mapping url-pattern="/BasicServlet"
 */
public class BasicServlet extends HttpServlet {
    
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) 
        throws ServletException {

        super.init(config);
    }
    
    /** Destroys the servlet.
     */
    public void destroy() { }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(
        HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        ServletConfig config = this.getServletConfig();
        String hi = config.getInitParameter("hi");
        String bye = config.getInitParameter("bye");
        
        try {
            response.setContentType("text/html");
            java.io.PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Basic Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> inicio: " + hi + "</h1>");
            getJdbcPool(out);
            out.println("<h1> fim: " + bye + "</h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "XDoc Rules";
    }
   
    /**
     * @todo Obter o nome do contexto a partir da propriedade do ANT e não deixá-la fixa como está.
     */
    private void getJdbcPool(java.io.PrintWriter out)
        throws Exception{

        out.println("<br/>");
        Context context = new InitialContext();
        DataSource pool = (DataSource) context.lookup("java:/DefaultDS");
        if (pool == null) 
            return;
        Connection connection = pool.getConnection();
        out.println("<table border=\"1\">");
        out.println("<tr><th>Tabelas do DataSource configurado:</th></tr>");
        try {
            ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
            while (rs.next()) {
                out.println("<tr><td>");
                out.println(rs.getString("TABLE_NAME"));
                out.println("</td></tr>");
            }
        } finally {
            connection.close();
        }
        out.println("</table>");
        out.println("<br/>");
    }
}
