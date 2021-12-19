package com.wrox.store.tag;

import java.rmi.RemoteException;

import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * A "remote" version of the standard <jsp:useBean> tag. Given a JNDI
 * name, this tag looks up the remote object bound by the name and
 * inserts a reference to it back into the page under the name supplied
 * by the "id" attribute.
 *
 * @author Simon Brown
 */
public class UseRemoteBeanTag extends TagSupport {

  /** the name of the variable on the page in which to place the references */
  private String id;

  /** the JNDI name that the remote object is bound to */
  private String jndiName;

  /** the fully qualified class name of the remote object */
  private String className;

  /**
   * Called when the starting tag is encountered.
   */
  public int doStartTag() throws JspException {
    try {
      InitialContext ctx = new InitialContext();
      Object remoteBean = null;
      Class remoteClass = Class.forName(className);

      // look up home and keep hold of the reference
      // (checking that it is of the type expected)
      remoteBean = PortableRemoteObject.narrow (ctx.lookup(jndiName), remoteClass);

      pageContext.setAttribute(id, remoteBean);
    } catch (ClassNotFoundException cnfe) {
      throw new JspException(cnfe.getMessage());
    } catch (NamingException ne) {
      throw new JspException(ne.getMessage());
    }

    // and evaluate the body
    return EVAL_BODY_INCLUDE;
  }

  /**
   * Sets the id (name of variable in page).
   *
   * @param s   the new id
   */
  public void setId(String s) {
    this.id = s;
  }

  /**
   * Sets the fully qualified class name.
   *
   * @param s   the new class name
   */
  public void setClassName(String s) {
    this.className = s;
  }

  /**
   * Sets the JNDI name used to lookup the remote object.
   *
   * @param s   the new JNDI string
   */
  public void setJndiName(String s) {
    this.jndiName = s;
  }

}