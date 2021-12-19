package com.wrox.projsp.ch06;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Debug {

  static private PrintWriter _debugFile;

  public static void init() {
    try {
      _debugFile = new PrintWriter (
                                    new FileWriter ("debug.log"), true);
    } catch (Exception e) {
      System.out.println("error opening debug.log");
    }
  }

  public static void log (Object src, String method, String msg) {
    _debugFile.println(src + ":" + method + ":" + msg);
    System.out.println(src + ":" + method + ":" + msg);
  }

}
