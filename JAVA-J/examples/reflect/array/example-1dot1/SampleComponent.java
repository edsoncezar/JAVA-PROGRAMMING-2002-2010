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

class SampleComponent {

   public static void main(String[] args) {
      int[] ints = new int[2];
      Button[]  buttons = new Button[6];
      String[][] twoDim = new String[4][5];

      printComponentType(ints);
      printComponentType(buttons);
      printComponentType(twoDim);
   }

   static void printComponentType(Object array) {
      Class arrayClass = array.getClass();
      String arrayName = arrayClass.getName();
      Class componentClass = arrayClass.getComponentType();
      String componentName = componentClass.getName();
      System.out.println("Array: " + arrayName + 
         ", Component: " + componentName);
   }
}

