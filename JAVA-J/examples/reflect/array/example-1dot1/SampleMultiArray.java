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

class SampleMultiArray {

   public static void main(String[] args) {

      // The oneDimA and oneDimB objects are one dimensional int arrays 
      // with 5 elements.

      int[] dim1 = {5};
      int[] oneDimA = (int[]) Array.newInstance(int.class, dim1);
      int[] oneDimB = (int[]) Array.newInstance(int.class, 5);
 
      // The twoDimStr object is a 5 X 10 array of String objects.

      int[] dimStr = {5, 10};
      String[][] twoDimStr = (String[][]) Array.newInstance(String.class,dimStr);

      // The twoDimA object is an array of 12 int arrays. The tail
      // dimension is not defined. It is equivalent to the array 
      // created as follows:
      //    int[][] ints = new int[12][];

      int[] dimA = {12};
      int[][] twoDimA = (int[][]) Array.newInstance(int[].class, dimA);
   }
}

