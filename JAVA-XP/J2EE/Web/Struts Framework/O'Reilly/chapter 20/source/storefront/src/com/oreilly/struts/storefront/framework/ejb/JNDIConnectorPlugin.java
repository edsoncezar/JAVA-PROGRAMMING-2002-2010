package com.oreilly.struts.storefront.framework.ejb;

import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.Context;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ApplicationConfig;
import org.apache.struts.action.PlugIn;
import javax.servlet.ServletException;

public class JNDIConnectorPlugin {
  private String jndiFactoryClass;
  private String jdniURL;
  private Context initCtx = null;

  public JNDIConnectorPlugin() {
  }

  public void init(ActionServlet servlet, ApplicationConfig config)
    throws ServletException{
      // Get the host and port where the JNDI service is running
      jndiFactoryClass = servlet.getInitParameter("jndiFactoryClass");
      jdniURL = servlet.getInitParameter("jndiURL");

      try{
        Hashtable props = new Hashtable();
        // The EJB spec also allows these to be read from jndi.properties file
        props.put( Context.INITIAL_CONTEXT_FACTORY, jndiFactoryClass );
        props.put( Context.PROVIDER_URL, jdniURL );
        initCtx = new InitialContext(props);
      }catch( Exception ex ){
        throw new ServletException( ex );
      }
      // Store the JNDI Context into the ServletContext
      servlet.getServletContext().setAttribute( "", initCtx );
    }

  public void destroy(){
    try{
      if ( initCtx != null ){
        initCtx.close();
        initCtx = null;
      }
    }catch( Exception ex ){
      ex.printStackTrace();
    }
  }
}