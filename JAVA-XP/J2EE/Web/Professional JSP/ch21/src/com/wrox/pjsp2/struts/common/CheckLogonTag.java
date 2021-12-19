package com.wrox.pjsp2.struts.common;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.BeanUtils;
import org.apache.struts.util.MessageResources;

/** ****************************************************************************
 * Check for a valid User logged on in the current session.  If there is no
 * such user, forward control to the logon page.
 * (taken from the struts-example: org.apache.struts.example.CheckLogonTag)
 **************************************************************************** */

public final class CheckLogonTag extends TagSupport {

   private String name = Constants.USER_KEY;
   private String page = Constants.LOGON_PAGE;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPage() {
      return (this.page);
   }

   public void setPage(String page) {
      this.page = page;
   }

   /** *************************************************************************
    * Defer our checking until the end of this tag is encountered.
    * @exception JspException if a JSP exception has occurred
    ************************************************************************* */
   public int doStartTag() throws JspException {
      return (SKIP_BODY);
   }

   /** *************************************************************************
    * Perform our logged-in user check by looking for the existence of
    * a session scope bean under the specified name.  If this bean is not
    * present, control is forwarded to the specified logon page.
    * @exception JspException if a JSP exception has occurred
    ************************************************************************* */
   public int doEndTag() throws JspException {

      // Is there a valid user logged on?
      boolean valid = false;
      HttpSession session = pageContext.getSession();
      if((session != null) && (session.getAttribute(name) != null)) {
         valid = true;
      }

      // Forward control based on the results
      if(valid) {
         return (EVAL_PAGE);
      } else {
         try {
            pageContext.forward(page);
         } catch(Exception e) {
            throw new JspException(e.toString());
         }
         return (SKIP_PAGE);
      }

   } //end doEndTag

}
