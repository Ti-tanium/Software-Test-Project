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
package org.assertj.core.internal.conditions;

import static org.assertj.core.error.ShouldHave.shouldHave;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.assertj.core.internal.ConditionsBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Conditions#assertHas(AssertionInfo, Object, Condition)}</code>.
 * 
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
public class Conditions_assertHas_Test extends ConditionsBaseTest {

  @Test public void should_fail_if_Condition_is_not_met(){condition.shouldMatch(false);AssertionInfo info=someInfo();try {conditions.assertHas(info,actual,condition);} catch (AssertionError e){verify(failures).failure(info,shouldHave(actual,condition));return;}failBecauseExpectedAssertionErrorWasNotThrown();}
}
