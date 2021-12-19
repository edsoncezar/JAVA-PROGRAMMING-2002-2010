package tagext;

import java.io.IOException;
import java.util.Date;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
*    Implementation of a tag to generate a single piece of HTML.
*    @author    Rod Johnson
*/
public class HelloTag extends TagSupport {

    /**
    *    This method will be called when the JSP engine encounters the start
    *    of a tag implemented by this class
    */
    public int doStartTag() throws JspTagException {
        // This return value means that the JSP engine should evaluate
        // the contents and any child tags of this tag
        return EVAL_BODY_INCLUDE;
    }

    /**
    *    This method will be called when the JSP engine encounters the end
    *    of a tag implemented by this class
    */
    public int doEndTag() throws JspTagException {
        String dateString = new Date().toString();
        try {
            pageContext.getOut().write("Hello world.<br/>");
            pageContext.getOut().write("My name is " + getClass().getName() +
                                        " and it's " + dateString + "<p/>");
        }
        catch (IOException ex) {
            throw new JspTagException("Fatal error: hello tag could not write to JSP out");
        }

        // This return value means that the JSP engine should continue to
        // evaluate the rest of this page
        return EVAL_PAGE;
    }
}    // class HelloTag
