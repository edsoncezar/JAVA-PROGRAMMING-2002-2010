package com.oreilly.struts.storefront.framework.database;

public class IntegerOid implements Oid{
  Integer value = null;

  public IntegerOid( int aValue ) {
    value = new Integer(aValue);
  }

  public Object getValue(){
    return value;
  }
}