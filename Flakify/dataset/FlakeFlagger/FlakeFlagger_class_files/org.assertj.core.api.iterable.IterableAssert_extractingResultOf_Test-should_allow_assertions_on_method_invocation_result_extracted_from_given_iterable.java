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
package org.assertj.core.api.iterable;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.test.ExpectedException.*;
import static org.assertj.core.util.Lists.*;

import org.assertj.core.api.AbstractIterableAssert;
import org.assertj.core.test.ExpectedException;
import org.assertj.core.test.FluentJedi;
import org.assertj.core.test.Name;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for: 
 * <ul>
 *  <li><code>{@link AbstractIterableAssert#extractingResultOf(String)}</code>,
 *  <li><code>{@link AbstractIterableAssert#extractingResultOf(String, Class)}</code>.
 * </ul>
 * 
 * @author Michał Piotrkowski
 */
public class IterableAssert_extractingResultOf_Test {

  private static FluentJedi yoda;
  private static FluentJedi vader;
  private static Iterable<FluentJedi> jedis;

  @BeforeClass
  public static void setUpOnce() {
    yoda = new FluentJedi(new Name("Yoda"), 800, false);
    vader = new FluentJedi(new Name("Darth Vader"), 50 ,true);
    jedis = newArrayList(yoda, vader);
  }

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_allow_assertions_on_method_invocation_result_extracted_from_given_iterable() throws Exception{assertThat(jedis).extractingResultOf("age").containsOnly(800,50);assertThat(jedis).extractingResultOf("darkSide").containsOnly(false,true);assertThat(jedis).extractingResultOf("name").containsOnly(new Name("Yoda"),new Name("Darth Vader"));assertThat(jedis).extractingResultOf("toString").containsOnly("Yoda","Darth Vader");}
  
}

