package com.oreilly.struts.storefront.framework.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.taglib.html.ImgTag;
import org.apache.struts.util.ResponseUtils;

public class ExtendedImg extends ImgTag{


 public int doEndTag() throws JspException {

  // Generate the name definition or image element
  HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
  HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
  StringBuffer results = new StringBuffer("<img");
  String tmp = src();
  String srcurl = url(tmp);
  if (srcurl != null) {
    results.append(" src=\"");
    results.append(response.encodeURL(srcurl));
    results.append("\"");
  }
  String lowsrcurl = url(this.lowsrc);
  if (lowsrcurl != null) {
    results.append(" lowsrc=\"");
    results.append(response.encodeURL(lowsrcurl));
    results.append("\"");
  }
  if (imageName != null) {
    results.append(" name=\"");
    results.append(imageName);
    results.append("\"");
  }
  if (height != null) {
    results.append(" height=\"");
    results.append(height);
    results.append("\"");
  }
  if (width != null) {
    results.append(" width=\"");
    results.append(width);
    results.append("\"");
  }
  if (align != null) {
    results.append(" align=\"");
    results.append(align);
    results.append("\"");
  }
  if (border != null) {
    results.append(" border=\"");
    results.append(border);
    results.append("\"");
  }
  if (hspace != null) {
    results.append(" hspace=\"");
    results.append(hspace);
    results.append("\"");
  }
  if (vspace != null) {
    results.append(" vspace=\"");
    results.append(vspace);
    results.append("\"");
  }
  if (ismap != null) {
    results.append(" ismap=\"");
    results.append(ismap);
    results.append("\"");
  }
  if (usemap != null) {
    results.append(" usemap=\"");
    results.append(usemap);
    results.append("\"");
  }
  results.append(prepareStyles());
  results.append(prepareEventHandlers());
  results.append(">");

  // Print this element to our output writer
  ResponseUtils.write(pageContext, results.toString());

  // Evaluate the reaminder of this page
  return (EVAL_PAGE);
}

}