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
package ch.qos.logback.core.joran.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

import ch.qos.logback.core.testUtil.FileTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.joran.TrivialConfigurator;
import ch.qos.logback.core.joran.action.ext.StackAction;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusChecker;
import ch.qos.logback.core.testUtil.RandomUtil;
import ch.qos.logback.core.util.CoreTestConstants;
import ch.qos.logback.core.util.StatusPrinter;

public class IncludeActionTest {

  final static String INCLUDE_KEY = "includeKey";
  final static String SUB_FILE_KEY = "subFileKey";
  final static String SECOND_FILE_KEY = "secondFileKey";

  Context context = new ContextBase();
  StatusChecker statusChecker = new StatusChecker(context);
  TrivialConfigurator tc;

  static final String INCLUSION_DIR_PREFIX = CoreTestConstants.JORAN_INPUT_PREFIX
      + "inclusion/";

  static final String TOP_BY_FILE = INCLUSION_DIR_PREFIX + "topByFile.xml";

  static final String TOP_OPTIONAL = INCLUSION_DIR_PREFIX + "topOptional.xml";

  static final String INTERMEDIARY_FILE = INCLUSION_DIR_PREFIX
      + "intermediaryByFile.xml";

  static final String SUB_FILE = INCLUSION_DIR_PREFIX + "subByFile.xml";

  static final String MULTI_INCLUDE_BY_FILE = INCLUSION_DIR_PREFIX
      + "multiIncludeByFile.xml";

  static final String SECOND_FILE = INCLUSION_DIR_PREFIX + "second.xml";

  static final String TOP_BY_URL = INCLUSION_DIR_PREFIX + "topByUrl.xml";

  static final String INCLUDE_BY_RESOURCE = INCLUSION_DIR_PREFIX
      + "topByResource.xml";

  static final String INCLUDED_FILE = INCLUSION_DIR_PREFIX + "included.xml";
  static final String URL_TO_INCLUDE = "file:./" + INCLUDED_FILE;

  static final String INVALID = INCLUSION_DIR_PREFIX + "invalid.xml";

  static final String INCLUDED_AS_RESOURCE = "asResource/joran/inclusion/includedAsResource.xml";

  int diff = RandomUtil.getPositiveInt();

  StackAction stackAction = new StackAction();

  @Before
  public void setUp() throws Exception {
    FileTestUtil.makeTestOutputDir();
    HashMap<ElementSelector, Action> rulesMap = new HashMap<ElementSelector, Action>();
    rulesMap.put(new ElementSelector("x"), new NOPAction());
    rulesMap.put(new ElementSelector("x/include"), new IncludeAction());
    rulesMap.put(new ElementSelector("x/stack"), stackAction);

    tc = new TrivialConfigurator(rulesMap);
    tc.setContext(context);
  }

  String copyToTemp(String in) throws IOException {
    FileInputStream fis = new FileInputStream(in);
    String out = CoreTestConstants.OUTPUT_DIR_PREFIX + "out" + diff;
    FileOutputStream fos = new FileOutputStream(out);
    int b;
    while ((b = fis.read()) != -1) {
      fos.write(b);
    }
    fis.close();
    fos.close();
    return out;
  }

  @Test public void multiInclude() throws JoranException{System.setProperty(INCLUDE_KEY,INCLUDED_FILE);System.setProperty(SECOND_FILE_KEY,SECOND_FILE);tc.doConfigure(MULTI_INCLUDE_BY_FILE);verifyConfig(new String[]{"IA","IB","SECOND"});}

  void verifyConfig(String[] expected) {
    Stack<String> witness = new Stack<String>();
    witness.addAll(Arrays.asList(expected));
    assertEquals(witness, stackAction.getStack());
  }

}
