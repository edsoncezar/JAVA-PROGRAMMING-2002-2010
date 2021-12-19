package com.wrox.projsp.ch07;

public class SimpleAuthenticator implements Authenticator {
  private String user;
  private String password;

  public SimpleAuthenticator() {}

  public void init(AuthContext context) {
    user = context.getValue("guesser");
    password = context.getValue("password");
  }

  public boolean authenticate() {
    //If the parameters exist, we consider them valid
    if ((user != null) && (password != null))
      return true;
    else
      return false;
  }

}
