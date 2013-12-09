package com.mrm.rss.xml.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for Repo object. Class used to save the like and story values in an XML
 * file
 * 
 * @author ebrigand
 * 
 */
@XmlRootElement
public class Repo {

  @XmlAttribute
  private final Map<String, LikeList> likeMap = new HashMap<String, LikeList>();

  @XmlAttribute
  private final List<Story> stories = new ArrayList<Story>();

  public Map<String, LikeList> getLikeMap() {
    return likeMap;
  }

  public List<Story> getStories() {
    return stories;
  }
}
