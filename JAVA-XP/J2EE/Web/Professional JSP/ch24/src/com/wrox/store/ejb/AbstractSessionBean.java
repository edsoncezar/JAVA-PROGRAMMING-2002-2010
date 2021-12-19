package com.wrox.store.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * A useful superclass for session beans, supplying a default
 * implementation of the lifecycle methods.
 *
 * @author    Simon Brown
 */
public abstract class AbstractSessionBean implements SessionBean {

  /** a reference to the context provided by container */
  protected SessionContext sessionContext;

  /**
   * Sets the context.
   *
   * @param ctx   a SessionContext instance
   */
  public void setSessionContext(SessionContext ctx) {
    this.sessionContext = ctx;
  }

  /**
   * Called just after the bean is re-activated.
   */
  public void ejbActivate() {
    // do nothing
  }

  /**
   * Called when the bean is about to be passivated.
   */
  public void ejbPassivate() {
    // do nothing
  }

  /**
   * Called to indicate that this bean has been created.
   */
  public void ejbCreate() {
    // do nothing
  }

  /**
   * Called to indicate that this bean has been removed.
   */
  public void ejbRemove() {
    // do nothing
  }

}