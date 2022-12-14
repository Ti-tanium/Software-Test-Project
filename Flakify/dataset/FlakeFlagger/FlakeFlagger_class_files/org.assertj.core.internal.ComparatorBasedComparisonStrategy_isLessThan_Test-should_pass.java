/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.internal;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.junit.Test;

/**
 * Tests for {@link ComparatorBasedComparisonStrategy#isLessThan(Object, Object)}.
 * 
 * @author Joel Costigliola
 */
public class ComparatorBasedComparisonStrategy_isLessThan_Test extends AbstractTest_ComparatorBasedComparisonStrategy {

  @Test
  public void should_pass() {
    String string = "string";
    String greaterString = "STRINGA";
    assertTrue(caseInsensitiveComparisonStrategy.isLessThan(string, greaterString));
    assertFalse(caseInsensitiveComparisonStrategy.isLessThan(greaterString, string));
    assertFalse(caseInsensitiveComparisonStrategy.isLessThan(string, string));
    assertFalse(caseInsensitiveComparisonStrategy.isLessThan(string, "STRING"));
    String lowerString = "stringA";
    assertTrue(caseInsensitiveComparisonStrategy.isLessThan(string, lowerString));
    assertFalse(caseInsensitiveComparisonStrategy.isLessThan(lowerString, string));
  }

}
