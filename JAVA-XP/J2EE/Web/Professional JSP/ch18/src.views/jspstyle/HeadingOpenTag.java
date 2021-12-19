package jspstyle;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
* 	Tag to set the headerOpen property of an enclosing
*	TableTag to its body content.
*/
public class HeadingOpenTag extends BodyTagSupport
{
	private TableTag 	parent;

    public int doStartTag() throws JspException {
		parent = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);
		if (parent == null)
			throw new JspTagException("HeadingOpenTag must be enclosed in a TableTag");
		return EVAL_BODY_TAG;
     }

	public int doEndTag() throws JspException {
		if (bodyContent != null)
			parent.setHeaderOpen(bodyContent.getString());
		return EVAL_PAGE;
	}
}
