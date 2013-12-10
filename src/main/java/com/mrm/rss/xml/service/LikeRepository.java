package com.mrm.rss.xml.service;

import java.io.IOException;
import java.util.List;

import com.mrm.rss.xml.model.Like;

/**
 * Like Repository interface
 * 
 * @author ebrigand
 * 
 * @param <E>
 */
public interface LikeRepository<E extends Like> extends XmlBaseRepository<E> {

  /**
   * Return a Story if a Story with the same uriWithoutSpecialChars of the
   * uriWithoutSpecialChars parameter and the same accountName of the
   * accountName parameter exists in the repository
   * 
   * @param accountName
   * @param uriWithoutSpecialChars
   * @return
   * @throws IOException
   */
  Like find(String accountName, String uriWithoutSpecialChars) throws IOException;

  /**
   * Return a list of Like, null if list is empty
   * 
   * @param accountName
   * @return
   * @throws IOException
   */
  List<Like> getAllByAccountName(String accountName) throws IOException;

}
