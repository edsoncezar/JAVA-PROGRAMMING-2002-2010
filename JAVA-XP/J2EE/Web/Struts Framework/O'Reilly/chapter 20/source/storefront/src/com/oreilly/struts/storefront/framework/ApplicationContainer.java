package com.oreilly.struts.storefront.framework;
import java.util.Locale;

/**
 * Used by the web container to store information that is needed and used
 * by all users.
 */
public class ApplicationContainer {
  
  /**
   * Retrieve the locale for the system.
   */
  public Locale getSystemLocale() {
    return systemLocale;
  }
  
  /**
   * Set the locale for the system.
   */
  public void setSystemLocale(Locale aLocale) {
    systemLocale = aLocale;
  }
  
  /**
   * Constructor
   */
  public ApplicationContainer() {
    super();
  }
  
  /**
   * The default locale for the application.
   */
  private Locale systemLocale = null;
}
