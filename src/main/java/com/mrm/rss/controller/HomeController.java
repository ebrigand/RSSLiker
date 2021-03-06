package com.mrm.rss.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import com.mrm.rss.exception.RSSLikerServiceException;
import com.mrm.rss.feedreader.FeedChannelInterceptor;
import com.mrm.rss.service.RSSLikerService;
import com.mrm.rss.service.async.DeferredResultContainer;
import com.mrm.rss.utils.URIUtil;
import com.mrm.rss.viewbean.HomeBeanView;
import com.mrm.rss.xml.model.Like;
import com.mrm.rss.xml.model.Story;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Controller for home page
 * 
 * @author ebrigand
 * 
 */
@Controller
@RequestMapping("/rss")
public class HomeController {

  private static Logger log = Logger.getLogger(HomeController.class.getName());

  @Resource(name = "rssLikerService")
  private RSSLikerService rssLikerService;

  @Resource
  private FeedChannelInterceptor feedChannelInterceptor;

  @Resource
  private DeferredResultContainer deferredResultContainer;

  /**
   * Called every 1000ms by the home page (see
   * LikeCountsUpdateScheduler.process) to maintain the updated like counts. A
   * list of updated count for each story is store in a DeferredResult object,
   * the object is created when the method is called and destroyed after
   * consumption
   * 
   * @return
   * @throws RSSLikerServiceException
   */
  @RequestMapping(value = "/getLikeCounts", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public DeferredResult<List<Story>> getLikeCounts() throws RSSLikerServiceException {
    final DeferredResult<List<Story>> deferredResult = new DeferredResult<List<Story>>(null, Collections.emptyList());

    deferredResultContainer.register(deferredResult);
    deferredResult.onTimeout(new Runnable() {
      @Override
      public void run() {
        deferredResultContainer.remove(deferredResult);

      }
    });
    deferredResult.onCompletion(new Runnable() {
      @Override
      public void run() {
        deferredResultContainer.remove(deferredResult);

      }
    });
    return deferredResult;
  }

  /**
   * Return the home page
   * 
   * @param accountName
   *          account name as a request parameter
   * @return a ModelAndView initialized with the view name and the list of
   *         HomeBeanView as attribute of the model
   * @throws IOException
   */
  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public ModelAndView getHome(@RequestParam(value = "accountName", required = false, defaultValue = "defaultName") String accountName) throws RSSLikerServiceException {
    ModelAndView modelAndView = new ModelAndView("home");
    List<HomeBeanView> homeBeanViewSet = buildHomeBeanViewSet(accountName);
    modelAndView.addObject("homeBeanViews", homeBeanViewSet);
    return modelAndView;
  }

  /**
   * Action executed when a user click on the like/unlike button, the like and
   * the rss story counter is saved, a view bean with the new values is returned
   * 
   * @param uriWithoutSpecialChars
   *          uri simplified, uri without special chars
   * @param homeBeanViewFromView
   *          view bean in JSON from the form of the home page
   * @return an homeBeanView in JSON containing the values updated
   * @throws IOException
   */
  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/saveOrUpdate/{uriWithoutSpecialChars}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Like likeAction(@PathVariable String uriWithoutSpecialChars, @RequestBody Like like) throws RSSLikerServiceException {
    log.debug("Saving like with uri: " + like.getUriWithoutSpecialChars());
    like = rssLikerService.like(like, like.getIsLike());
    return like;
  }

  /**
   * Build the list of HomeBeanView with Like object. The Like are retrieve from
   * RSSLikerService or created if not find.
   * 
   * @param accountName
   *          account name
   * @return a list of HomeBeanView
   * @throws IOException
   */
  private List<HomeBeanView> buildHomeBeanViewSet(String accountName) throws RSSLikerServiceException {
    // Reverse the set of SyndEntry to get the more recent rss news on the top
    // of the page
    List<HomeBeanView> homeBeanViews = new LinkedList<HomeBeanView>();
    LinkedList<SyndEntry> list = new LinkedList<SyndEntry>(feedChannelInterceptor.getEntries());
    Iterator<SyndEntry> itr = list.descendingIterator();
    while (itr.hasNext()) {
      SyndEntry syndEntry = itr.next();
      // Try to get a Like from the service with the accountName and the
      // simplified uri
      Like like = rssLikerService.findLike(accountName, URIUtil.getUriWithoutSpecialChars(syndEntry.getUri()));
      if (like == null) {
        like = new Like(accountName, URIUtil.getUriWithoutSpecialChars(syndEntry.getUri()));
      }
      HomeBeanView homeBeanView = new HomeBeanView();
      homeBeanView.setSyndEntry(syndEntry);
      homeBeanView.setLike(like);
      // Get the count of like for a rss story
      homeBeanView.setLikeCount(rssLikerService.computeLikeCount(URIUtil.getUriWithoutSpecialChars(syndEntry.getUri())));
      homeBeanViews.add(homeBeanView);
    }
    return homeBeanViews;
  }
}