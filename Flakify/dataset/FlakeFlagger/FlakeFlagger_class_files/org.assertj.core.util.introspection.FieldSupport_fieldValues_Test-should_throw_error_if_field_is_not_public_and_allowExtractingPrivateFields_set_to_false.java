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
package org.assertj.core.util.introspection;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.Lists.newArrayList;

import java.util.List;

import org.assertj.core.test.Employee;
import org.assertj.core.test.ExpectedException;
import org.assertj.core.test.Name;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link FieldSupport#fieldValues(String, Class, Iterable)}</code>.
 * 
 * @author Joel Costigliola
 */
public class FieldSupport_fieldValues_Test {

  private Employee yoda;
  private Employee luke;
  private List<Employee> employees;
  private FieldSupport fieldSupport = FieldSupport.extraction();

  @Before
  public void setUpOnce() {
	yoda = new Employee(1L, new Name("Yoda"), 800);
	luke = new Employee(2L, new Name("Luke", "Skywalker"), 26);
	employees = newArrayList(yoda, luke);
  }

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_throw_error_if_field_is_not_public_and_allowExtractingPrivateFields_set_to_false(){FieldSupport.EXTRACTION.setAllowUsingPrivateFields(false);try {thrown.expect(IntrospectionError.class,"Unable to obtain the value of the field <'age'> from <Employee[id=1, name=Name[first='Yoda', last='null'], age=800]>, check that field is public.");fieldSupport.fieldValues("age",Integer.class,employees);}  finally {FieldSupport.EXTRACTION.setAllowUsingPrivateFields(true);}}
}
