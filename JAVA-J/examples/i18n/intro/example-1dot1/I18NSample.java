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

public class I18NSample {

   static public void main(String[] args) {

      String language;
      String country;

      if (args.length != 2) {
          language = new String("en");
          country = new String("US");
      } else {
          language = new String(args[0]);
          country = new String(args[1]);
      }

      Locale currentLocale;
      ResourceBundle messages;

      currentLocale = new Locale(language, country);

      messages =
        ResourceBundle.getBundle("MessagesBundle",currentLocale);

      System.out.println(messages.getString("greetings"));
      System.out.println(messages.getString("inquiry"));
      System.out.println(messages.getString("farewell"));
   }
}
