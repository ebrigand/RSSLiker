package com.mrm.rss.xml.service;

import java.io.IOException;

/**
 * XML Repository base interface with the base methods for the repository
 * 
 * @author ebrigand
 * 
 * @param <E>
 */
public interface XmlBaseRepository<E> {

  /**
   * Save if entity doesn't exist in the database or update the entity if the
   * entity exists in the database
   * 
   * @param entity
   * @throws IOException
   *           if an error occurs when accessing to the XML repository file
   */
  void saveOrUpdate(E entity) throws IOException;

  /**
   * Return true if the entity exists in the repository false otherwise (based
   * on the equals method)
   * 
   * @param entity
   * @return
   * @throws IOException
   *           if an error occurs when accessing to the XML repository file
   */
  boolean isEntityExists(E entity) throws IOException;
}
