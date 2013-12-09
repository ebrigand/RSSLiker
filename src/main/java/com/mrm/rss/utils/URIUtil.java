package com.mrm.rss.utils;

/**
 * Util class to transform an uri into a simplified uri
 * 
 * @author ebrigand
 * 
 */
public class URIUtil {

  /**
   * Transform an uri into a simplified uri, the characters '/' ':' '.' and the
   * characters sequence 'httpwww' are removed
   * 
   * @param uri
   *          the simplified uri
   * @return
   */
  public static String getUriWithoutSpecialChars(String uri) {
    return uri.replace("/", "").replace(":", "").replace(".", "").replace("-", "").replace("httpwww", "");
  }

}
