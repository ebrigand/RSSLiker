package com.mrm.rss.service.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mrm.rss.exception.RSSLikerServiceException;
import com.mrm.rss.feedreader.FeedChannelInterceptor;
import com.mrm.rss.service.RSSLikerService;
import com.mrm.rss.utils.URIUtil;
import com.mrm.rss.xml.model.Story;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Service to update the deferredResult list hold by the deferredResultContainer
 * The update is scheduled.
 * 
 * @author ebrigand
 * 
 */
@Service
public class LikeCountsUpdateScheduler {

  @Resource
  private DeferredResultContainer deferredResultContainer;

  @Resource(name = "rssLikerService")
  private RSSLikerService rssLikerService;

  @Resource
  private FeedChannelInterceptor feedChannelInterceptor;

  /**
   * Called in a separate thread, the time is defined by the fixedRate
   * 
   * @throws RSSLikerServiceException
   * @throws IOException
   */
  @Scheduled(fixedRate = 1000)
  public void process() throws RSSLikerServiceException {
    Set<Story> repoStories = rssLikerService.getAllStory();
    Set<SyndEntry> syndEntries = feedChannelInterceptor.getEntries();
    deferredResultContainer.updateAllResults(getMatchingStoriesWithCurrentReadStories(repoStories, syndEntries));
  }

  /**
   * Retain Story of repoStories whose the uriWithoutSpecialChars exist in a
   * SyndEntry of syndEntries. Used to return only a list of Story still
   * existing as a SyndEntry get from the RSS URL
   * 
   * @param repoStories
   *          All the stories read from the repository
   * @param syndEntries
   *          All the syndEntries read from the RSS URL
   * @return
   * @throws RSSLikerServiceException
   */
  private List<Story> getMatchingStoriesWithCurrentReadStories(Set<Story> repoStories, Set<SyndEntry> syndEntries) throws RSSLikerServiceException {
    List<Story> matchingStories = new ArrayList<Story>();
    for (Story story : repoStories) {
      for (Iterator<SyndEntry> it = syndEntries.iterator(); it.hasNext();) {
        SyndEntry syndEntry = it.next();
        if (story.getUriWithoutSpecialChars().equals(URIUtil.getUriWithoutSpecialChars(syndEntry.getUri()))) {
          matchingStories.add(story);
          break;
        }
      }
    }
    return matchingStories;
  }
}