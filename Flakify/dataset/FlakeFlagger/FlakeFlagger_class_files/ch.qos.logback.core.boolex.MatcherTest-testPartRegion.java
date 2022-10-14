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
package ch.qos.logback.core.boolex;

import junit.framework.TestCase;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;

public class MatcherTest extends TestCase {

  Context context;
  Matcher matcher;
  
  public void testPartRegion() throws Exception {
	matcher.setRegex("test");
	matcher.start();
	assertTrue(matcher.matches("test"));
	assertTrue(matcher.matches("xxxxtest"));
	assertTrue(matcher.matches("testxxxx"));
	assertTrue(matcher.matches("xxxxtestxxxx"));
}
}
