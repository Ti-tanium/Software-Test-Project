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
package org.assertj.core.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ExpectedException.none;

import org.assertj.core.test.ExpectedException;
import org.assertj.core.util.introspection.IntrospectionError;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class OnFieldsComparator_compare_Test {

  @Rule
  public ExpectedException thrown = none();

  private OnFieldsComparator onFieldsComparator;

  @Before
  public void setUp() {
	onFieldsComparator = new OnFieldsComparator("telling");
  }

  @Test
  public void should_throw_exception_if_Objects_have_not_the_same_properties() {
	thrown.expect(IntrospectionError.class,
	              "Unable to obtain the value of <'telling'> field/property from <2>, expecting a public field or getter");
	assertThat(onFieldsComparator.compare(new DarthVader("I like you", "I'll kill you"), 2)).isNotZero();
  }

  public static class DarthVader {

	public final String telling;
	public final String thinking;

	public DarthVader(String telling, String thinking) {
	  this.telling = telling;
	  this.thinking = thinking;
	}

  }

}