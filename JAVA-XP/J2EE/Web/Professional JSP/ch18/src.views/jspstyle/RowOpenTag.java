package jspstyle;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
*	Simple BodyTag to configure RowsTag parent.
*/
public class RowOpenTag extends BodyTagSupport
{
	private RowsTag 	parent;

	public int doStartTag() throws JspException {
		parent = (RowsTag) findAncestorWithClass(this, RowsTag.class);
		if (parent == null)
			throw new JspTagException("RowOpenTag must be enclosed in a RowsTag");
		pageContext.setAttribute("row",  new Integer(parent.getRow()));
		return EVAL_BODY_TAG;
	}


	public int doEndTag() throws JspException {
		if (bodyContent != null)
			parent.setRowOpen(bodyContent.getString());
		return EVAL_PAGE;
	}
}
