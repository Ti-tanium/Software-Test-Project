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
package org.assertj.core.internal.doublearrays;

import static org.assertj.core.error.ShouldContainExactly.elementsDifferAtIndex;
import static org.assertj.core.error.ShouldContainExactly.shouldContainExactly;
import static org.assertj.core.test.DoubleArrays.arrayOf;
import static org.assertj.core.test.DoubleArrays.emptyArray;
import static org.assertj.core.test.ErrorMessages.valuesToLookForIsNull;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.DoubleArrays;
import org.assertj.core.internal.DoubleArraysBaseTest;
import org.junit.Test;

/**
 * Tests for <code>{@link DoubleArrays#assertContainsExactly(AssertionInfo, double[], double[])}</code>.
 */
public class DoubleArrays_assertContainsExactly_Test extends DoubleArraysBaseTest {

  @Test
  public void should_fail_if_actual_does_not_contain_given_values_exactly() {
	AssertionInfo info = someInfo();
	double[] expected = { 6d, 8d, 20d };
	try {
	  arrays.assertContainsExactly(info, actual, expected);
	} catch (AssertionError e) {
	  verify(failures).failure(info,
		                       shouldContainExactly(actual, expected, newLinkedHashSet(20d), newLinkedHashSet(10d)));
	  return;
	}
	failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
