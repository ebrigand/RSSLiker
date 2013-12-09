package com.mrm.rss.feedreader;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * Feed Channel interceptor, used to hold the syndEntry when the channel read a
 * SyndEntry
 * 
 * @author ebrigand
 * 
 */
@Component
public class FeedChannelInterceptor extends ChannelInterceptorAdapter {

  private final static Set<SyndEntry> entries = new LinkedHashSet<SyndEntry>();

  @Override
  @SuppressWarnings("unchecked")
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    Message<SyndEntry> messageSyndEntryImpl = (Message<SyndEntry>) message;
    entries.add(messageSyndEntryImpl.getPayload());
    return super.preSend(message, channel);
  }

  public Set<SyndEntry> getEntries() {
    return Collections.unmodifiableSet(entries);
  }
}