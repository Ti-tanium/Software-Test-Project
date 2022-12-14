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

import static org.assertj.core.util.Dates.millisecondOf;

import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;

import java.text.*;
import java.util.Date;

import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * Tests for <code>{@link Dates#millisecondOf(Date)}</code>.
 * 
 * @author Joel Costigliola
 */
public class Dates_millisecondOf_Test {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_return_millisecond_of_date() throws ParseException{String dateAsString="26/08/1994T22:35:17:29";Date date=new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss:SS").parse(dateAsString);assertEquals(29,millisecondOf(date));}

}
