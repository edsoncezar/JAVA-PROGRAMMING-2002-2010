package com.wrox.projsp.ch06.time;

import java.util.Hashtable;
import java.io.*;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class TimeHashtable extends Hashtable implements HttpSessionBindingListener, Serializable {

  private String path;

  public TimeHashtable(String path) {
    this.path = path;
  }

  public void valueBound(HttpSessionBindingEvent event) {
    try {
      File file = new File(path);
      if (file.exists()) {
        FileInputStream in = new FileInputStream(path);
        ObjectInputStream s = new ObjectInputStream(in);
        TimeHashtable t = (TimeHashtable)s.readObject();
        this.putAll(t);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void valueUnbound(HttpSessionBindingEvent event) {
    try {
      FileOutputStream out = new FileOutputStream(path);
      ObjectOutputStream s = new ObjectOutputStream(out);
      s.writeObject(this);
      s.flush();   
      s.close();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
