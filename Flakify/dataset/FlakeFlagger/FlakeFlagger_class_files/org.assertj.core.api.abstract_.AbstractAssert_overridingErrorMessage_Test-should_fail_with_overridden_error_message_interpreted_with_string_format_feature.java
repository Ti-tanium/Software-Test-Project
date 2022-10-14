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
package org.assertj.core.api.abstract_;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.ConcreteAssert;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for <code>{@link AbstractAssert#overridingErrorMessage(String, Object...)}</code>.
 * 
 * @author Joel Costigliola
 */
public class AbstractAssert_overridingErrorMessage_Test {

  private ConcreteAssert assertions;

  @Before
  public void setUp() {
    assertions = new ConcreteAssert(6L);
  }

  @Test public void should_fail_with_overridden_error_message_interpreted_with_string_format_feature(){try {long expected=8L;assertions.overridingErrorMessage("new error message, expected value was : '%s'",expected).isEqualTo(expected);} catch (AssertionError err){assertEquals("new error message, expected value was : '8'",err.getMessage());return;}failBecauseExpectedAssertionErrorWasNotThrown();}
}