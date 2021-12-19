
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

public class Available {

   static public void main(String[] args) {

      Locale list[] = SimpleDateFormat.getAvailableLocales();

      for (int i = 0; i < list.length; i++) {
          System.out.println(list[i].toString());
      }

      for (int i = 0; i < list.length; i++) {
          System.out.println(list[i].getDisplayName());
      }

      for (int i = 0; i < list.length; i++) {
          System.out.println(list[i].getDisplayName(Locale.FRANCE));
      }

   } 
} 


