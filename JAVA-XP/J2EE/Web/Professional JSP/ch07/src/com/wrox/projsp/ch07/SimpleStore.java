package com.wrox.projsp.ch07;

import java.io.*;
import java.util.Properties;

public class SimpleStore implements Store {
  private Properties props;

  public SimpleStore() {}

  public SimpleStore(Properties p) {
    props = p;
  }

  public void store(Properties p) throws StoreException {
    props = p;
    store();
  }

  public void store() throws StoreException {
    if (props == null) {
      throw new StoreException("Problem.  store is null.");
    }

    try {
      FileOutputStream out = new FileOutputStream((String)props.getProperty("guesser"));
      props.store(out, "Baby Game -- "+props.get("guesser")+"'s guesses");
      out.flush();
      out.close();    
    }
    catch (IOException e) {
      throw new StoreException("Problem writing storage file: "+e.getMessage());
    }
  }

  public Object load(String id) throws StoreException {
    Properties props = new Properties();

    try {
      FileInputStream in = new FileInputStream(id);
      props.load(in);
      in.close();
    }
    catch (IOException e) {
      throw new StoreException("Problem reading storage file: "+e.getMessage());
    }

    return props;
  }

  public static void main(String[] args) throws Throwable {
    Properties p = new Properties();
    p.put("guesser", "abc");
    p.put("fish", "asdfasdfasdfasdfas");
    Store storer = StoreFactory.createStore();
    storer.store(p);
    System.out.println("LOADED SUCCESSFULLY  --->"+storer.load("abc"));
  }
}

