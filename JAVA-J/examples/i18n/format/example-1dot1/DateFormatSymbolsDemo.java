
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

public class DateFormatSymbolsDemo {

   static public void changeWeekDays() {

      Date today;
      String result;
      SimpleDateFormat formatter;
      DateFormatSymbols symbols;
      String[] defaultDays;
      String[] modifiedDays;

      symbols = new DateFormatSymbols(new Locale("en","US"));
      defaultDays = symbols.getShortWeekdays();

      for (int i = 0; i < defaultDays.length; i++) {
         System.out.print(defaultDays[i] + "  ");
      }
      System.out.println();

      String[] capitalDays = {
         "", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
      symbols.setShortWeekdays(capitalDays);

      modifiedDays = symbols.getShortWeekdays();
      for (int i = 0; i < modifiedDays.length; i++) {
         System.out.print(modifiedDays[i] + "  ");
      }

      System.out.println();
      System.out.println();

      formatter = new SimpleDateFormat("E", symbols);
      today = new Date();
      result = formatter.format(today);
      System.out.println(result);
   }

   static public void main(String[] args) {
      changeWeekDays();
   }
}
