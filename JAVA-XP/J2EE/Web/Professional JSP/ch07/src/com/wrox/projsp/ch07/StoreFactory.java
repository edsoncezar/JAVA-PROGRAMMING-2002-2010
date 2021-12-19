package com.wrox.projsp.ch07;

/**
 * Currently only one type of Store, a simple implementation that saves/restores information to/from file.
 * A Helper method is provided for creation of this simple storage implementation.
 */
public class StoreFactory {

  public static final int SIMPLE = 0;

  public static Store createStore() {
    return createStore(SIMPLE);
  } 

  public static Store createStore(int type) {
    if (type == SIMPLE) {
      return new SimpleStore();
    } 

    return null;
  } 

}
