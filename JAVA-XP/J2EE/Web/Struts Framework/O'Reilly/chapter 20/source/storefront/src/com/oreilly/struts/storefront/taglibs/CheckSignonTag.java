package com.oreilly.struts.storefront.taglibs;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.util.IConstants;

/**
 * Similar to the Struts example tag, except that the location that we need
 * to check is nested within a session bean object. If there is no
 * such userView, forward control to the logon page.
 */
public final class CheckSignonTag extends TagSupport {
  
  /**
   * Release any acquired resources.
   */
  public void release() {
    super.release();
    this.page = "/action/signin";
  }
  
  /**
   * Return the forward page.
   */
  public String getPage() {
    return (this.page);
  }
  
  /**
   * Set the forward page.
   *
   * @param page The new forward page
   */
  public void setPage(String page) {
    this.page = page;
  }
  
  /**
   * Defer our checking until the end of this tag is encountered.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag()
    throws JspException {
    return (SKIP_BODY);
  }
  
  /**
   * Perform our logged-in user check by looking for the existence of
   * a userView nested inside of the UserContainer object in the session
   * scope bean.  If this bean is not present, control is forwarded to the
   * specified signin page.
   *
   * @exception JspException if a JSP exception has occurred
   */
  public int doEndTag()
    throws JspException {
    
    // Default that the user is not logged on.
    boolean valid = false;
    HttpSession session = pageContext.getSession();
    if(session != null) {
      UserContainer userContainer = (UserContainer)session.getAttribute(IConstants.USER_CONTAINER_KEY);
      if(userContainer != null && userContainer.getUserView() != null) {
        valid = true;
      }
    }
    
    // Forward control based on the results
    if(valid) {
      return (EVAL_PAGE);
    }
    else {
      try {
        pageContext.forward(page);
      }
      catch(Exception e) {
        throw new JspException(e.toString());
      }
      return (SKIP_PAGE);
    }
  }
  
  /**
   * The page to which we should forward for the user to log on.
   */
  private String page = "/action/signin";
}
