/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.text.DateFormat;
import java.util.*;

public class MyDate {
  Date today;
  DateFormat dateFormatter;

  public MyDate() {
    today = new Date();
  }

  public String getDate() {
    return dateFormatter.format(today);	
  }

  public void setLocale(Locale l) {
    dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, l);
  }
}
