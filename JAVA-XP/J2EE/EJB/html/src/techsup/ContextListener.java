import javax.servlet.*;

public final class ContextListener
  implements ServletContextListener {
  private ServletContext context = null;

  public void contextInitialized(ServletContextEvent event) {
    context = event.getServletContext();
   
    try {
      context.setAttribute("techSupDB", new TechSupDB());
    } catch (Exception ex) {
      context.log("Nao pode criar o objeto de base de dados de pedidos: " + ex.getMessage());
    }
  }
    
  public void contextDestroyed(ServletContextEvent event) {
    context = event.getServletContext();
    TechSupDB techSupDB = (TechSupDB)context.getAttribute("techSupDB");
    techSupDB.remove();
    context.removeAttribute("techSupDB");
  }
}
