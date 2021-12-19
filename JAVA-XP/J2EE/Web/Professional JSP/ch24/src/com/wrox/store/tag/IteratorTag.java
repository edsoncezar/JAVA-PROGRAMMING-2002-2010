package com.wrox.store.tag;

import java.util.Iterator;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Given an iterator, this tag runs over each element and places
 * a reference in the page by the name provided by the id attribute.
 *
 * @author Simon Brown
 */
public class IteratorTag extends TagSupport implements IterationTag {

  /** the name of the variable on the page in which to place the references */
  private String id;

  /** the fully qualified class name of the elements */
  private String className;

  /** the iterator that this tag should use */
  private Iterator iterator;

  /**
   * Called when the starting tag is encountered.
   */
  public int doStartTag() throws JspException {
    if (iterator.hasNext()) {
      // if there are elements, put the first one into the
      // page under the name provided by the "id" attribute
      pageContext.setAttribute(id, iterator.next());

      // and include the body
      return EVAL_BODY_INCLUDE;
    } else {
      // there are no elements so skip the body
      return SKIP_BODY;
    }
  }

  /**
   * Called after the body has been included.
   */
  public int doAfterBody() throws JspException {
    if (iterator.hasNext()) {
      // if there are more elements, put the next one into the
      // page under the name provided by the "id" attribute
      pageContext.setAttribute(id, iterator.next());

      // and instruct the JSP engine to re-evaluate the body
      // of this tag
      return EVAL_BODY_AGAIN;
    } else {
      // there are no more elements so skip the body
      return SKIP_BODY;
    }
  }

  /**
   * Sets the id (name of variable in page).
   *
   * @param s   the new id
   */
  public void setId(String s) {
    this.id = s;
  }

  /**
   * Sets the fully qualified class name.
   *
   * @param s   the new class name
   */
  public void setClassName(String s) {
    this.className = s;
  }

  /**
   * Sets the iterator that this tag should use.
   *
   * @param it    the new iterator
   */
  public void setIterator(Iterator it) {
    this.iterator = it;
  }

}