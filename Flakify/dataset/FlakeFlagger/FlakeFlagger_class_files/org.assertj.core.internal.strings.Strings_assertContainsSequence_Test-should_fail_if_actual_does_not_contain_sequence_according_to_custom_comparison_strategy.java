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
package org.assertj.core.internal.strings;

import static org.assertj.core.error.ShouldContainCharSequence.shouldContain;
import static org.assertj.core.error.ShouldContainCharSequenceSequence.shouldContainSequence;
import static org.assertj.core.test.ErrorMessages.arrayOfValuesToLookForIsEmpty;
import static org.assertj.core.test.ErrorMessages.arrayOfValuesToLookForIsNull;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Sets.newLinkedHashSet;

import static org.mockito.Mockito.verify;

import org.junit.Test;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Strings;
import org.assertj.core.internal.StringsBaseTest;

/**
 * Tests for <code>{@link Strings#assertContains(AssertionInfo, CharSequence, CharSequence)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Strings_assertContainsSequence_Test extends StringsBaseTest {

  

  // tests with custom comparison strategy

  @Test public void should_fail_if_actual_does_not_contain_sequence_according_to_custom_comparison_strategy(){AssertionInfo info=someInfo();try {stringsWithCaseInsensitiveComparisonStrategy.assertContainsSequence(info,"Yoda",array("Yo","da","Han"));} catch (AssertionError e){verify(failures).failure(info,shouldContain("Yoda",array("Yo","da","Han"),newLinkedHashSet("Han"),comparisonStrategy));return;}failBecauseExpectedAssertionErrorWasNotThrown();}

}
