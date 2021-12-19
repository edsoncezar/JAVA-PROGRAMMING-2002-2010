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

class SampleGetArray {

   public static void main(String[] args) {
      int[] sourceInts = {12, 78};
      int[] destInts = new int[2];
      copyArray(sourceInts, destInts);
      String[] sourceStrgs = {"Hello ", "there ", "everybody"};
      String[] destStrgs = new String[3];
      copyArray(sourceStrgs, destStrgs);
   }

   public static void copyArray(Object source, Object dest) {
      for (int i = 0; i < Array.getLength(source); i++) {
         Array.set(dest, i, Array.get(source, i));
         System.out.println(Array.get(dest, i));
      }
   }
}
