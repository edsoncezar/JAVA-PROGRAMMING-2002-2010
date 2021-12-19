/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package taglib;
import java.util.HashMap;

public class Definition {
   private HashMap params = new HashMap();

   public void setParam(Parameter p) {
      params.put(p.getName(), p);
   }
   public Parameter getParam(String name) {
      return (Parameter) params.get(name);
   }
}
