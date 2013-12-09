package com.mrm.rss.xml.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for a Like object
 * 
 * @author ebrigand
 * 
 */
@XmlRootElement
public class Like implements Serializable {

  private static final long serialVersionUID = 9128441405402974907L;

  @XmlAttribute
  private String accountName;

  @XmlAttribute
  private String uriWithoutSpecialChars;

  @XmlAttribute
  private Boolean isLike = false;

  public Like() {
  }

  public Like(String accountName, String uriWithoutSpecialChars) {
    this.accountName = accountName;
    this.uriWithoutSpecialChars = uriWithoutSpecialChars;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public Boolean getIsLike() {
    return isLike;
  }

  public void setIsLike(Boolean isLike) {
    this.isLike = isLike;
  }

  public String getUriWithoutSpecialChars() {
    return uriWithoutSpecialChars;
  }

  public void setUriWithoutSpecialChars(String uriWithoutSpecialChars) {
    this.uriWithoutSpecialChars = uriWithoutSpecialChars;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
    result = prime * result + ((uriWithoutSpecialChars == null) ? 0 : uriWithoutSpecialChars.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Like))
      return false;
    Like other = (Like) obj;
    if (accountName == null) {
      if (other.accountName != null)
        return false;
    } else if (!accountName.equals(other.accountName))
      return false;
    if (uriWithoutSpecialChars == null) {
      if (other.uriWithoutSpecialChars != null)
        return false;
    } else if (!uriWithoutSpecialChars.equals(other.uriWithoutSpecialChars))
      return false;
    return true;
  }

}
