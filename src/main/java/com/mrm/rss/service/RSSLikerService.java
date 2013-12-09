package com.mrm.rss.service;

import com.mrm.rss.exception.RSSLikerServiceException;
import com.mrm.rss.xml.model.Like;
import com.mrm.rss.xml.model.Story;

/**
 * Service layer for RSSLiker
 * 
 * @author ebrigand
 * 
 */
public interface RSSLikerService {

  /**
   * Find a Like by an accountName and an uri without special chars
   * 
   * @param accountName
   *          an account name
   * @param uriWithoutSpecialChars
   *          an uri without special chars
   * @return a Like or null if not find
   * @throws RSSLikerServiceException
   */
  public Like findLike(String accountName, String uriWithoutSpecialChars) throws RSSLikerServiceException;

  /**
   * Like action, modify the Like object with the isLike value, save the Like
   * object in the repository and update the count of the Story (the Story with
   * the same uriWithoutSpecialChars of the like)
   * 
   * @param like
   *          a like
   * @param isLike
   *          the new boolean value to assign to the like
   * @return a like updated with the isLike
   * @throws RSSLikerServiceException
   */
  public Like like(Like like, boolean isLike) throws RSSLikerServiceException;

  /***
   * Find a Story by an uri without special chars
   * 
   * @param uriWithoutSpecialChars
   *          an uri without special chars
   * @return a Story or null if not find
   * @throws RSSLikerServiceException
   */
  public Story findStory(String uriWithoutSpecialChars) throws RSSLikerServiceException;

  /**
   * Get the like count of a Story, the story is retrieve by the
   * uriWithoutSpecialChars parameter
   * 
   * @param uriWithoutSpecialChars
   *          an uri without special chars
   * @return a like count
   * @throws RSSLikerServiceException
   */
  public int computeLikeCount(String uriWithoutSpecialChars) throws RSSLikerServiceException;

}
