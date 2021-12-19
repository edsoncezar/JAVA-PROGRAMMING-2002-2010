package com.wrox.store.tag;

import javax.servlet.jsp.tagext.*;

/**
 * Extra info class for the use remote bean tag.
 *
 * @author Simon Brown
 */
public class UseRemoteBeanTagExtraInfo extends TagExtraInfo {

  /**
   * Gets details of the variables that will be placed
   * directly into the page by the tag.
   *
   * @param data    information on the associated tag
   * @return  an array of VariableInfo objects
   */
  public VariableInfo[] getVariableInfo(TagData data) {
    return new VariableInfo[]
      {
        new VariableInfo(
          data.getId(),
          data.getAttributeString("className"),
          true,
          VariableInfo.AT_END)
      };
  }

}