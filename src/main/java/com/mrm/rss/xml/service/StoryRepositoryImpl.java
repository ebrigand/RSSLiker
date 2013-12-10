package com.mrm.rss.xml.service;

import java.io.IOException;

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
    boolean isStoryExist = false;
    for (int i = 0; i < repo.getStories().size(); i++) {
      Story story = repo.getStories().get(i);
      if (story.equals(entity)) {
        repo.getStories().set(i, entity);
        break;
      }
    }
    if (!isStoryExist) {
      repo.getStories().add(entity);
    }
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
    for (int i = 0; i < repo.getStories().size(); i++) {
      Story story = repo.getStories().get(i);
      if (story.equals(entity)) {
        return true;
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.xml.service.StoryRepository#find(java.lang.String)
   */
  @Override
  public Story find(String uriWithoutSpecialChars) throws IOException {
    Repo repo = xmlRepoManager.openRepo();
    for (int i = 0; i < repo.getStories().size(); i++) {
      Story story = repo.getStories().get(i);
      if (story.getUriWithoutSpecialChars().equals(story)) {
        return story;
      }
    }
    return null;
  }

}
