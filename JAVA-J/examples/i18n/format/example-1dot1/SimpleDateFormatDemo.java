
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

import java.util.*;
import java.text.*;

public class SimpleDateFormatDemo {

   static public void displayDate(Locale currentLocale) {

      Date today;
      String result;
      SimpleDateFormat formatter;

      formatter = new SimpleDateFormat("EEE d MMM yy", currentLocale);
      today = new Date();
      result = formatter.format(today);

      System.out.println("Locale: " + currentLocale.toString());
      System.out.println("Result: " + result);
   }


   static public void displayPattern(String pattern, Locale currentLocale) {

      Date today;
      SimpleDateFormat formatter;
      String output;

      formatter = new SimpleDateFormat(pattern, currentLocale);
      today = new Date();
      output = formatter.format(today);

      System.out.println(pattern + "   " + output);
   }

   static public void main(String[] args) {

      Locale[] locales = {
          new Locale("fr","FR"),
          new Locale("de","DE"),
          new Locale("en","US")
      };

      for (int i = 0; i < locales.length; i++) {
         displayDate(locales[i]);
         System.out.println();
      }

      String[] patterns = {
         "dd.MM.yy",
         "yyyy.MM.dd G 'at' hh:mm:ss z",
         "EEE, MMM d, ''yy",
         "h:mm a",
         "H:mm",
         "H:mm:ss:SSS",
         "K:mm a,z",
         "yyyy.MMMMM.dd GGG hh:mm aaa"
      };

      for (int k = 0; k < patterns.length; k++) {
         displayPattern(patterns[k], new Locale("en","US"));
         System.out.println();
      }

      System.out.println();
   }
}
