/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 * or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.core.rolling;

import ch.qos.logback.core.encoder.EchoEncoder;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SizeAndTimeBasedFNATP_Test extends ScaffoldingForRollingTests {
  private SizeAndTimeBasedFNATP sizeAndTimeBasedFNATP = null;
  private RollingFileAppender<Object> rfa1 = new RollingFileAppender<Object>();
  private TimeBasedRollingPolicy<Object> tbrp1 = new TimeBasedRollingPolicy<Object>();
  private RollingFileAppender<Object> rfa2 = new RollingFileAppender<Object>();
  private TimeBasedRollingPolicy<Object> tbrp2 = new TimeBasedRollingPolicy<Object>();

  private EchoEncoder<Object> encoder = new EchoEncoder<Object>();
  int fileSize = 0;
  int fileIndexCounter = 0;
  int sizeThreshold = 0;


  @Before
  public void setUp() {
    super.setUp();
  }

  private void initRollingFileAppender(RollingFileAppender<Object> rfa, String filename) {
    rfa.setContext(context);
    rfa.setEncoder(encoder);
    if (filename != null) {
      rfa.setFile(filename);
    }
  }

  private void initPolicies(RollingFileAppender<Object> rfa,
                            TimeBasedRollingPolicy<Object> tbrp,
                            String filenamePattern, int sizeThreshold,
                            long givenTime, long lastCheck) {
    sizeAndTimeBasedFNATP = new SizeAndTimeBasedFNATP<Object>();
    tbrp.setContext(context);
    sizeAndTimeBasedFNATP.setMaxFileSize("" + sizeThreshold);
    tbrp.setTimeBasedFileNamingAndTriggeringPolicy(sizeAndTimeBasedFNATP);
    tbrp.setFileNamePattern(filenamePattern);
    tbrp.setParent(rfa);
    tbrp.timeBasedFileNamingAndTriggeringPolicy.setCurrentTime(givenTime);
    rfa.setRollingPolicy(tbrp);
    tbrp.start();
    rfa.start();
  }

  private void addExpectedFileNamedIfItsTime(String randomOutputDir, String testId, String msg, String compressionSuffix) {
    fileSize = fileSize + msg.getBytes().length;
    if (passThresholdTime(nextRolloverThreshold)) {
      fileIndexCounter = 0;
      fileSize = 0;
      addExpectedFileName_ByFileIndexCounter(randomOutputDir, testId, getMillisOfCurrentPeriodsStart(),
              fileIndexCounter, compressionSuffix);
      recomputeRolloverThreshold(currentTime);
      return;
    }

    // windows can delay file size changes, so we only allow for
    // fileIndexCounter 0
    if ((fileIndexCounter < 1) && fileSize > sizeThreshold) {
      addExpectedFileName_ByFileIndexCounter(randomOutputDir, testId, getMillisOfCurrentPeriodsStart(),
              fileIndexCounter, compressionSuffix);
      fileIndexCounter = fileIndexCounter + 1;
      fileSize = 0;
    }
  }


  void generic(String testId, String stem, boolean withSecondPhase, String compressionSuffix) throws IOException, InterruptedException, ExecutionException {
    String file = (stem != null) ? randomOutputDir + stem : null;
    initRollingFileAppender(rfa1, file);
    sizeThreshold = 300;
    initPolicies(rfa1, tbrp1, randomOutputDir + testId + "-%d{" + DATE_PATTERN_WITH_SECONDS + "}-%i.txt" + compressionSuffix, sizeThreshold, currentTime, 0);
    addExpectedFileName_ByFileIndexCounter(randomOutputDir, testId, getMillisOfCurrentPeriodsStart(), fileIndexCounter, compressionSuffix);
    incCurrentTime(100);
    tbrp1.timeBasedFileNamingAndTriggeringPolicy.setCurrentTime(currentTime);
    int runLength = 100;
    String prefix = "Hello -----------------";

    for (int i = 0; i < runLength; i++) {
      String msg = prefix + i;
      rfa1.doAppend(msg);
      addExpectedFileNamedIfItsTime(randomOutputDir, testId, msg, compressionSuffix);
      incCurrentTime(20);
      tbrp1.timeBasedFileNamingAndTriggeringPolicy.setCurrentTime(currentTime);
      add(tbrp1.future);
    }

    if (withSecondPhase) {
      secondPhase(testId, file, stem, compressionSuffix, runLength, prefix);
      runLength = runLength * 2;
    }

    if (stem != null)
      massageExpectedFilesToCorresponToCurrentTarget(file, true);

    Thread.yield();
    // wait for compression to finish
    waitForJobsToComplete();

    StatusPrinter.print(context);
    existenceCheck(expectedFilenameList);
    sortedContentCheck(randomOutputDir, runLength, prefix);
  }


  void secondPhase(String testId, String file, String stem, String compressionSuffix, int runLength, String prefix) {
    rfa1.stop();

    if (stem != null) {
      File f = new File(file);
      f.setLastModified(currentTime);
    }

    StatusManager sm = context.getStatusManager();
    sm.add(new InfoStatus("Time when rfa1 is stopped: " + new Date(currentTime), this));
    sm.add(new InfoStatus("currentTime%1000=" + (currentTime % 1000), this));

    initRollingFileAppender(rfa2, file);
    initPolicies(rfa2, tbrp2, randomOutputDir + testId + "-%d{"
            + DATE_PATTERN_WITH_SECONDS + "}-%i.txt" + compressionSuffix, sizeThreshold, currentTime, 0);

    for (int i = runLength; i < runLength * 2; i++) {
      incCurrentTime(100);
      tbrp2.timeBasedFileNamingAndTriggeringPolicy.setCurrentTime(currentTime);
      String msg = prefix + i;
      rfa2.doAppend(msg);
      addExpectedFileNamedIfItsTime(randomOutputDir, testId, msg, compressionSuffix);
    }
  }

  static final boolean FIRST_PHASE_ONLY = false;
  static final boolean WITH_SECOND_PHASE = true;
  static String DEFAULT_COMPRESSION_SUFFIX = "";

  @Test public void withGZCompression_FileBlank_NoRestart_6() throws Exception{generic("test6",null,FIRST_PHASE_ONLY,".gz");}
}
