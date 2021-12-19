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
import java.awt.*;

class SampleInstance {

   public static void main(String[] args) {

      Rectangle rectangle;
      Class rectangleDefinition;
      Class[] intArgsClass = new Class[] {int.class, int.class};
      Integer height = new Integer(12);
      Integer width = new Integer(34);
      Object[] intArgs = new Object[] {height, width};
      Constructor intArgsConstructor;

      try {
        rectangleDefinition = Class.forName("java.awt.Rectangle");
        intArgsConstructor = 
            rectangleDefinition.getConstructor(intArgsClass);
        rectangle = 
            (Rectangle) createObject(intArgsConstructor, intArgs);
      } catch (ClassNotFoundException e) {
          System.out.println(e);
      } catch (NoSuchMethodException e) {
          System.out.println(e);
      }
   }

   public static Object createObject(Constructor constructor, 
                                     Object[] arguments) {

      System.out.println ("Constructor: " + constructor.toString());
      Object object = null;

      try {
        object = constructor.newInstance(arguments);
        System.out.println ("Object: " + object.toString());
        return object;
      } catch (InstantiationException e) {
          System.out.println(e);
      } catch (IllegalAccessException e) {
          System.out.println(e);
      } catch (IllegalArgumentException e) {
          System.out.println(e);
      } catch (InvocationTargetException e) {
          System.out.println(e);
      }
      return object;
   }
}
