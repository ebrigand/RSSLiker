package com.mrm.rss.xml.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for Story object
 * 
 * @author ebrigand
 * 
 */
@XmlRootElement
public class Story implements Serializable {

  private static final long serialVersionUID = -2045276999414524752L;

  @XmlAttribute
  private Integer count;

  @XmlAttribute
  private String uriWithoutSpecialChars;

  public Story() {
  }

  public String getUriWithoutSpecialChars() {
    return uriWithoutSpecialChars;
  }

  public void setUriWithoutSpecialChars(String uriWithoutSpecialChars) {
    this.uriWithoutSpecialChars = uriWithoutSpecialChars;
  }

  public Story(String uriWithoutSpecialChars) {
    this.uriWithoutSpecialChars = uriWithoutSpecialChars;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((uriWithoutSpecialChars == null) ? 0 : uriWithoutSpecialChars.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Story))
      return false;
    Story other = (Story) obj;
    if (uriWithoutSpecialChars == null) {
      if (other.uriWithoutSpecialChars != null)
        return false;
    } else if (!uriWithoutSpecialChars.equals(other.uriWithoutSpecialChars))
      return false;
    return true;
  }

}
