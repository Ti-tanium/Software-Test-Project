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
package org.assertj.core.internal.objectarrays;

import static org.assertj.core.error.ShouldContainSubsequence.shouldContainSubsequence;
import static org.assertj.core.test.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.test.ObjectArrays.emptyArray;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ObjectArrays;
import org.assertj.core.internal.ObjectArraysBaseTest;
import org.junit.Test;

/**
 * Tests for <code>{@link ObjectArrays#assertContainsSubsequence(AssertionInfo, Object[], Object[])}</code>.
 * 
 * @author Marcin Mikosik
 */
public class ObjectArrays_assertContainsSubsequence_Test extends ObjectArraysBaseTest {

  @Test
  public void should_pass_if_actual_and_sequence_are_equal() {
    arrays.assertContainsSubsequence(someInfo(), actual, array("Yoda", "Luke", "Leia", "Obi-Wan"));
  }

  private void verifyFailureThrownWhenSubsequenceNotFound(AssertionInfo info, Object[] sequence) {
    verify(failures).failure(info, shouldContainSubsequence(actual, sequence));
  }
}
