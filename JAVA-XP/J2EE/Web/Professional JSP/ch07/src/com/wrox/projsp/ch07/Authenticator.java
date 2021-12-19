package com.wrox.projsp.ch07;

public interface Authenticator {
  public boolean authenticate();
  public void init(AuthContext context);
}
