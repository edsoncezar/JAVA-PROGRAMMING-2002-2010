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

class SampleMethod {

   public static void main(String[] args) {
      Polygon p = new Polygon();
      showMethods(p);
   }

   static void showMethods(Object o) {
      Class c = o.getClass();
      Method[] theMethods = c.getMethods();
      for (int i = 0; i < theMethods.length; i++) {
         String methodString = theMethods[i].getName();
         System.out.println("Name: " + methodString);
         String returnString = theMethods[i].getReturnType().getName();
         System.out.println("   Return Type: " + returnString);
         Class[] parameterTypes = theMethods[i].getParameterTypes();
         System.out.print("   Parameter Types:");
         for (int k = 0; k < parameterTypes.length; k ++) {
            String parameterString = parameterTypes[k].getName();
            System.out.print(" " + parameterString);
            }
         System.out.println();
         }
   }
}
