package jspstyle;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
* 	A dynamic NameValueModel iterator tag.
*	Outputs no content, but provides scripting
*	variables to calling JSPs.
*/
public class NameValueTag extends BodyTagSupport
{
	/** Model from which we'll obtain our content */
	private NameValueModel 	model;

	/** Our current index in the list */
	private int				index;


	/** Required model attribute/property */
	public void setModel(NameValueModel model) {
		this.model = model;
	}

	public NameValueModel getModel() {
		return model;
	}

    public int doStartTag() throws JspException {
		if (model.getSize() > 0) {
			setVariables();
			return EVAL_BODY_TAG;
		}
		// We'll only get here if the model is empty, and
		// we never want to evaluate this tag's body
		return SKIP_BODY;
     }

	private void setVariables() {
		pageContext.setAttribute("index",  new Integer(index));
		pageContext.setAttribute("value", model.getElementAt(index));
		pageContext.setAttribute("name", model.getName(index));
	}

	/**
	*	Iterate over elements in the model.
	*	Ensure the variables are updated, but write no content.
	*/
	public int doAfterBody() throws JspException {
		if (++index < model.getSize()) {
			setVariables();
			return EVAL_BODY_TAG;
		}
		else
			return SKIP_BODY;
    }

	/**
	*	Output the body content
	*/
	public int doEndTag() throws JspException {
		if (bodyContent != null) {
			try {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			}
			catch (IOException ex) {
				throw new JspException("IO Error: " + ex.getMessage());
			}
		}
		return EVAL_PAGE;
	}

}	// NameValueTag
