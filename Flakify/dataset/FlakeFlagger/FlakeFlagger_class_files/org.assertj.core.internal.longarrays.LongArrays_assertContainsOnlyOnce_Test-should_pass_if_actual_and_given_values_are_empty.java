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
package org.assertj.core.internal.longarrays;

import static org.assertj.core.error.ShouldContainsOnlyOnce.shouldContainsOnlyOnce;
import static org.assertj.core.test.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.test.LongArrays.arrayOf;
import static org.assertj.core.test.LongArrays.emptyArray;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.LongArrays;
import org.assertj.core.internal.LongArraysBaseTest;
import org.junit.Test;

/**
 * Tests for <code>{@link LongArrays#assertContainsOnlyOnce(AssertionInfo, long[], long[])}</code>.
 * 
 * @author William Delanoue
 */
public class LongArrays_assertContainsOnlyOnce_Test extends LongArraysBaseTest {

  @Test
  public void should_pass_if_actual_and_given_values_are_empty() {
    actual = emptyArray();
    arrays.assertContainsOnlyOnce(someInfo(), actual, emptyArray());
  }
}
