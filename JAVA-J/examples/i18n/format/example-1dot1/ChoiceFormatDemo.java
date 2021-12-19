
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

public class ChoiceFormatDemo {

   static void displayMessages(Locale currentLocale) {

      System.out.println("currentLocale = " + currentLocale.toString());
      System.out.println();

      ResourceBundle bundle = 
         ResourceBundle.getBundle("ChoiceBundle",currentLocale);

      MessageFormat messageForm = new MessageFormat("");
      messageForm.setLocale(currentLocale);

      double[] fileLimits = {0,1,2};

      String [] fileStrings = {
         bundle.getString("noFiles"),
         bundle.getString("oneFile"),
         bundle.getString("multipleFiles")
      };

      ChoiceFormat choiceForm = new ChoiceFormat(fileLimits, fileStrings);

      String pattern = bundle.getString("pattern");
      Format[] formats = {choiceForm, null, NumberFormat.getInstance()};

      messageForm.applyPattern(pattern);
      messageForm.setFormats(formats);

      Object[] messageArguments = {null, "XDisk", null};

      for (int numFiles = 0; numFiles < 4; numFiles++) {
         messageArguments[0] = new Integer(numFiles);
         messageArguments[2] = new Integer(numFiles);
         String result = messageForm.format(messageArguments);
         System.out.println(result);
      }
   }

   static public void main(String[] args) {
      displayMessages(new Locale("en", "US"));
      System.out.println();
      displayMessages(new Locale("fr", "FR"));
   }
} 
