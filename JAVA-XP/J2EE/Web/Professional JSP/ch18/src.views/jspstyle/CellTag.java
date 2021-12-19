package jspstyle;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.table.TableModel;

/**
* 	Class to handle each cell in a table. Will be called
*	once for each row: must handle iteration for each column,
*	and output row open and row close values where necessary.
*	Mixes markup output (with values coming from parent tag)
*	with output from evaluating its body content, hence is
*	implemented using a StringBuffer to build up content.
*/
public class CellTag extends BodyTagSupport
{
	/**
	*	Column we're presently working on.
	*	We'll create a scripting variable for this.
	*/
	private int				col;

	/** Buffer in which we'll save up content */
	private StringBuffer	sbOut = new StringBuffer();

	/** Enclosing tag */
	private	RowsTag 		parent;


    public int doStartTag() throws JspException {
		parent = (RowsTag) TagSupport.findAncestorWithClass(this, RowsTag.class);
		if (parent == null)
			throw new JspTagException("CellTag must be enclosed in a RowsTag");
		sbOut.append(parent.getRowOpen());
		setVariables();
		return EVAL_BODY_TAG;
     }

	private void setVariables() {
		Object valObj = parent.getModel().getValueAt(parent.getRow(), col);
		if (valObj == null) {
			// Http sessions are implemented using Hashtables rather
			// than the Java 2 HashMap. They don't take kindly to null values.
			valObj = "NULL";
		}
		pageContext.setAttribute("value", valObj);
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
		else {
			// The column is finished
			sbOut.append(parent.getRowClose());
			return SKIP_BODY;
		}
    }

	/**
	*	Output the content in our internal buffer
	*/
	public int doEndTag() throws JspException {
		try {
			if (bodyContent != null)
				bodyContent.getEnclosingWriter().write(sbOut.toString());
		}
		catch (IOException ex) {
			throw new JspException("IO Error: " + ex.getMessage());
		}
		return EVAL_PAGE;
	}

}	// class CellTag
