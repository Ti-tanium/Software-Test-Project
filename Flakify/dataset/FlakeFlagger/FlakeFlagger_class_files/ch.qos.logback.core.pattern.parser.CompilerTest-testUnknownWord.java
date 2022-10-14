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
package ch.qos.logback.core.pattern.parser;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.pattern.Converter123;
import ch.qos.logback.core.pattern.ConverterHello;
import ch.qos.logback.core.status.StatusChecker;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CompilerTest {

  Map<String, String> converterMap = new HashMap<String, String>();
  Context context = new ContextBase();

  @Before
  public void setUp() {
    converterMap.put("OTT", Converter123.class.getName());
    converterMap.put("hello", ConverterHello.class.getName());
    converterMap.putAll(Parser.DEFAULT_COMPOSITE_CONVERTER_MAP);
  }


  String write(final Converter<Object> head, Object event) {
    StringBuilder buf = new StringBuilder();
    Converter<Object> c = head;
    while (c != null) {
      c.write(buf, event);
      c = c.getNext();
    }
    return buf.toString();
  }

  @Test public void testUnknownWord() throws Exception{Parser<Object> p=new Parser<Object>("%unknown");p.setContext(context);Node t=p.parse();p.compile(t,converterMap);StatusChecker checker=new StatusChecker(context.getStatusManager());checker.assertContainsMatch("\\[unknown] is not a valid conversion word");}

}
