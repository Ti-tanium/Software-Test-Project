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
package ch.qos.logback.classic.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.ClassicTestConstants;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.net.mock.MockSyslogServer;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.net.SyslogConstants;
import ch.qos.logback.core.recovery.RecoveryCoordinator;
import ch.qos.logback.core.testUtil.RandomUtil;
import ch.qos.logback.core.util.StatusPrinter;

public class SyslogAppenderTest {

  LoggerContext lc = new LoggerContext();
  SyslogAppender sa = new SyslogAppender();
  MockSyslogServer mockServer;
  String loggerName = this.getClass().getName();
  Logger logger = lc.getLogger(loggerName);

  @Before
  public void setUp() throws Exception {
    lc.setName("test");
    sa.setContext(lc);
  }

  @Test
  public void basic() throws InterruptedException {

    setMockServerAndConfigure(1);
    String logMsg = "hello";
    logger.debug(logMsg);

    // wait max 2 seconds for mock server to finish. However, it should
    // much sooner than that.
    mockServer.join(8000);

    assertTrue(mockServer.isFinished());
    assertEquals(1, mockServer.getMessageList().size());
    String msg = mockServer.getMessageList().get(0);

    String threadName = Thread.currentThread().getName();

    String expected = "<"
        + (SyslogConstants.LOG_MAIL + SyslogConstants.DEBUG_SEVERITY) + ">";
    assertTrue(msg.startsWith(expected));

    String first = "<\\d{2}>\\w{3} \\d{2} \\d{2}(:\\d{2}){2} [\\w.-]* ";
    checkRegexMatch(msg, first + "\\[" + threadName + "\\] " + loggerName + " "
        + logMsg);

  }
  
  private void checkRegexMatch(String s, String regex) {
    assertTrue("The string [" + s + "] did not match regex [" + regex + "]", s
        .matches(regex));
  }
}
