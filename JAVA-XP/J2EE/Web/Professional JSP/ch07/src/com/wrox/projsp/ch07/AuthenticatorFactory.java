package com.wrox.projsp.ch07;

/**
Currently only one type of Authenticator, a simple implementation that merely checks if the two parameters are non-null
Currently only one type of AuthContext, as well. The simple auth context just stuffs the values into a Hashtable.
*/

public class AuthenticatorFactory {

  public static final int SIMPLE=0;
    
  public static Authenticator create(int type) {
    if (type == SIMPLE)
      return new SimpleAuthenticator();

    return null;
  }

  public static AuthContext createContext(int type) {
    if (type == SIMPLE)
      return new SimpleAuthContext();

    return null;
  }

}
