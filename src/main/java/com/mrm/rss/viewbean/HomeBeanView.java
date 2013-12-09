package com.mrm.rss.viewbean;

import java.io.Serializable;

import com.mrm.rss.xml.model.Like;
import com.sun.syndication.feed.synd.SyndEntry;

/**
 * View bean used in the home.jsp
 * 
 * @author ebrigand
 * 
 */
public class HomeBeanView implements Serializable {

  private static final long serialVersionUID = -7182100087299736725L;

  private SyndEntry syndEntry;

  private Like like;

  private Integer likeCount;

  public Like getLike() {
    return like;
  }

  public void setLike(Like like) {
    this.like = like;
  }

  public Integer getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(Integer likeCount) {
    this.likeCount = likeCount;
  }

  public SyndEntry getSyndEntry() {
    return syndEntry;
  }

  public void setSyndEntry(SyndEntry syndEntry) {
    this.syndEntry = syndEntry;
  }

}
