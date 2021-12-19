package com.oreilly.struts.storefront.service;

public interface IStorefrontServiceFactory {
  public IStorefrontService createService();
  public void destroy();
}