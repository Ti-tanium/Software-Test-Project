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
package org.assertj.core.extractor;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.test.ExpectedException.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.iterable.Extractor;
import org.assertj.core.groups.Tuple;
import org.assertj.core.test.Employee;
import org.assertj.core.test.ExpectedException;
import org.assertj.core.test.Name;
import org.assertj.core.util.introspection.IntrospectionError;
import org.junit.Rule;
import org.junit.Test;

public class ByNameMultipleExtractorTest {

  @Rule
  public ExpectedException thrown = none();

  private Employee yoda = new Employee(1L, new Name("Yoda"), 800);

  @Test public void should_extract_multiple_values_from_maps_by_keys(){String key1="key1";String key2="key2";Map<String, Employee> map1=new HashMap<>();map1.put(key1,yoda);Employee luke=new Employee(2L,new Name("Luke"),22);map1.put(key2,luke);Map<String, Employee> map2=new HashMap<>();map2.put(key1,yoda);Employee han=new Employee(3L,new Name("Han"),31);map2.put(key2,han);List<Map<String, Employee>> maps=asList(map1,map2);assertThat(maps).extracting(key1,key2,"bad key").containsExactly(tuple(yoda,luke,null),tuple(yoda,han,null));}

}
