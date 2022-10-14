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
package org.assertj.core.internal.iterables;

import static org.assertj.core.error.ShouldStartWith.shouldStartWith;
import static org.assertj.core.test.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.test.ObjectArrays.emptyArray;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;

import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Iterables;
import org.assertj.core.internal.IterablesBaseTest;

/**
 * Tests for <code>{@link Iterables#assertStartsWith(AssertionInfo, Collection, Object[])}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Iterables_assertStartsWith_Test extends IterablesBaseTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    actual = newArrayList("Yoda", "Luke", "Leia", "Obi-Wan");
  }

  private void verifyFailureThrownWhenSequenceNotFound(AssertionInfo info, Object[] sequence) {
    verify(failures).failure(info, shouldStartWith(actual, sequence));
  }

  private Iterable<Integer> infiniteListOfNumbers() {

    return new Iterable<Integer>() {

      int number = 1;

      @Override
      public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

          @Override
          public boolean hasNext() {
            return true;
          }

          @Override
          public Integer next() {
            return number++;
          }

          @Override
          public void remove() {
          }
        };
      }
    };
  }

  // ------------------------------------------------------------------------------------------------------------------
  // tests using a custom comparison strategy
  // ------------------------------------------------------------------------------------------------------------------

  @Test
  public void should_fail_if_actual_does_not_start_with_sequence_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    Object[] sequence = { "Han", "C-3PO" };
    try {
      iterablesWithCaseInsensitiveComparisonStrategy.assertStartsWith(info, actual, sequence);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldStartWith(actual, sequence, comparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
