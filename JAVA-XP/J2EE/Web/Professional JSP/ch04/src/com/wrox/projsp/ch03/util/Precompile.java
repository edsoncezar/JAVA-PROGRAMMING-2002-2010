package com.wrox.projsp.ch03.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Precompile {

  public static void main(String[] args) {

    String pathname = args[0];
    String queryString = "?jsp_precompile";
    String urlBase = "/chapter04/jsp/";

    try {
      File dir = new File(pathname);
      if (!dir.exists()) {
        throw new IOException("pathname " + pathname + " not found");
      } 
      File[] files = dir.listFiles();
      for (int i = 0; i < files.length; i++) {
        String jspFile = files[i].getName();
        if (jspFile.endsWith(".jsp")) {
          System.out.println("working on " + files[i].getName());
          try {
            URL url = new URL("http", "127.0.0.1", 8080, 
                              urlBase + jspFile + queryString);
            System.out.println("compiling " + jspFile + " using " + urlBase 
                               + jspFile + queryString);
            url.getContent();
          } catch (Exception e) {

            // add error handling
          } 
        } 
      } 
    } catch (Exception e) {
      System.out.println("exception raised:" + e);
    } 
  } 

}
