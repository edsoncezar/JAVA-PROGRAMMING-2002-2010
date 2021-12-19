package org.jboss.docs.jaas.howto;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.jboss.security.auth.callback.UsernamePasswordHandler;

/** An example filter that does a JAAS login on the way in and logs out
 * on completion of the request.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revison:$
 */
public class JaasLoginFilter implements Filter
{
   private String configName = "client-login";
   private String username;
   private char[] password;
   private UsernamePasswordHandler handler;

   public void init(FilterConfig config)
      throws ServletException
   {
      configName = config.getInitParameter("configName");
      username = config.getInitParameter("username");
      String x = config.getInitParameter("password");
      if( x != null )
         password = x.toCharArray();
      handler = new UsernamePasswordHandler(username, password);
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException
   {
      LoginContext lc = null;
      try
      {
         System.out.println("JaasLoginFilter, login as: "+username);
         lc = new LoginContext(configName, handler);
         lc.login();
      }
      catch(LoginException e)
      {
         throw new ServletException("Failed to perform JAAS login", e);
      }

      try
      {
         chain.doFilter(request, response);
      }
      finally
      {
         if( lc != null )
         {
            try
            {
               System.out.println("JaasLoginFilter, logout");
               lc.logout();
            }
            catch(LoginException e)
            {
               e.printStackTrace();
            }
         }
      }
   }

   public void destroy()
   {
      username = null;
      if( password != null )
         Arrays.fill(password, '\0');
   }
}
