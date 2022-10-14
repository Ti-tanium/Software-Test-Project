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
package org.assertj.core.condition;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.condition.DoesNotHave.doesNotHave;

import org.assertj.core.api.Condition;
import org.assertj.core.api.TestCondition;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for <code>{@link DoesNotHave#toString()}</code>.
 * 
 * @author Nicolas François
 */
public class DoesNotHave_toString_Test {

  private TestCondition<Object> condition;
  private Condition<Object> doesNotHave;

  @Before
  public void setUp() {
    condition = new TestCondition<>("JediPower");
    doesNotHave = doesNotHave(condition);
  }

  @Test
  public void should_implement_toString_showing_descriptions_of_inner_Conditions() {
    String expected = "does not have :<JediPower>";
    assertEquals(expected, doesNotHave.toString());
  }

}
