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
package ch.qos.logback.core;

import ch.qos.logback.core.helpers.NOPAppender;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.testUtil.DelayingListAppender;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusChecker;
import ch.qos.logback.core.testUtil.NPEAppender;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ceki G&uuml;lc&uuml;
 * @author Torsten Juergeleit
 */
public class AsyncAppenderBaseTest {


  Context context = new ContextBase();
  AsyncAppenderBase<Integer> asyncAppenderBase = new AsyncAppenderBase<Integer>();
  LossyAsyncAppender lossyAsyncAppender = new LossyAsyncAppender();
  DelayingListAppender<Integer> delayingListAppender = new DelayingListAppender<Integer>();
  ListAppender<Integer> listAppender = new ListAppender<Integer>();
  OnConsoleStatusListener onConsoleStatusListener = new OnConsoleStatusListener();
  StatusChecker statusChecker = new StatusChecker(context);

  @Before
  public void setUp() {
    onConsoleStatusListener.setContext(context);
    context.getStatusManager().add(onConsoleStatusListener);
    onConsoleStatusListener.start();

    asyncAppenderBase.setContext(context);
    lossyAsyncAppender.setContext(context);

    listAppender.setContext(context);
    listAppender.setName("list");
    listAppender.start();

    delayingListAppender.setContext(context);
    delayingListAppender.setName("list");
    delayingListAppender.start();
  }

  @Test public void exceptionsShouldNotCauseHalting() throws InterruptedException{NPEAppender npeAppender=new NPEAppender<Integer>();npeAppender.setName("bad");npeAppender.setContext(context);npeAppender.start();asyncAppenderBase.addAppender(npeAppender);asyncAppenderBase.start();assertTrue(asyncAppenderBase.isStarted());for (int i=0;i < 10;i++)asyncAppenderBase.append(i);asyncAppenderBase.stop();assertFalse(asyncAppenderBase.isStarted());assertEquals(AppenderBase.ALLOWED_REPEATS,statusChecker.matchCount("Appender \\[bad\\] failed to append."));}

  private void verify(ListAppender la, int expectedSize) {
    assertFalse(la.isStarted());
    assertEquals(expectedSize, la.list.size());
    statusChecker.assertIsErrorFree();
    statusChecker.assertContainsMatch("Worker thread will flush remaining events before exiting.");
  }

  static class LossyAsyncAppender extends AsyncAppenderBase<Integer> {
    @Override
    protected boolean isDiscardable(Integer i) {
      return (i % 3 == 0);
    }
  }
}
