package com.wrox.projsp.ch07;

import java.util.Properties;

public class SimpleAuthContext implements AuthContext {
  private Properties props = new Properties();

  public SimpleAuthContext() {}

  public String getValue(String key) {
    return (String) props.get(key);
  }

  public void addValue(String key, String value) {
    props.put(key, value);
  }

}
