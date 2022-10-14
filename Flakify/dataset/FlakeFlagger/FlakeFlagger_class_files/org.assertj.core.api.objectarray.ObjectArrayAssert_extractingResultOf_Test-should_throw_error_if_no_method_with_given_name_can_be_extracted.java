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
package org.assertj.core.api.objectarray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.Arrays.array;

import org.assertj.core.api.ObjectArrayAssert;
import org.assertj.core.test.ExpectedException;
import org.assertj.core.test.FluentJedi;
import org.assertj.core.test.Name;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for: 
 * <ul>
 *  <li><code>{@link ObjectArrayAssert#extractingResultOf(String)}</code>,
 *  <li><code>{@link ObjectArrayAssert#extractingResultOf(String, Class)}</code>.
 * </ul>
 * 
 * @author Michał Piotrkowski
 */
public class ObjectArrayAssert_extractingResultOf_Test {

  private static FluentJedi yoda;
  private static FluentJedi vader;
  private static FluentJedi[] jedis;

  @BeforeClass
  public static void setUpOnce() {
    yoda = new FluentJedi(new Name("Yoda"), 800, false);
    vader = new FluentJedi(new Name("Darth Vader"), 50 ,true);
    jedis = array(yoda, vader);
  }

  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_throw_error_if_no_method_with_given_name_can_be_extracted() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Can't find method 'unknown' in class FluentJedi.class. Make sure public method exists and accepts no arguments!");
    assertThat(jedis).extractingResultOf("unknown");
  }

}

