package com.ks.dao;

import com.ks.db.DBException;

/**
 * Data Access Object exception.
 */
public class DaoException extends DBException
{
  /**
   * Constructor 
   * 
   * @param message the exception message 
   */
  public DaoException(String message)
  {
    super(message);
  }

  /**
   * Constructor
   * 
   * @param message the exception message
   * @param cause the root cause
   */
  public DaoException(String message, Throwable cause)
  {
    super(message, cause);
  }

  /**
   * Constructor
   * 
   * @param cause the root cause
   */
  public DaoException(Throwable cause)
  {
    super(cause);
  }

}