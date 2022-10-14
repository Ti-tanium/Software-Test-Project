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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.spi.ScanException;
import ch.qos.logback.core.status.StatusChecker;
import org.junit.Test;

import ch.qos.logback.core.pattern.FormatInfo;

public class ParserTest {

  String BARE = Token.BARE_COMPOSITE_KEYWORD_TOKEN.getValue().toString();
  Context context = new ContextBase();


  @Test public void testNested() throws Exception{{Parser p=new Parser("%top %(%child%(%h))");Node t=p.parse();Node witness=new SimpleKeywordNode("top");Node w=witness.next=new Node(Node.LITERAL," ");CompositeNode composite=new CompositeNode(BARE);w=w.next=composite;Node child=new SimpleKeywordNode("child");composite.setChildNode(child);composite=new CompositeNode(BARE);child.next=composite;composite.setChildNode(new SimpleKeywordNode("h"));assertEquals(witness,t);}}

}