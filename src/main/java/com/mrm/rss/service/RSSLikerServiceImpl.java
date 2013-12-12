package com.mrm.rss.service;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mrm.rss.exception.RSSLikerServiceException;
import com.mrm.rss.xml.model.Like;
import com.mrm.rss.xml.model.Story;
import com.mrm.rss.xml.service.LikeRepository;
import com.mrm.rss.xml.service.StoryRepository;

/**
 * RSSLikerService implementation, used with the xml file repository service
 * layer
 * 
 * @author ebrigand
 * 
 */
@Service("rssLikerService")
public class RSSLikerServiceImpl implements RSSLikerService {

  @Resource(name = "likeRepository")
  private LikeRepository<Like> likeRepository;

  @Resource(name = "storyRepository")
  private StoryRepository<Story> storyRepository;

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.service.RSSLikerService#findLike(java.lang.String,
   * java.lang.String)
   */
  @Override
  public Like findLike(String accountName, String uriWithoutSpecialChars) throws RSSLikerServiceException {
    try {
      return likeRepository.find(accountName, uriWithoutSpecialChars);
    } catch (IOException e) {
      throw new RSSLikerServiceException("error when reading the like " + uriWithoutSpecialChars + " the repository file", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.service.RSSLikerService#like(com.mrm.rss.xml.model.Like,
   * boolean)
   */
  @Override
  public Like like(Like like, boolean isLike) throws RSSLikerServiceException {
    like.setIsLike(isLike);
    try {
      likeRepository.saveOrUpdate(like);
    } catch (IOException e) {
      throw new RSSLikerServiceException("error when saving the like " + like.getUriWithoutSpecialChars() + " in the repository file", e);
    }
    Story story = findStory(like.getUriWithoutSpecialChars());
    if (story != null) {
      story.setCount(isLike ? story.getCount() + 1 : story.getCount() - 1);
    } else {
      story = new Story(like.getUriWithoutSpecialChars());
      story.setCount(1);
    }
    try {
      storyRepository.saveOrUpdate(story);
    } catch (IOException e) {
      throw new RSSLikerServiceException("error when saving the like " + like.getUriWithoutSpecialChars() + " in the repository file", e);
    }
    return like;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.service.RSSLikerService#computeLikeCount(java.lang.String)
   */
  @Override
  public int computeLikeCount(String uriWithoutSpecialChars) throws RSSLikerServiceException {
    Story story = findStory(uriWithoutSpecialChars);
    if (story == null) {
      return 0;
    }
    return story.getCount();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mrm.rss.service.RSSLikerService#findStory(java.lang.String)
   */
  @Override
  public Story findStory(String uriWithoutSpecialChars) throws RSSLikerServiceException {
    try {
      return storyRepository.find(uriWithoutSpecialChars);
    } catch (IOException e) {
      throw new RSSLikerServiceException("error when reading the like " + uriWithoutSpecialChars + " in the repository file", e);
    }
  }

  @Override
  public List<Story> getAllStory() throws RSSLikerServiceException {
    try {
      return storyRepository.getAll();
    } catch (IOException e) {
      throw new RSSLikerServiceException("error when reading all the stories", e);
    }
  }
}
