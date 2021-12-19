
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

public class KeysDemo  {

   public static void sortArray(CollationKey[] keys) {

       CollationKey tmp;

       for (int i = 0; i < keys.length; i++) {
          for (int j = i + 1; j < keys.length; j++) {
             // Compare the keys
             if( keys[i].compareTo( keys[j] ) > 0 ) {
                // Swap keys[i] and keys[j] 
                tmp = keys[i];
                keys[i] = keys[j];
                keys[j] = tmp;
             }
          }
       }
   }

   static void displayWords(CollationKey[] keys) {

      for (int i = 0; i < keys.length; i++) {
                System.out.println(keys[i].getSourceString());
      }
   }

   static public void main(String[] args) {

      Collator enUSCollator = Collator.getInstance(new Locale("en","US"));
  
      String [] words = {
        "peach",
        "apricot",
        "grape",
        "lemon"
      };
 
      CollationKey[] keys = new CollationKey[words.length];
  
      for (int k = 0; k < keys.length; k ++) {
         keys[k] = enUSCollator.getCollationKey(words[k]);
      }
 
      sortArray(keys);
      displayWords(keys);
   }
}
