package com.treelight.krnl;

import java.util.Properties;

/**
 * Maintains display policies for each of the link types.
 * Display policies include:
 * <ul>
 *  <li>Show link
 *  <li>Hide link
 *  <li>Show content
 * </ul>
 * <p>
 * Link types include
 * <ul>
 *  <li>reference
 *  <li>comment
 *  <li>___other (tbd)___
 * </ul>
 *
 * @version 0.1
 * @author Eric Armstrong
 * @see ../NodesAndLists.html
 */
public class LinkDisplayPolicy {
  // A table of name/value pairs to hold the policies
  static Properties policies; 

  // Initialize the policy table
  static LinkDisplayPolicy {
    policies = new Properties();
    policies.setProperty(COMMENT, SHOW_LINK);
    policies.setProperty(REFERENCE, SHOW_LINK);
    policies.setProperty(QUOTATION, TARGET_CONTENT);
  }

  /**
   * Determine the proper course of action based on the
   * link's type. If the link type has not been regestered,
   * default to SHOW_LINK. If the policy is SHOW_CONTENT,
   * throw an exception, because the mechanism for doing
   * that is link-dependent. If the policy is HIDE_LINK,
   * return a null string. Otherwise, return the link's
   * getLinkText() value.
   */
  public static linkContent(AbstractLink link) {
    String linkType = link.getLinkType();
    String policy = policies.getProperty(linkType);
    if (policy == null) policy = SHOW_LINK;
    if ("SHOW_LINK".equals(policy)) return link.getLinkText();
    if ("HIDE_LINK".equals(policy)) return "";
    throw new LinkDisplayException();
    //return ""; //--compiler needs this ????
  }

}//LinkDisplayPolicy