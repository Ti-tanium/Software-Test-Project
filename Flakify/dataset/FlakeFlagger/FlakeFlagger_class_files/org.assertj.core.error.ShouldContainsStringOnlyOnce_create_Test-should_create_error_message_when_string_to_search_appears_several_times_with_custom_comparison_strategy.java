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
package org.assertj.core.error;

import static junit.framework.Assert.assertEquals;

import static org.assertj.core.error.ShouldContainCharSequenceOnlyOnce.shouldContainOnlyOnce;

import org.assertj.core.description.TextDescription;
import org.assertj.core.internal.*;
import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.core.util.CaseInsensitiveStringComparator;
import org.junit.Before;
import org.junit.Test;


public class ShouldContainsStringOnlyOnce_create_Test {

  private ErrorMessageFactory factoryWithSeveralOccurences;
  private ErrorMessageFactory factoryWithNoOccurence;

  @Before
  public void setUp() {
    factoryWithSeveralOccurences = shouldContainOnlyOnce("aaamotifmotifaabbbmotifaaa", "motif", 3);
    factoryWithNoOccurence = shouldContainOnlyOnce("aaamodifmoifaabbbmotfaaa", "motif", 0);
  }

  @Test public void should_create_error_message_when_string_to_search_appears_several_times_with_custom_comparison_strategy(){ErrorMessageFactory factory=shouldContainOnlyOnce("aaamotIFmoTifaabbbmotifaaa","MOtif",3,new ComparatorBasedComparisonStrategy(CaseInsensitiveStringComparator.instance));String message=factory.create(new TextDescription("Test"),new StandardRepresentation());assertEquals("[Test] \nExpecting:\n <\"MOtif\">\nto appear only once in:\n <\"aaamotIFmoTifaabbbmotifaaa\">\nbut it appeared 3 times when comparing values using 'CaseInsensitiveStringComparator'",message);}

}
