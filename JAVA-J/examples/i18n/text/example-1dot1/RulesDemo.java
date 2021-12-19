
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

public class RulesDemo  {

   public static void sortStrings(Collator collator, String[] words) {
       String tmp;
       for (int i = 0; i < words.length; i++) {
           for (int j = i + 1; j < words.length; j++) {
               // Compare elements of the words array
               if( collator.compare(words[i], words[j] ) > 0 ) {
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

   static public void main(String[] args) {

      String englishRules = 
         ("< a,A < b,B < c,C < d,D < e,E < f,F " +
          "< g,G < h,H < i,I < j,J < k,K < l,L " +
          "< m,M < n,N < o,O < p,P < q,Q < r,R " +
          "< s,S < t,T < u,U < v,V < w,W < x,X " +
          "< y,Y < z,Z");
  
      String smallnTilde  = new String("\u00F1");
      String capitalNTilde = new String("\u00D1");
  
      String traditionalSpanishRules = 
         ("< a,A < b,B < c,C " +
          "< ch, cH, Ch, CH "  +
          "< d,D < e,E < f,F " +
          "< g,G < h,H < i,I < j,J < k,K < l,L " +
          "< ll, lL, Ll, LL "  +
          "< m,M < n,N " +
          "< " + smallnTilde + "," + capitalNTilde + " " +
          "< o,O < p,P < q,Q < r,R " +
          "< s,S < t,T < u,U < v,V < w,W < x,X " +
          "< y,Y < z,Z");

       String [] words = {
         "luz",
         "curioso",
         "llama",
         "chalina"
       };

       try {
           RuleBasedCollator enCollator = 
              new RuleBasedCollator(englishRules);
           RuleBasedCollator spCollator = 
              new RuleBasedCollator(traditionalSpanishRules);
 
           sortStrings(enCollator, words);
           printStrings(words);
      
           System.out.println();
      
           sortStrings(spCollator, words);
           printStrings(words);
       } catch (ParseException pe) {
           System.out.println("Parse exception for rules");
       }
   }
}
