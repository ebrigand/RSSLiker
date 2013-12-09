package com.mrm.rss.feedreader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.stereotype.Component;

import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * (Class copied from the web) Handler for processing inc oming RSS message
 * payloads.
 * 
 * @author gregsmith
 */
@Component
public class FeedMessageHandler implements MessageHandler {

  private static final Log log = LogFactory.getLog(FeedMessageHandler.class);

  public FeedMessageHandler() {
    log.debug("Created FeedMessageHandler");
  }

  /**
   * Handles the message if possible. If the handler cannot deal with the
   * message this will result in a <code>MessageRejectedException</code> e.g. in
   * case of a Selective Consumer. When a consumer tries to handle a message,
   * but fails to do so, a <code>MessageHandlingException</code> is thrown. In
   * the last case it is recommended to treat the message as tainted and go into
   * an error scenario.
   * <p/>
   * When the handling results in a failure of another message being sent (e.g.
   * a "reply" message), that failure will trigger a
   * <code>MessageDeliveryException</code>.
   * 
   * @param message
   *          the message to be handled
   * @throws org.springframework.integration.MessageRejectedException
   *           if the handler doesn't accept the message
   * @throws org.springframework.integration.MessageHandlingException
   *           when something fails during the handling
   * @throws org.springframework.integration.MessageDeliveryException
   *           when this handler failed to deliver the reply related to the
   *           handling of the message
   */
  @Override
  public void handleMessage(Message<?> message) throws MessagingException {
    SyndEntryImpl syndFeed = (SyndEntryImpl) message.getPayload();
    log.debug("Message: " + syndFeed.getTitle() + " created " + syndFeed.getPublishedDate());
  }

}