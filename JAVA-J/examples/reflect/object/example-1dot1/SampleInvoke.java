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

import java.lang.reflect.*;

class SampleInvoke {

   public static void main(String[] args) {
      String firstWord = "Hello ";
      String secondWord = "everybody.";
      String bothWords = append(firstWord, secondWord);
      System.out.println(bothWords);
   }

   public static String append(String firstWord, String secondWord) {
      String result = null;
      Class c = String.class;
      Class[] parameterTypes = new Class[] {String.class};
      Method concatMethod;
      Object[] arguments = new Object[] {secondWord};
      try {
        concatMethod = c.getMethod("concat", parameterTypes);
        result = (String) concatMethod.invoke(firstWord, arguments);
      } catch (NoSuchMethodException e) {
          System.out.println(e);
      } catch (IllegalAccessException e) {
          System.out.println(e);
      } catch (InvocationTargetException e) {
          System.out.println(e);
      }
      return result;
   }
}
