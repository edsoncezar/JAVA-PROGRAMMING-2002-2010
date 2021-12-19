package com.wrox.store.exception;

/**
 * A simple exception that might be thrown when credit card
 * authorization fails for example.
 *
 * @author    Simon Brown
 */
public class AuthorizationException extends Exception {

  /**
   * Creates a new exception with the specific message.
   *
   * @param message   the message for this exception
   */
  public AuthorizationException(String message) {
    super(message);
  }

}