package com.wrox.store.ejb.product;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

/**
 * A wrapper for the ProductHome remote object. This hides away all of the
 * remote lookups for the product home and also caches a reference to the
 * home object.
 *
 * @author    Simon Brown
 */
public class ProductManager {

  /** a reference to the actual remote home object */
  private ProductHome productHome;

  /**
   * Default, no args constructor.
   */
  public ProductManager() throws NamingException, RemoteException {
    InitialContext ctx = new InitialContext();

    // look up home and keep hold of the reference
    productHome = (ProductHome)PortableRemoteObject.narrow (ctx.lookup("store/ProductHome"), ProductHome.class);
  }

  /**
   * Wraps up the findAll method on the home interface.
   *
   * @return  a Collection of remote Product references, as returned by the remote home
   */
  public Collection findAll() throws FinderException, RemoteException {
    return productHome.findAll();
  }

  /**
   * Wraps up the findByPrimaryKey method on the home interface.
   *
   * @return  a remote Product reference, as returned by the remote home
   */
  public Product findByPrimaryKey(String id) throws FinderException, RemoteException {
    return productHome.findByPrimaryKey(id);
  }

}