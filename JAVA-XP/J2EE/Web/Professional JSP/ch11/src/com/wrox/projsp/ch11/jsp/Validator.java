package com.wrox.projsp.ch11.jsp;
public interface Validator {
  public void setPattern(String field, String pattern);
  public void setErrorMessage(String field, String message);
  public String getError(String field);
  public boolean validate(javax.servlet.http.HttpServletRequest request);
}

