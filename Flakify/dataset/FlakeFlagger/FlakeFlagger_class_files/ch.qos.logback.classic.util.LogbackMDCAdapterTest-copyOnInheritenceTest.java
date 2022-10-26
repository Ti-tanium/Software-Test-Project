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
package ch.qos.logback.classic.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import ch.qos.logback.core.testUtil.RandomUtil;

public class LogbackMDCAdapterTest {

  final static String A_SUFFIX = "A_SUFFIX";
  final static String B_SUFFIX = "B_SUFFIX";

  int diff = RandomUtil.getPositiveInt();

  private final LogbackMDCAdapter mdcAdapter = new LogbackMDCAdapter();


  

  // =================================================

  /**
 * Test that LogbackMDCAdapter copies its hashmap when a child thread inherits it.
 * @throws InterruptedException
 */@Test public void copyOnInheritenceTest() throws InterruptedException{CountDownLatch countDownLatch=new CountDownLatch(1);String firstKey="x" + diff;String secondKey="o" + diff;mdcAdapter.put(firstKey,firstKey + A_SUFFIX);ChildThread childThread=new ChildThread(mdcAdapter,firstKey,secondKey,countDownLatch);childThread.start();countDownLatch.await();mdcAdapter.put(firstKey,firstKey + B_SUFFIX);childThread.join();assertNull(mdcAdapter.get(secondKey));assertTrue(childThread.successful);Map<String, String> parentHM=getMapFromMDCAdapter(mdcAdapter);assertTrue(parentHM != childThread.childHM);HashMap<String, String> parentHMWitness=new HashMap<String, String>();parentHMWitness.put(firstKey,firstKey + B_SUFFIX);assertEquals(parentHMWitness,parentHM);HashMap<String, String> childHMWitness=new HashMap<String, String>();childHMWitness.put(firstKey,firstKey + A_SUFFIX);childHMWitness.put(secondKey,secondKey + A_SUFFIX);assertEquals(childHMWitness,childThread.childHM);}

  Map<String, String> getMapFromMDCAdapter(LogbackMDCAdapter lma) {
    InheritableThreadLocal<Map<String, String>> copyOnInheritThreadLocal = lma.copyOnInheritThreadLocal;
    return copyOnInheritThreadLocal.get();
  }

  // ==========================    various thread classes
  class ChildThreadForMDCAdapter extends Thread {

    LogbackMDCAdapter logbackMDCAdapter;
    boolean successul;
    Map<String, String> childHM;

    ChildThreadForMDCAdapter(LogbackMDCAdapter logbackMDCAdapter) {
      this.logbackMDCAdapter = logbackMDCAdapter;
    }

    @Override
    public void run() {
      childHM = getMapFromMDCAdapter(logbackMDCAdapter);
      logbackMDCAdapter.get("");
      successul = true;
    }
  }


  class ChildThread extends Thread {

    LogbackMDCAdapter logbackMDCAdapter;
    String firstKey;
    String secondKey;
    boolean successful;
    Map<String, String> childHM;
    CountDownLatch countDownLatch;

    ChildThread(LogbackMDCAdapter logbackMDCAdapter) {
      this(logbackMDCAdapter, null, null);
    }

    ChildThread(LogbackMDCAdapter logbackMDCAdapter, String firstKey, String secondKey) {
      this(logbackMDCAdapter, firstKey, secondKey, null);
    }

    ChildThread(LogbackMDCAdapter logbackMDCAdapter, String firstKey, String secondKey, CountDownLatch countDownLatch) {
      super("chil");
      this.logbackMDCAdapter  = logbackMDCAdapter;
      this.firstKey = firstKey;
      this.secondKey = secondKey;
      this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      logbackMDCAdapter.put(secondKey, secondKey + A_SUFFIX);
      assertNotNull(logbackMDCAdapter.get(firstKey));
      assertEquals(firstKey + A_SUFFIX, logbackMDCAdapter.get(firstKey));
      if (countDownLatch != null) countDownLatch.countDown();
      assertEquals(secondKey + A_SUFFIX, logbackMDCAdapter.get(secondKey));
      successful = true;
      childHM = getMapFromMDCAdapter(logbackMDCAdapter);
    }
  }
}