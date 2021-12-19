/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.util.*;
import javax.ejb.*;

public class CartBean implements SessionBean {

  String customerName;
  String customerId;
  Vector contents;

  public void ejbCreate(String person) throws CreateException {
    if (person == null) {
      throw new CreateException("Null person not allowed.");
    } else {
      customerName = person;
    }
    customerId = "0";
    contents = new Vector();
  }

  public void ejbCreate(String person, String id) throws CreateException {
    ejbCreate(person);
    IdVerifier idChecker = new IdVerifier();
    if (idChecker.validate(id)) {
      customerId = id;
    } else {
      throw new CreateException("Invalid id: " + id);
    }
  }

  public void addBook(String title) {
    contents.add(title);
  }

  public void removeBook(String title) throws BookException {
    boolean result = contents.remove(title);
    if (result == false) {
      throw new BookException(title + " not in cart.");
    }
  }

  public Vector getContents() {
    return contents;
  }

  public CartBean() {}
  public void ejbRemove() {}
  public void ejbActivate() {}
  public void ejbPassivate() {}
  public void setSessionContext(SessionContext sc) {}
} 
