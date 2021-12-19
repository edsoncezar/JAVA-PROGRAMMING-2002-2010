package com.wrox.projsp.ch09;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class HelloWorld extends TagSupport {

    public int doStartTag() throws JspException {
     try {
         pageContext.getOut().print("Hello JSP World!");
     } catch (Exception ioException) {
         System.err.println("IO Exception thrown in HelloWorld.doStartTag():");
         System.err.println(ioException.toString());

         throw new JspException(ioException);
     } 

     return SKIP_BODY;
    } 

}

