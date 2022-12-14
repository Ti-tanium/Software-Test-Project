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
package org.assertj.core.internal.dates;

import static org.assertj.core.error.ShouldBeInThePast.shouldBeInThePast;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Dates.monthOf;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Date;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Dates;
import org.assertj.core.internal.DatesBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Dates#assertIsInThePast(AssertionInfo, Date)}</code>.
 * 
 * @author Joel Costigliola
 */
public class Dates_assertIsInThePast_Test extends DatesBaseTest {

  @Test
  public void should_fail_if_actual_is_today_according_to_custom_comparison_strategy() {
    AssertionInfo info = someInfo();
    try {
      // we want actual to "now" according to our comparison strategy (that compares only month and year)
      // => if we are at the end of the month we subtract one day instead of adding one
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_MONTH, 1);
      Date tomorrow = cal.getTime();
      cal.add(Calendar.DAY_OF_MONTH, -2);
      Date yesterday = cal.getTime();
      actual = monthOf(tomorrow) == monthOf(new Date()) ? tomorrow : yesterday;
      datesWithCustomComparisonStrategy.assertIsInThePast(info, actual);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldBeInThePast(actual, yearAndMonthComparisonStrategy));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }

}
