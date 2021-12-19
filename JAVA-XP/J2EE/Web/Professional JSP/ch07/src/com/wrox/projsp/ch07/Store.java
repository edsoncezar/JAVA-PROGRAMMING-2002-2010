package com.wrox.projsp.ch07;

import java.util.Properties;

public interface Store {
  public void store(Properties p) throws StoreException;
  public void store() throws StoreException;
  public Object load(String id) throws StoreException;
}
