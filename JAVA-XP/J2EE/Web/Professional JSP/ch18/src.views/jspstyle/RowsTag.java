package jspstyle;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.table.TableModel;

/**
* 	Tag to handle one row in a table. Iterates over its content
*	for each row in the table. Exposes rowOpen and rowClose properties
*	in the same way as TableTag exposes headerOpen and headerClose,
*	to allow subtags to communicate.
*/
public class RowsTag extends BodyTagSupport
{
	private static final String	DEFAULT_ROW_OPEN = "<tr>";
	private static final String	DEFAULT_ROW_CLOSE = "</tr>";

	private TableModel 	model;
	private int			row;
	private String		rowOpen = DEFAULT_ROW_OPEN;
	private String		rowClose = DEFAULT_ROW_CLOSE;

    public int doStartTag() throws JspException {
		TableTag parent = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);
		if (parent == null)
			throw new JspTagException("Rows tag must be enclosed in a Table tag");
		model = parent.getModel();
		setVariables();
		return EVAL_BODY_TAG;
     }

	/** Make the TableModel available to subtags in this package */
	TableModel getModel() {
		return model;
	}

	String getRowOpen() {
		return rowOpen;
	}

	String getRowClose() {
		return rowClose;
	}

	void setRowOpen(String rowOpen) {
		this.rowOpen = rowOpen;
	}

	void setRowClose(String rowClose) {
		this.rowClose = rowClose;
	}

	private void setVariables() {
		pageContext.setAttribute("row",  new Integer(row));
	}

	public int getRow() {
		return row;
	}


	/**
	*	Iterate over this tag's body content and subtags for each
	*	row in the TableModel.
	*/
	public int doAfterBody() throws JspException {
		if (++row < model.getRowCount()) {
			setVariables();
			return EVAL_BODY_TAG;
		}
		else
			return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
		try {
			if(bodyContent != null)
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
		}
		catch (Exception ex) {
			throw new JspException("IO Error: " + ex.getMessage());
		}
		return EVAL_PAGE;
	}

}	// RowsTag
