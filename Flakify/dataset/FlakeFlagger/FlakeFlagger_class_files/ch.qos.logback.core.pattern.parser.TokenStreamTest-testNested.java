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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.core.spi.ScanException;
import org.junit.Test;

import ch.qos.logback.core.pattern.util.AlmostAsIsEscapeUtil;

public class TokenStreamTest {

  @Test
  public void testNested() throws ScanException {
    List tl = new TokenStream("%(%a%(%b))").tokenize();
    List<Token> witness = new ArrayList<Token>();
    witness.add(Token.PERCENT_TOKEN);
    witness.add(Token.BARE_COMPOSITE_KEYWORD_TOKEN);
    witness.add(Token.PERCENT_TOKEN);
    witness.add(new Token(Token.SIMPLE_KEYWORD, "a"));
    witness.add(Token.PERCENT_TOKEN);
    witness.add(Token.BARE_COMPOSITE_KEYWORD_TOKEN);
    witness.add(Token.PERCENT_TOKEN);
    witness.add(new Token(Token.SIMPLE_KEYWORD, "b"));
    witness.add(Token.RIGHT_PARENTHESIS_TOKEN);
    witness.add(Token.RIGHT_PARENTHESIS_TOKEN);

    assertEquals(witness, tl);

  }
}