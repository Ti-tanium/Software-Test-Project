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

import static org.assertj.core.error.ShouldStartWith.shouldStartWith;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Strings;
import org.assertj.core.internal.StringsBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Strings#assertStartsWith(AssertionInfo, CharSequence, CharSequence)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Strings_assertStartsWith_Test extends StringsBaseTest {

  @Test public void should_pass_if_actual_starts_with_prefix_according_to_custom_comparison_strategy(){stringsWithCaseInsensitiveComparisonStrategy.assertStartsWith(someInfo(),"Yoda","Y");stringsWithCaseInsensitiveComparisonStrategy.assertStartsWith(someInfo(),"Yoda","Yo");stringsWithCaseInsensitiveComparisonStrategy.assertStartsWith(someInfo(),"Yoda","Yod");stringsWithCaseInsensitiveComparisonStrategy.assertStartsWith(someInfo(),"Yoda","Yoda");stringsWithCaseInsensitiveComparisonStrategy.assertStartsWith(someInfo(),"Yoda","yoda");stringsWithCaseInsensitiveComparisonStrategy.assertStartsWith(someInfo(),"Yoda","YODA");}

}
