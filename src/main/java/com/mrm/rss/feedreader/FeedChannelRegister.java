package com.mrm.rss.feedreader;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.integration.channel.ChannelInterceptor;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.stereotype.Component;

/**
 * Class to register the Message Handler and to set the interceptor
 * 
 * @author ebrigand
 * 
 */
@Component
public class FeedChannelRegister {

  @Resource
  private FeedMessageHandler feedMessageHandler;

  @Resource(name = "feedChannel")
  private DirectChannel feedChannel;

  @Resource(name = "feedChannelInterceptor")
  private ChannelInterceptor feedChannelInterceptor;

  @PostConstruct
  public void afterPropertiesSet() throws Exception {
    feedChannel.setInterceptors(Arrays.asList(feedChannelInterceptor));
    feedChannel.subscribe(feedMessageHandler);
  }

}
