package com.treelight.krnl;

/**
 * Abstract template for a class that defines a link.
 *
 * @version 0.1
 * @author Eric Armstrong
 * @see ../NodesAndLists.html
 */
abstract public class AbstractLink extends ContentNode {
  static final String nodetype = LINK;

  //To be defined by subclasses
  private String targetType;
  public String getTargetType { return targetType; }

  //Defined when the node is constructed
  private String linktype;
  public String getLinkType { return linkType; }

  /**
   * The system's policy object maintains a list of link types
   * and the display policy for each (show link, hide link, or show content).
   * The policy object returns a null string or the results of calling
   * {@link #getLinkText} for hide link and show link. For show content,
   * it throws an exception to tell the system  
   */
  public String getContent() 
  throws LinkDisplayException {
    return LinkDisplayPolicy.linkContent(this);
  }

  /**
   * Returns a string representation of the link. 
   */
  abstract public String getLinkText() { }

  /** 
   * Construct a node, specifying the kind of node it is.
   * (In XML, the specification would be the element name.)
   */
  public AbstractLink(String linkType) {
    this.linkType = linkType;
  }

}//AbstractLink