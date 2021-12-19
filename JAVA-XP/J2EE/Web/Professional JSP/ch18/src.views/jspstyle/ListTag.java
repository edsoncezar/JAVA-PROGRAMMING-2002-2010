package jspstyle;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.swing.ListModel;

/**
* 	A dynamic list iterator tag. Outputs no content, but provides scripting
*	variables to calling JSPs.
*	@see jspstyle.ListTagExtraInfo for variable definitions available
*	within this tag.
*	@author Rod Johnson
*/
public class ListTag extends BodyTagSupport
{
	/** ListModel we'll present data from */
	private ListModel 	model;

	/**
	*	Index of our current position in the list.
	*	Incremented with each pass over our body content.
	*/
	private int			index;

	/** Setter for model attribute/property */
	public void setModel(ListModel model) {
		this.model = model;
	}

	/** Getter for model attribute/property */
	public ListModel getModel() {
		return model;
	}

	/**
	*	Make sure that variables are set before doAfterBody()
	*	is called. We could use doInitBody() for the same purpose.
	*/
    public int doStartTag() throws JspException {
		setVariables();
		return EVAL_BODY_TAG;
     }

	/**
	*	Convenience method to set variables before each loop iteration.
	*/
	private void setVariables() {
		pageContext.setAttribute("index",  new Integer(index));
		pageContext.setAttribute("value", model.getElementAt(index));
	}

	/**
	*	Check the index to see whether we need to make another pass
	*	over our body content.
	*	Ensure scripting variables are updated, but write no content.
	*/
	public int doAfterBody() throws JspException {
		if (++index < model.getSize()) {
			// We still have more list entries:
			// make at least one more pass overr body content
			setVariables();
			return EVAL_BODY_TAG;
		}
		else
			// We've finished the list
			return SKIP_BODY;
    }

	/**
	*	Output the body content we have built up so far.
	*/
	public int doEndTag() throws JspException {
		try {
			if (bodyContent != null)
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
		}
		catch (Exception ex) {
			throw new JspTagException("IO Error: " + ex.getMessage());
		}

		return EVAL_PAGE;
	}

}	// ListTag
