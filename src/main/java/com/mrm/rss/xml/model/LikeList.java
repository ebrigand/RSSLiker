package com.mrm.rss.xml.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for a LikeList object HACK: intermediate object because a map of value
 * as list is not possible when saved in XML
 * 
 * @author ebrigand
 * 
 */
@XmlRootElement
public class LikeList implements Serializable {

  private static final long serialVersionUID = 9128405402974907L;

  @XmlAttribute
  private final List<Like> likes = new ArrayList<Like>();

  public List<Like> getLikes() {
    return likes;
  }

}
