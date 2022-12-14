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
package org.assertj.core.api;

import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.test.TestData.someTextDescription;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.assertj.core.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link Condition#as(String)}</code>.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class Condition_as_String_Test {

  @Rule
  public ExpectedException thrown = none();

  private Condition<Object> condition;

  @Before
  public void setUp() {
    condition = new TestCondition<>();
  }

  @Test
  public void should_set_description() {
    String description = someTextDescription();
    condition.as(description);
    assertEquals(description, condition.description.value());
  }
}
