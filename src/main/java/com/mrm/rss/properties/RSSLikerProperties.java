package com.mrm.rss.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("rssLikerProperties")
public class RSSLikerProperties {

  @Value("${xml.file.repository}")
  private String xmlFileRepository;

  public String getXmlFileRepository() {
    return xmlFileRepository;
  }

  public void setXmlFileRepository(String xmlFileRepository) {
    this.xmlFileRepository = xmlFileRepository;
  }

}
