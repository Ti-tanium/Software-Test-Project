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

import static org.assertj.core.error.ShouldBeAfterYear.shouldBeAfterYear;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Dates;
import org.assertj.core.internal.DatesBaseTest;
import org.junit.Test;

/**
 * Tests for <code>{@link Dates#assertIsAfterYear(AssertionInfo, Date, int)}</code>.
 * 
 * @author Joel Costigliola
 */
public class Dates_assertIsAfterYear_Test extends DatesBaseTest {

  @Test public void should_fail_if_actual_year_is_equals_to_given_year(){AssertionInfo info=someInfo();parseDate("2011-01-01");int year=2011;try {dates.assertIsAfterYear(info,actual,year);} catch (AssertionError e){verify(failures).failure(info,shouldBeAfterYear(actual,year));return;}failBecauseExpectedAssertionErrorWasNotThrown();}

}
