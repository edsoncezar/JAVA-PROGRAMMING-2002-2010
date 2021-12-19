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

class SampleCreateArray {

   public static void main(String[] args) {
      int[] originalArray =  {55, 66};
      int[] biggerArray = (int[]) doubleArray(originalArray);
      System.out.println("originalArray:");
      for (int k = 0; k < Array.getLength(originalArray); k++)
         System.out.println(originalArray[k]);
      System.out.println("biggerArray:");
      for (int k = 0; k < Array.getLength(biggerArray); k++)
         System.out.println(biggerArray[k]);
   }

   static Object doubleArray(Object source) {
      int sourceLength = Array.getLength(source);
      Class arrayClass = source.getClass();
      Class componentClass = arrayClass.getComponentType();
      Object result = Array.newInstance(componentClass, sourceLength * 2);     
      System.arraycopy(source, 0, result, 0, sourceLength);
      return result;
   }
}

