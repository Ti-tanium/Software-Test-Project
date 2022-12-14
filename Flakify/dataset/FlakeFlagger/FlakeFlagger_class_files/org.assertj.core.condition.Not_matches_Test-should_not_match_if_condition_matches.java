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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.assertj.core.condition.Not.not;

import org.assertj.core.api.Condition;
import org.assertj.core.api.TestCondition;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for <code>{@link Not#matches(Object)}</code>.
 * 
 * @author Nicolas François
 */
public class Not_matches_Test {

  private TestCondition<Object> condition;
  private Condition<Object> not;

  @Before
  public void setUp() {
    condition = new TestCondition<>();
    not = not(condition);
  }

  @Test public void should_not_match_if_condition_matches(){condition.shouldMatch(true);assertFalse(not.matches("Yoda"));}

}
