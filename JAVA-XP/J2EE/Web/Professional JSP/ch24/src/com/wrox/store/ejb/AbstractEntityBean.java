package com.wrox.store.ejb;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

/**
 * A useful superclass for entity beans, supplying a default
 * implementation of the lifecycle methods.
 *
 * @author    Simon Brown
 */
public abstract class AbstractEntityBean implements EntityBean {

  /** a reference to the context provided by the container */
  protected EntityContext entityContext;

  /**
   * Sets the context.
   *
   * @param ctx   an EntityContext instance
   */
  public void setEntityContext(EntityContext ctx) {
    this.entityContext = ctx;
  }

  /**
   * Unsets the context.
   */
  public void unsetEntityContext() {
    this.entityContext = null;
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
   * Called after the bean has been loaded from the database.
   */
  public void ejbLoad() {
    // do nothing
  }

  /**
   * Called just before the bean is about to be stored in the database.
   */
  public void ejbStore() {
    // do nothing
  }

  /**
   * Called to indicate that this bean has been removed.
   */
  public void ejbRemove() {
    // do nothing
  }

}