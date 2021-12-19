/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.util.*;
import java.text.DateFormat;

public class MyLocales {
  HashMap locales;
  ArrayList localeNames;
  DateFormat dateFormatter;

  public MyLocales() {
    locales = new HashMap();
    localeNames = new ArrayList();
    Locale list[] = DateFormat.getAvailableLocales();
    for (int i = 0; i < list.length; i++) {     
      locales.put(list[i].getDisplayName(), list[i]);
      localeNames.add(list[i].getDisplayName());
    }
    Collections.sort(localeNames);
  }
  public Collection getLocaleNames() {
    return localeNames;
  }
     
  public Locale getLocale(String displayName) {
    return (Locale)locales.get(displayName);
  }
}
