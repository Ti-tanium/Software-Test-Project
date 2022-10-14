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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldContainOnly.shouldContainOnly;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Sets.newLinkedHashSet;

import java.util.Collections;

import org.assertj.core.description.TextDescription;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.core.util.CaseInsensitiveStringComparator;
import org.junit.Test;

/**
 * Tests for
 * <code>{@link ShouldContainOnly#create(org.assertj.core.description.Description, org.assertj.core.presentation.Representation)}</code>
 * .
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Joel Costigliola
 */
public class ShouldContainOnly_create_Test {

  private static final ComparatorBasedComparisonStrategy CASE_INSENSITIVE_COMPARISON_STRATEGY =
      new ComparatorBasedComparisonStrategy(CaseInsensitiveStringComparator.instance);

  @Test
  public void should_create_error_message() {
	ErrorMessageFactory factory = shouldContainOnly(newArrayList("Yoda", "Han"), newArrayList("Luke", "Yoda"),
	                                                newLinkedHashSet("Luke"), newLinkedHashSet("Han"));

	String message = factory.create(new TextDescription("Test"), new StandardRepresentation());

	assertThat(message).isEqualTo("[Test] \n"
	                              + "Expecting:\n"
	                              + "  <[\"Yoda\", \"Han\"]>\n"
	                              + "to contain only:\n"
	                              + "  <[\"Luke\", \"Yoda\"]>\n"
	                              + "elements not found:\n"
	                              + "  <[\"Luke\"]>\n"
	                              + "and elements not expected:\n"
	                              + "  <[\"Han\"]>\n");
  }
}
