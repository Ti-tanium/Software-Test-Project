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
package org.assertj.core.internal.characters;

import static org.assertj.core.error.ShouldBeGreater.shouldBeGreater;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Characters;
import org.assertj.core.internal.CharactersBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Characters#assertGreaterThan(AssertionInfo, Character, char)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Characters_assertGreaterThan_Test extends CharactersBaseTest {

  

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_fail_if_actual_is_equal_to_other_according_to_custom_comparison_strategy() {
    AssertionInfo someInfo = someInfo();
    try {
      charactersWithCaseInsensitiveComparisonStrategy.assertGreaterThan(someInfo, 'B', 'b');
    } catch (AssertionError e) {
      verify(failures).failure(someInfo, shouldBeGreater('B', 'b', caseInsensitiveComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
