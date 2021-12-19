
/*
 * Copyright (c) 1995-1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

import java.io.*;
import java.util.*;

public class StringConverter {

   public static void printBytes(byte[] array, String name) {
      for (int k = 0; k < array.length; k++) {
         System.out.println(name + "[" + k + "] = " + "0x" +
            UnicodeFormatter.byteToHex(array[k]));
      }
   }

   public static void main(String[] args) {

      System.out.println(System.getProperty("file.encoding"));
      String original = new String("A" + "\u00ea" + "\u00f1"
                                 + "\u00fc" + "C");
   
      System.out.println("original = " + original);
      System.out.println();
   
      try {
          byte[] utf8Bytes = original.getBytes("UTF8");
          byte[] defaultBytes = original.getBytes();
 
          String roundTrip = new String(utf8Bytes, "UTF8");
          System.out.println("roundTrip = " + roundTrip);
 
          System.out.println();
          printBytes(utf8Bytes, "utf8Bytes");
          System.out.println();
          printBytes(defaultBytes, "defaultBytes");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      
   } // main

}

