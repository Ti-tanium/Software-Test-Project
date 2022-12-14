/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.classic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class MessageFormattingTest  {

  LoggerContext lc;
  ListAppender<ILoggingEvent> listAppender;

  @Before
  public void setUp() {
    lc = new LoggerContext();
    Logger logger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
    listAppender = new ListAppender<ILoggingEvent>();
    listAppender.setContext(lc);
    listAppender.start();
    logger.addAppender(listAppender);
  }

  @Test public void testNoFormatting(){Logger logger=lc.getLogger(Logger.ROOT_LOGGER_NAME);logger.debug("test",new Integer(12),new Integer(13));ILoggingEvent event=(ILoggingEvent)listAppender.list.get(0);assertEquals("test",event.getFormattedMessage());}

}
