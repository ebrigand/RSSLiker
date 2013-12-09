package com.mrm.rss.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RssLikerProperties {

  @Value("${xml.file.repository}")
  private String xmlFileRepository;

  public String getXmlFileRepository() {
    return xmlFileRepository;
  }

  public void setXmlFileRepository(String xmlFileRepository) {
    this.xmlFileRepository = xmlFileRepository;
  }

}
