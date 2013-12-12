package com.mrm.rss.xml.service;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.mrm.rss.xml.model.Repo;
import com.mrm.rss.xml.model.Story;
import com.mrm.rss.xml.repository.XMLRepoManager;

/**
 * Story Repository implementation
 * 
 * @author mamos
 * 
 */
@Repository("storyRepository")
public class StoryRepositoryImpl implements StoryRepository<Story> {

  @Resource
  private XMLRepoManager xmlRepoManager;

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mrm.rss.xml.service.XmlBaseRepository#saveOrUpdate(java.lang.Object)
   */
  @Override
  public synchronized void saveOrUpdate(Story entity) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    // Remove and add the entity (based on equals so it will update the count
    // field)
    repo.getStories().remove(entity);
    repo.getStories().add(entity);
    xmlRepoManager.saveRepo(repo);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mrm.rss.xml.service.XmlBaseRepository#isEntityExists(java.lang.Object)
   */
  @Override
  public boolean isEntityExists(Story entity) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    return isEntityExistsInternal(repo, entity);
  }

  /**
   * Internal research method
   * 
   * @param repo
   * @param entity
   * @return
   * @throws IOException
   */
  private boolean isEntityExistsInternal(Repo repo, Story entity) throws IOException {
    return repo.getStories().contains(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.xml.service.StoryRepository#find(java.lang.String)
   */
  @Override
  public Story find(String uriWithoutSpecialChars) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    for (Story story : repo.getStories()) {
      if (story.getUriWithoutSpecialChars().equals(uriWithoutSpecialChars)) {
        return story;
      }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.xml.service.StoryRepository#getAll()
   */
  @Override
  public Set<Story> getAll() throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    if (!repo.getStories().isEmpty()) {
      return repo.getStories();
    }
    return null;
  }

}
