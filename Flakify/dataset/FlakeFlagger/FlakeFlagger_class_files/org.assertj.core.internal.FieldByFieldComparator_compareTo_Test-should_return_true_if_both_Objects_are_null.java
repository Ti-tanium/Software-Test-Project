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

public class FieldByFieldComparator_compareTo_Test {

  @Rule
  public ExpectedException thrown = none();

  private FieldByFieldComparator fieldByFieldComparator;

  @Before
  public void setUp() {
	fieldByFieldComparator = new FieldByFieldComparator();
  }

  @Test public void should_return_true_if_both_Objects_are_null(){assertThat(fieldByFieldComparator.compare(null,null)).isZero();}

  public static class JarJar {

	public final String field;

	public JarJar(String field) {
	  this.field = field;
	}

  }

}