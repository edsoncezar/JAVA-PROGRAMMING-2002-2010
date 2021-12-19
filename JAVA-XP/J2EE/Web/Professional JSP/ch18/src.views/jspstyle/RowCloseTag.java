package jspstyle;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
*	Simple BodyTag to configure RowsTag parent.
*/
public class RowCloseTag extends BodyTagSupport
{
	private RowsTag 	parent;

    public int doStartTag() throws JspException {
		parent = (RowsTag) TagSupport.findAncestorWithClass(this, RowsTag.class);
		if (parent == null)
			throw new JspTagException("RowOpenTag must be enclosed in a RowsTag");
		return EVAL_BODY_TAG;
     }

	public int doEndTag() throws JspException {
		if (bodyContent != null)
			parent.setRowClose(bodyContent.getString());
		return EVAL_PAGE;
	}
}
