
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

public class CollatorDemo {

   public static void sortStrings(Collator collator, String[] words) {
       String tmp;
       for (int i = 0; i < words.length; i++) {
           for (int j = i + 1; j < words.length; j++) {
               // Compare elements of the array two at a time.
               if (collator.compare(words[i], words[j] ) > 0 ) {
                   // Swap words[i] and words[j] 
                   tmp = words[i];
                   words[i] = words[j];
                   words[j] = tmp;
               }
           }
       }
   }

   public static void printStrings(String [] words) {
       for (int i = 0; i < words.length; i++) {
          System.out.println(words[i]);
       }
   }

   public static void testCompare() {

      Collator myCollator = Collator.getInstance(new Locale("en", "US"));

      System.out.println(myCollator.compare("abc", "def")); 
      System.out.println(myCollator.compare("rtf", "rtf")); 
      System.out.println(myCollator.compare("xyz", "abc")); 
   }

   static public void main(String[] args) {

      testCompare();
      System.out.println();

      Collator fr_FRCollator = Collator.getInstance(new Locale("fr","FR"));
      Collator en_USCollator = Collator.getInstance(new Locale("en","US"));
 
      String eWithCircumflex = new String("\u00EA");
      String eWithAcute = new String("\u00E9");
 
      String peachfr = "p" + eWithAcute + "ch" + eWithAcute;
      String sinfr = "p" + eWithCircumflex + "che";
 
      String [] words = {
        peachfr,
        sinfr,
        "peach",
        "sin"
      };
 
      sortStrings(fr_FRCollator, words);
      System.out.println("Locale: fr_FR");
      printStrings(words);
 
      System.out.println();
 
      sortStrings(en_USCollator, words);
      System.out.println("Locale: en_US");
      printStrings(words);
   }
}
