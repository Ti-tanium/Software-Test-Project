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
package org.assertj.core.util;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.Dates.formatTimeDifference;
import static org.assertj.core.util.Dates.parseDatetimeWithMs;

import java.text.ParseException;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;

import org.assertj.core.test.ExpectedException;

/**
 * Tests for <code>{@link Dates#timeDifference(java.util.Date, java.util.Date)}</code>.
 *
 * @author Joel Costigliola
 */
public class Dates_formatTimeDifference_Test {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_throws_IllegalArgumentException_if_first_date_parameter_is_null(){thrown.expectIllegalArgumentException("Expecting date parameter not to be null");formatTimeDifference(new Date(),null);}

}
