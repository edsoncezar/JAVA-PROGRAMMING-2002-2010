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

public class ListDemo {

   static void displayValues(Locale currentLocale) {

      ResourceBundle stats = 
         ResourceBundle.getBundle("StatsBundle",currentLocale);

      Integer gdp = (Integer)stats.getObject("GDP");
      System.out.println("GDP = " + gdp.toString());
      Integer pop = (Integer)stats.getObject("Population");
      System.out.println("Population = " + pop.toString());
      Double lit = (Double)stats.getObject("Literacy");
      System.out.println("Literacy = " + lit.toString());
      
   } // displayValues

   static public void main(String[] args) {

      Locale[] supportedLocales = {
         new Locale("en","CA"),
         new Locale("ja","JP"),
         new Locale("fr","FR")
      };

      for (int i = 0; i < supportedLocales.length; i ++) {
         System.out.println("Locale = " + supportedLocales[i]);
         displayValues(supportedLocales[i]);
         System.out.println();
      }

   } // main

} // class
