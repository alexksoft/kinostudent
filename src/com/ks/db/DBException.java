package com.ks.db;

/**
 * General application exception.
 */
public class DBException extends Exception
{
  /**
   * Constructor.
   * 
   * @param message the exception message 
   */
  public DBException(String message)
  {
    super(message);
  }

  /**
   * Constructor.
   * 
   * @param message the exception message
   * @param cause the root cause
   */
  public DBException(String message, Throwable cause)
  {
    super(message, cause);
  }

  /**
   * Constructor.
   * 
   * @param cause the root cause
   */
  public DBException(Throwable cause)
  {
    super(cause);
  }
}