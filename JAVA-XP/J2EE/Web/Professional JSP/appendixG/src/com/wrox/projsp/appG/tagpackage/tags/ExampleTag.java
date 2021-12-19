package com.wrox.projsp.appG.tagpackage.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;

public class ExampleTag extends TagSupport {
  public int doStartTag() {
    try {
      JspWriter out = pageContext.getOut();
      out.print("Hello from a Simple TagLib!");
    } catch (IOException ioErr) {
      System.out.println("Error in ExampleTag: " + ioErr);
    } 

    return (SKIP_BODY);
  } 
}
