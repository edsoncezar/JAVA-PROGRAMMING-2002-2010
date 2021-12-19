package jspstyle;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.table.TableModel;

/**
* 	A dynamic table tag. This tag produces no output:
*	it's purpose is to expose the TableModel to subtags, and
*	store markup fragments for subtags.
*	The headerOpen and headerClose properties are meant for
*	subtags to use, although they can be set using attributes
*	of this tag.
*/
public class TableTag extends TagSupport
{
	private static final String	DEFAULT_HEADER_OPEN = "<tr>";
	private static final String	DEFAULT_HEADER_CLOSE = "</tr>";

	private String	headerOpen = DEFAULT_HEADER_OPEN;
	private String	headerClose = DEFAULT_HEADER_CLOSE;

	private TableModel model;


	public void setModel(TableModel model) {
		this.model = model;
	}

	/**
	*	Package-visible, for child tags
	*/
	TableModel getModel() {
		return model;
	}

	public String getHeaderOpen() {
		return headerOpen;
	}

	public void setHeaderOpen(String headerOpen) {
		this.headerOpen = headerOpen;
	}

	public String getHeaderClose() {
		return headerClose;
	}

	public void setHeaderClose(String headerClose) {
		this.headerClose = headerClose;
	}


    public int doStartTag() throws JspException {
		if (model == null)
			throw new JspException("Model must not be null");
		if (model.getRowCount() > 0) {
			// Only evaluate body if there's data in the model
			return EVAL_BODY_INCLUDE;
		}
		else
		  	return SKIP_BODY;
     }

}	// class TableTag
