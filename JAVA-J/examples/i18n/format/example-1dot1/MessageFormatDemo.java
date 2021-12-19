
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

public class MessageFormatDemo {

   static  void displayMessage(Locale currentLocale) {

      System.out.println("currentLocale = " + currentLocale.toString());
      System.out.println();

      ResourceBundle messages = 
         ResourceBundle.getBundle("MessageBundle",currentLocale);

      Object[] messageArguments = {
         messages.getString("planet"),
         new Integer(7),
         new Date()
      };

      MessageFormat formatter = new MessageFormat("");
      formatter.setLocale(currentLocale);

      formatter.applyPattern(messages.getString("template"));
      String output = formatter.format(messageArguments);

      System.out.println(output);

   }

   static public void main(String[] args) {
      displayMessage(new Locale("en", "US"));
      System.out.println();
      displayMessage(new Locale("de", "DE"));
   }
} 
