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

class SampleGet {

   public static void main(String[] args) {
      Rectangle r = new Rectangle(100, 325);
      printHeight(r);

   }

   static void printHeight(Rectangle r) {
      Field heightField;
      Integer heightValue;
      Class c = r.getClass();
      try {
        heightField = c.getField("height");
        heightValue = (Integer) heightField.get(r);
        System.out.println("Height: " + heightValue.toString());
      } catch (NoSuchFieldException e) {
          System.out.println(e);
      } catch (SecurityException e) {
          System.out.println(e);
      } catch (IllegalAccessException e) {
          System.out.println(e);
      }
   }
}
