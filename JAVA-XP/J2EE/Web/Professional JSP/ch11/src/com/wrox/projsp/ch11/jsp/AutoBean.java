package com.wrox.projsp.ch11.jsp;
public interface AutoBean {
    public void set(String property, Object value);
    public String get(String property);
    public Object getObject(String property);
    public void afterPopulate();
}

