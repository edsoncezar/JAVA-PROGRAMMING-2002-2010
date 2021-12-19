package jspstyle;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.table.TableModel;

/**
*	Tag to iterate over headings.
*/
public class HeadingTag extends BodyTagSupport
{
	private TableTag 		parent;
	private StringBuffer	sbOut = new StringBuffer();

	private int				col;

    public int doStartTag() throws JspException {
		parent = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);
		if (parent == null)
			throw new JspTagException("HeadingTag must be enclosed in a TableTag");
		sbOut.append(parent.getHeaderOpen());
		setVariables();
		return EVAL_BODY_TAG;
     }

	private void setVariables() {
		pageContext.setAttribute("heading", parent.getModel().getColumnName(col));
		pageContext.setAttribute("column", new Integer(col));
	}

	public int doAfterBody() throws JspException {
		String bodyTag = bodyContent.getString();
		sbOut.append(bodyTag);
		bodyContent.clearBody();
		if (++col < parent.getModel().getColumnCount()) {
			setVariables();
			return EVAL_BODY_TAG;
		}
		else
			return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
		try {
			if(bodyContent != null) {
				bodyContent.getEnclosingWriter().write(sbOut.toString());
				bodyContent.getEnclosingWriter().write(parent.getHeaderClose());
			}
		}
		catch (Exception ex) {
			throw new JspException("IO Error: " + ex.getMessage());
		}
		return EVAL_PAGE;
	}
}
