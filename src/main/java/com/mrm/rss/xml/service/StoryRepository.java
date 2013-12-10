package com.mrm.rss.xml.service;

import java.io.IOException;
import java.util.List;

import com.mrm.rss.xml.model.Story;

/**
 * Story Repository interface
 * 
 * @author ebrigand
 * 
 * @param <E>
 */
public interface StoryRepository<E extends Story> extends XmlBaseRepository<E> {

  /**
   * Return a Story if a Story with the same uriWithoutSpecialChars of the
   * uriWithoutSpecialChars parameter exists in the repository
   * 
   * @param uriWithoutSpecialChars
   * @return
   * @throws IOException
   */
  Story find(String uriWithoutSpecialChars) throws IOException;;

  /**
   * Return a list of Story, null if list is empty
   * 
   * @return
   * @throws IOException
   */
  List<Story> getAll() throws IOException;

}
