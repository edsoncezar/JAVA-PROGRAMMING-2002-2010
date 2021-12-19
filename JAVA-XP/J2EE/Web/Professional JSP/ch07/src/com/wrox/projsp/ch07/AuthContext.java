package com.wrox.projsp.ch07;

public interface AuthContext {
  public String getValue(String key);
  public void addValue(String key, String value);
}
