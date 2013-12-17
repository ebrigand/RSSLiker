package com.mrm.rss.exception;

/**
 * Service exception to catch the repositories exception (for a better layer
 * separation)
 * 
 * @author ebrigand
 * 
 */
public class RSSLikerServiceException extends Exception {

  private static final long serialVersionUID = 8290083516180935414L;

  private String error;

  public RSSLikerServiceException(String error, Exception exception) {
    super(exception);
    this.error = error;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

}
