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
package org.assertj.core.api.date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ErrorMessages.dateToCompareActualWithIsNull;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.Dates.parseDatetime;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.assertj.core.api.DateAssertBaseTest;
import org.assertj.core.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link org.assertj.core.api.DateAssert#hasSameTimeAs(String)} </code>.
 *
 * @author Michal Kordas
 */
public class DateAssert_hasSameTimeAsDateInString_Test extends DateAssertBaseTest {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_throw_exception_when_date_is_null(){thrown.expectNullPointerException(dateToCompareActualWithIsNull());assertThat(new Date()).hasSameTimeAs((String)null);}
}
