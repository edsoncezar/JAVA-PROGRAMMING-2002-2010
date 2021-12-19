package org.jboss.docs.jaas.howto;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Tests of accessing EJBs from a servlet.

 @author  Scott.Stark@jboss.org
 @version $Revision: 1.1 $
 */
public class EJBServlet extends HttpServlet
{

   /** Command dispatcher. It checks the method parameter against:
    ('echo', 'noop', 'restricted') and invokes the corresponding
    callXXX() method to perform the dispatch.
    */
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      String method = request.getParameter("method");

      if (method == null || method.equals("echo"))
         callEcho(request, response);
      else if (method.equals("noop"))
         callNoop(request, response);
      else if (method.equals("restricted"))
         callRestricted(request, response);
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      processRequest(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /** Creates a session bean using the home interface bound to
    the "java:comp/env/ejb/SecuredEJB" ENC path and stores it
    in the current http session under the name 'bean'. The echo()
    method of the bean is called and its result sent back to
    the client.
    */
   private void callEcho(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      String tmp = request.getParameter("private");
      boolean priv = false;
      if (tmp != null)
         priv = Boolean.valueOf(tmp).booleanValue();
      String echoMsg = null;
      Principal user = request.getUserPrincipal();
      try
      {
         HttpSession session = request.getSession();
         Session bean = createBean(session, priv);
         session.setAttribute("bean", bean);
         echoMsg = bean.echo("Hello");
      }
      catch (Exception e)
      {
         throw new ServletException("Failed to call SecuredEJB.echo", e);
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<head><title>EJBServlet</title></head>");
      out.println("<h1>EJBServlet Accessed</h1>");
      out.println("<body><pre>You have accessed this servlet as user: " + user);
      out.println("The SecuredEJB.echo('Hello') returned: " + echoMsg);
      out.println("</pre></body></html>");
      out.close();
   }

   /** Invoke the noop() method on the session bean stored in the
    current http session
    */
   private void callNoop(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      Principal user = request.getUserPrincipal();
      try
      {
         HttpSession session = request.getSession();
         Session bean = (Session) session.getAttribute("bean");
         if (bean == null)
         {
            bean = createBean(session, false);
            //throw new IllegalStateException("The echo method must be called first to create the session bean");
         }
         /* There can only be one client thread accessing a stateless
           at a time so synchronize on the session bean
         */
         synchronized (bean)
         {
            bean.noop();
         }
      }
      catch (Throwable e)
      {
         throw new ServletException("Failed to call SecuredEJB.noop", e);
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<head><title>EJBServlet</title></head>");
      out.println("<h1>EJBServlet Accessed</h1>");
      out.println("<body><pre>You have accessed this servlet as user: " + user);
      out.println("The SecuredEJB.noop() was successfully called");
      out.println("</pre></body></html>");
      out.close();
   }

   /** Invoke the restricted() method on the session bean stored in the
    current http session
    */
   private void callRestricted(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      Principal user = request.getUserPrincipal();
      try
      {
         HttpSession session = request.getSession();
         Session bean = (Session) session.getAttribute("bean");
         if (bean == null)
            throw new IllegalStateException("The echo method must be called first to create the session bean");
         /* There can only be one client thread accessing a stateless
           at a time so synchronize on the session bean
         */
         synchronized (bean)
         {
            bean.restricted();
         }
      }
      catch (Throwable e)
      {
         throw new ServletException("Failed to call SecuredEJB.restricted", e);
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<head><title>EJBServlet</title></head>");
      out.println("<h1>EJBServlet Accessed</h1>");
      out.println("<body><pre>You have accessed this servlet as user: " + user);
      out.println("The SecuredEJB.restricted() was successfully called");
      out.println("</pre></body></html>");
      out.close();
   }

   private Session createBean(HttpSession session, boolean priv)
      throws ServletException
   {
      Session bean = null;
      try
      {
         InitialContext ctx = new InitialContext();
         String jndiName = "java:comp/env/ejb/SecuredEJB";
         if (priv == true)
            jndiName = "java:comp/env/ejb/PrivateEJB";
         SessionHome home = (SessionHome) ctx.lookup(jndiName);
         bean = home.create();
         session.setAttribute("bean", bean);
      }
      catch (Exception e)
      {
         throw new ServletException("Failed to call SecuredEJB.echo", e);
      }
      return bean;
   }
}
